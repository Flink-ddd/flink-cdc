package com.panda.cdc.config;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.panda.cdc.constant.GlobalConstant;
import com.panda.cdc.constant.SystemConstant;
import com.panda.cdc.dao.entity.MCdcTenants;
import com.panda.cdc.dao.mapper.MCdcTenantsMapper;
import com.panda.cdc.dto.resp.user.LoginRespDTO;
import com.panda.cdc.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * @author muxh
 * @since 2021/08/27 上午11:18
 */
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private MCdcTenantsMapper tenantsMapper;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenantsId = request.getHeader("tenantsId");
        String tenantsType = request.getHeader("tenantsType");
        String encryptedData = request.getHeader("encryptedData");

        //验证token
        handleToken(request, response, handler);

        //内部用户验证逻辑
        if (StringUtils.isNotBlank(tenantsType)) {
            if (String.valueOf(GlobalConstant.ONE).equals(tenantsType)) {
                LambdaQueryWrapper<MCdcTenants> tenantsLambdaQueryWrapper = Wrappers.lambdaQuery();
                tenantsLambdaQueryWrapper.eq(StringUtils.isNotBlank(tenantsId), MCdcTenants::getTenantId, tenantsId);
                tenantsLambdaQueryWrapper.eq(MCdcTenants::getFgDel, GlobalConstant.ZERO);
                MCdcTenants tenants = tenantsMapper.selectOne(tenantsLambdaQueryWrapper);
                if (null == tenants) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Tenants not found");
                    return false;
                }
                if (!GlobalConstant.ONE.equals(tenants.getTenantStatus())) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Tenants account not approved");
                    return false;
                }
                return true;
            } else {
                //B端企业和C端用户验证逻辑
                LambdaQueryWrapper<MCdcTenants> tenantsLambdaQueryWrapper = Wrappers.lambdaQuery();
                tenantsLambdaQueryWrapper.eq(StringUtils.isNotBlank(tenantsId), MCdcTenants::getTenantId, tenantsId);
                tenantsLambdaQueryWrapper.eq(MCdcTenants::getFgDel, GlobalConstant.ZERO);
                MCdcTenants tenants = tenantsMapper.selectOne(tenantsLambdaQueryWrapper);
                if (null != tenants) {
                    //判断isDock字段
                    if (GlobalConstant.ONE.equals(tenants.getIsDock())) {
                        if (tenantsId == null || encryptedData == null) {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            response.getWriter().write("Missing tenant ID or encrypted data");
                            return false;
                        }
                        try {
                            PrivateKey privateKey = getPrivateKeyForTenant(tenantsId);
                            String decryptedData = decryptData(encryptedData, privateKey);
                            String[] parts = decryptedData.split(":");
                            String tenantKey = parts[0];
                            String appid = parts[1];
                            if (isTenantApproved(tenantsId, tenantKey, appid)) {
                                request.setAttribute("tenantKey", tenantKey);
                                request.setAttribute("appid", appid);
                                return true;
                            } else {
                                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                response.getWriter().write("Access Denied: Not Approved");
                                return false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                            response.getWriter().write("Internal Server Error");
                            return false;
                        }
                    } else {
                        if (!GlobalConstant.ONE.equals(tenants.getTenantStatus())) {
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Tenants account not approved");
                            return false;
                        }
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Tenants not found");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 根据租户ID获取私钥
     */
    private PrivateKey getPrivateKeyForTenant(String tenantsId) throws Exception {
        LambdaQueryWrapper<MCdcTenants> tenantsLambdaQueryWrapper = Wrappers.lambdaQuery();
        tenantsLambdaQueryWrapper.eq(StringUtils.isNotBlank(tenantsId), MCdcTenants::getTenantId, tenantsId);
        tenantsLambdaQueryWrapper.eq(MCdcTenants::getFgDel, GlobalConstant.ZERO);
        MCdcTenants cdcTenants = tenantsMapper.selectOne(tenantsLambdaQueryWrapper);
        String base64PrivateKey = cdcTenants.getPrivateKey();
        byte[] decodedKey = Base64.getDecoder().decode(base64PrivateKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    /**
     * 解密
     */
    private String decryptData(String encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedData, "UTF-8");
    }

    /**
     * 验证外部用户的审核状态
     */
    private boolean isTenantApproved(String tenantsId, String tenantKey, String appid) {
        LambdaQueryWrapper<MCdcTenants> tenantsLambdaQueryWrapper = Wrappers.lambdaQuery();
        tenantsLambdaQueryWrapper.eq(StringUtils.isNotBlank(tenantsId), MCdcTenants::getTenantId, tenantsId);
        tenantsLambdaQueryWrapper.eq(StringUtils.isNotBlank(tenantKey), MCdcTenants::getTenantKey, tenantKey);
        tenantsLambdaQueryWrapper.eq(StringUtils.isNotBlank(appid), MCdcTenants::getAppId, appid);
        tenantsLambdaQueryWrapper.eq(MCdcTenants::getFgDel, GlobalConstant.ZERO);
        MCdcTenants cdcTenants = tenantsMapper.selectOne(tenantsLambdaQueryWrapper);
        if (null == cdcTenants) {
            return false;
        }
        if (!GlobalConstant.ONE.equals(cdcTenants.getTenantType())) {
            return false;
        }
        return true;
    }

    protected boolean handleToken(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 返回验证结果
        if (validToken(request)) {
            return true;
        } else {
            return handleInvalidToken(request, response, handler);
        }
    }

    /**
     * 验证当前请求参数中的token是否合法
     *
     * @return 验证结果
     */
    public boolean validToken(HttpServletRequest request) {
        //获取请求中的token
        String requestToken = JwtUtil.getRequestToken(request);
        LoginRespDTO requestUser = jwtUtil.getRequestUser(requestToken);
        String token = request.getHeader(SystemConstant.ACCESSTOKEN);
        if (token == null) {
            log.info("No token found -> Invalid token");
            return false;
        }
        // token过期
        if (requestUser == null) {
            return false;
        }
//        OperatorHolder.set(requestToken);
        return true;
    }

    /**
     * 当出现一个非法令牌时调用
     */
    protected boolean handleInvalidToken(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        JSONObject res = new JSONObject();
        res.put("code", 200);
        res.put("message", "登录信息过期");
        res.put("data", false);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.append(res.toString());
        return false;
    }

    /**
     * 拒绝未授权的请求
     *
     * @param response
     */
    private void reject(HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        try {
            JSONObject resp = new JSONObject();
            resp.put("success", false);
            resp.put("code", HttpStatus.UNAUTHORIZED.value());
            resp.put("message", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            response.setCharacterEncoding(GlobalConstant.CHARSET_UTF_8);
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(resp.toJSONString());
        } catch (IOException e) {
        }
    }


}
