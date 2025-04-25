package com.panda.cdc.utils;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.panda.cdc.constant.SystemConstant;
import com.panda.cdc.dto.resp.user.LoginRespDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * token控制类
 */
@Component
public class JwtUtil {

    /**
     * 创建token
     *
     * @param id
     * @param subject
     * @param roles
     * @return
     */
    public String createJWT(String id, String subject, String roles) {
        long nowMillis = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject).setIssuedAt(new Date(nowMillis))
                .signWith(SignatureAlgorithm.HS256, SystemConstant.key).claim("roles", roles);

        builder.setExpiration(new Date(nowMillis + getTimeOut()));
        return builder.compact();
    }

    public static long getTimeOut() {
        return System.currentTimeMillis() + SystemConstant.ttl * SystemConstant.dayTimes;
    }

    /**
     * token字符串转换Claims对象
     *
     * @param jwtStr
     * @return
     */
    public static Claims parseJwt(String jwtStr) {
        return Jwts.parser().setSigningKey(SystemConstant.key).parseClaimsJws(jwtStr).getBody();
    }

    /**
     * 获取请求中的token值
     *
     * @param request
     * @return
     */
    public static String getRequestToken(HttpServletRequest request) {
        String jwtStr = request.getHeader(SystemConstant.ACCESSTOKEN);
        //判断token格式
        if (StringUtils.isNotBlank(jwtStr) && jwtStr.startsWith("Bearer ")) {
            return jwtStr.replace("Bearer ", "");
        }
        return jwtStr;
    }


    /**
     * token转换用户对象
     *
     * @return
     */
    public static LoginRespDTO getRequestUser() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = getRequestToken(request);
        return getRequestUser(token);
    }

    public static LoginRespDTO getRequestUser(String token) {
        if(StringUtils.isNotBlank(token)){
            Claims claims = parseJwt(token);
            String data = claims.getSubject();
            if(StringUtils.isNotBlank(data))
                return JSONObject.parseObject(JSONUtil.toJsonStr(data), LoginRespDTO.class);
        }
        return null;
    }
}
