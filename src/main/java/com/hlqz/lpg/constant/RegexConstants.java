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
    Pattern USER_ADDRESS = Pattern.compile(".+县.+[镇乡].+(村|社区).+[屯街区路巷].*?");

    /**
     * 兰洋系统溯源客户名称
     */
    Pattern LY_TRACE_UNAME = Pattern.compile(">客户：(.*?)<", Pattern.DOTALL);
}
