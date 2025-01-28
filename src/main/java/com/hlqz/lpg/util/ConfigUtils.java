package com.hlqz.lpg.util;

import cn.hutool.extra.spring.SpringUtil;

/**
 * @author Karbob
 * @date 2024-01-06
 *
 * 配置工具类
 */
public class ConfigUtils {

    public static String getDatabaseAesKey() {
        return SpringUtil.getProperty("DB_AES_KEY");
    }

    public static String getLyCookie() {
        return SpringUtil.getProperty("LY_COOKIE");
    }

    public static String getLyNewCookie() {
        return SpringUtil.getProperty("LY_NEW_COOKIE");
    }

    public static String getLyOptId() {
        return SpringUtil.getProperty("LY_OPT_ID");
    }

    public static String getLySectionId() {
        return SpringUtil.getProperty("LY_SECTION_ID");
    }

    public static String getLyMinCrNo() {
        return SpringUtil.getProperty("LY_MIN_CR_NO");
    }

    public static String getLyMaxCrNo() {
        return SpringUtil.getProperty("LY_Max_CR_NO");
    }

    public static String getNtfyUsername() {
        return SpringUtil.getProperty("NTFY_USERNAME");
    }

    public static String getNtfyPassword() {
        return SpringUtil.getProperty("NTFY_PASSWORD");
    }
}
