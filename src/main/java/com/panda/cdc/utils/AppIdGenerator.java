package com.panda.cdc.utils;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author muxiaohui
 */
public class AppIdGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    public static String generateAppId() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
