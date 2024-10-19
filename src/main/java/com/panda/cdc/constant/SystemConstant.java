package com.panda.cdc.constant;

import org.springframework.context.annotation.Configuration;

/**
 * 系统常量类
 * @author muxiaohui
 */
@Configuration
public class SystemConstant {
    /**
     * 秘钥
     */
    public static String key = "cdc_jk";

    /**
     * 有效时间
     */
    public static  long ttl = 180;


    /**
     * 一天的毫秒数
     */
    public static Integer dayTimes = 86400000;
    /**
     * redis使用的常量
     */
    public static String TITLE = "GX_RHT:";
    public static String YZM = TITLE + "yzm:";
    public static String YZM_INTERVAL_IP = TITLE + "yzm_interval_ip";
    public static String YZM_INTERVAL_PHONE = TITLE + "yzm_interval_phone";
    public static String TOKEN_KEY = TITLE + "token";
    //验证码请求间隔
    public static Long YZM_REQUEST_INTERVAL_TIME = 60l;
    //每天发送次数
    public static Long YZM_SEND_TIMES_DAY = 100l;

    /**
     * 其他
     */
    public static String AUTHORIZATION = "authorization";

    public static String ACCESSTOKEN = "accessToken";

    //登录手机验证码
    public static String SMS_TYPE_LOGIN = "login";

    //注销账号验证码
    public static String SMS_TYPE_CANCEL = "cancel";

    public static String getSmsType(Integer type){
        switch (type){
            case 1:
                return "login";
            case 2:
                return "cancelUser";
            case 3:
                return "oldPhone";
            case 4:
                return "updatePhone";
            default:
                return "other";
        }
    }
}
