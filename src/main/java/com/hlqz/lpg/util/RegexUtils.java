package com.hlqz.lpg.util;

import java.util.regex.Pattern;

/**
 * @author Karbob
 * @date 2024-02-18
 */
public class RegexUtils {

    private RegexUtils() {
    }

    public static boolean matches(final Pattern pattern, final CharSequence input) {
        return pattern.matcher(input).matches();
    }
}
