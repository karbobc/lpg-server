package com.hlqz.lpg.util;

/**
 * @author Karbob
 * @date 2024-01-06
 *
 * 配置工具类
 */
public class ConfigUtils {

    public static String getDatabaseAESKey() {
        return System.getenv("DB_AES_KEY");
    }

    public static String getLYCookie() {
        return System.getenv("LY_COOKIE");
    }
}
