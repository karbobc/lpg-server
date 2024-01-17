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

    public static String getLyCookie() {
        return System.getenv("LY_COOKIE");
    }

    public static String getLyOptId() {
        return System.getenv("LY_OPT_ID");
    }

    public static String getLySectionId() {
        return System.getenv("LY_SECTION_ID");
    }

    public static String getNtfyUsername() {
        return System.getenv("NTFY_USERNAME");
    }

    public static String getNtfyPassword() {
        return System.getenv("NTFY_PASSWORD");
    }
}
