package com.hlqz.lpg.constant;

import java.util.regex.Pattern;

/**
 * @author Karbob
 * @date 2024-02-18
 */
public interface RegexConstants {

    /**
     * 用户住址
     */
    Pattern USER_ADDRESS = Pattern.compile(".+县.+[镇乡].+(村|社区).+[屯街区].*?");
}
