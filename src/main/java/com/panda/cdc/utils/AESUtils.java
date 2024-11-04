package com.panda.cdc.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtils {
    private static final byte[] KEY_BYTES = new byte[16];

    public static String encrypt(String plaintext) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY_BYTES, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
            byte[] ciphertext = cipher.doFinal(plaintext.getBytes());
            return Base64.getEncoder().encodeToString(iv) + ":" + Base64.getEncoder().encodeToString(ciphertext);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String ciphertext) {
        try {
            String[] parts = ciphertext.split(":");
            byte[] iv = Base64.getDecoder().decode(parts[0]);
            byte[] encrypted = Base64.getDecoder().decode(parts[1]);
            SecretKeySpec keySpec = new SecretKeySpec(KEY_BYTES, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
            byte[] plaintext = cipher.doFinal(encrypted);
            return new String(plaintext);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
