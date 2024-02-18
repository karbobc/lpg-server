package com.hlqz.lpg.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

import java.nio.charset.StandardCharsets;

/**
 * @author Karbob
 * @date 2024-01-06
 */
public class AesUtils {

    private AesUtils() {
    }

    /**
     * AES/ECB/PKCS5Padding 加密
     * @param data 明文
     * @param secretKey 密钥
     * @return 密文
     */
    public static String encrypt(String data, String secretKey) {
        AES aes = SecureUtil.aes(secretKey.getBytes(StandardCharsets.UTF_8));
        return aes.encryptBase64(data, StandardCharsets.UTF_8);
    }

    /**
     * AES/ECB/PKCS5Padding 解密
     * @param data 密文
     * @param secretKey 密钥
     * @return 明文
     */
    public static String decrypt(String data, String secretKey) {
        AES aes = SecureUtil.aes(secretKey.getBytes(StandardCharsets.UTF_8));
        return aes.decryptStr(data, StandardCharsets.UTF_8);
    }
}
