package com.hlqz.lpg.util;

import com.hlqz.lpg.exception.BizException;
import com.hlqz.lpg.model.enums.RcEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @author Karbob
 * @date 2024-01-05
 */
public class AssertionUtils {

    private AssertionUtils() {
    }

    private static boolean objectEquals(Object object1, Object object2) {
        if (object1 == null) {
            return (object2 == null);
        }
        return object1.equals(object2);
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new BizException(message);
        }
    }

    public static void assertTrue(boolean condition, String code, String message) {
        if (!condition) {
            throw new BizException(code, message);
        }
    }

    public static void assertTrue(boolean condition, RcEnum rc) {
        if (!condition) {
            throw new BizException(rc.getCode(), rc.getMessage());
        }
    }

    public static void assertTrue(boolean condition, RcEnum rc, String... messages) {
        if (!condition) {
            throw new BizException(rc.getCode(), rc.getMessage(messages));
        }
    }

    public static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new BizException(message);
        }
    }

    public static void assertFalse(boolean condition, String code, String message) {
        if (condition) {
            throw new BizException(code, message);
        }
    }

    public static void assertFalse(boolean condition, RcEnum rc) {
        if (condition) {
            throw new BizException(rc.getCode(), rc.getMessage());
        }
    }

    public static void assertFalse(boolean condition, RcEnum rc, String... messages) {
        if (condition) {
            throw new BizException(rc.getCode(), rc.getMessage(messages));
        }
    }

    public static void assertNull(Object object, String message) {
        if (object != null) {
            throw new BizException(message);
        }
    }

    public static void assertNull(Object object, String code, String message) {
        if (object != null) {
            throw new BizException(code, message);
        }
    }

    public static void assertNull(Object object, RcEnum rc) {
        if (object != null) {
            throw new BizException(rc.getCode(), rc.getMessage());
        }
    }

    public static void assertNull(Object object, RcEnum rc, String... messages) {
        if (object != null) {
            throw new BizException(rc.getCode(), rc.getMessage(messages));
        }
    }

    public static void assertNotNull(Object object, String message) {
        if (object == null) {
            throw new BizException(message);
        }
    }

    public static void assertNotNull(Object object, String code, String message) {
        if (object == null) {
            throw new BizException(code, message);
        }
    }

    public static void assertNotNull(Object object, RcEnum rc) {
        if (object == null) {
            throw new BizException(rc.getCode(), rc.getMessage());
        }
    }

    public static void assertNotNull(Object object, RcEnum rc, String... messages) {
        if (object == null) {
            throw new BizException(rc.getCode(), rc.getMessage(messages));
        }
    }

    public static void assertEmpty(CharSequence cs, String message) {
        if (StringUtils.isNotEmpty(cs)) {
            throw new BizException(message);
        }
    }

    public static void assertEmpty(CharSequence cs, String code, String message) {
        if (StringUtils.isNotEmpty(cs)) {
            throw new BizException(code, message);
        }
    }

    public static void assertEmpty(CharSequence cs, RcEnum rc) {
        if (StringUtils.isNotEmpty(cs)) {
            throw new BizException(rc.getCode(), rc.getMessage());
        }
    }

    public static void assertEmpty(CharSequence cs, RcEnum rc, String... messages) {
        if (StringUtils.isNotEmpty(cs)) {
            throw new BizException(rc.getCode(), rc.getMessage(messages));
        }
    }

    public static void assertEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isNotEmpty(collection)) {
            throw new BizException(message);
        }
    }

    public static void assertEmpty(Collection<?> collection, String code, String message) {
        if (CollectionUtils.isNotEmpty(collection)) {
            throw new BizException(code, message);
        }
    }

    public static void assertEmpty(Collection<?> collection, RcEnum rc) {
        if (CollectionUtils.isNotEmpty(collection)) {
            throw new BizException(rc.getCode(), rc.getMessage());
        }
    }

    public static void assertEmpty(Collection<?> collection, RcEnum rc, String... messages) {
        if (CollectionUtils.isNotEmpty(collection)) {
            throw new BizException(rc.getCode(), rc.getMessage(messages));
        }
    }

    public static void assertEmpty(Map<?, ?> map, String message) {
        if (MapUtils.isNotEmpty(map)) {
            throw new BizException(message);
        }
    }

    public static void assertEmpty(Map<?, ?> map, String code, String message) {
        if (MapUtils.isNotEmpty(map)) {
            throw new BizException(code, message);
        }
    }

    public static void assertEmpty(Map<?, ?> map, RcEnum rc) {
        if (MapUtils.isNotEmpty(map)) {
            throw new BizException(rc.getCode(), rc.getMessage());
        }
    }

    public static void assertEmpty(Map<?, ?> map, RcEnum rc, String... messages) {
        if (MapUtils.isNotEmpty(map)) {
            throw new BizException(rc.getCode(), rc.getMessage(messages));
        }
    }

    public static void assertNotEmpty(CharSequence cs, String message) {
        if (StringUtils.isEmpty(cs)) {
            throw new BizException(message);
        }
    }

    public static void assertNotEmpty(CharSequence cs, String code, String message) {
        if (StringUtils.isEmpty(cs)) {
            throw new BizException(code, message);
        }
    }

    public static void assertNotEmpty(CharSequence cs, RcEnum rc) {
        if (StringUtils.isEmpty(cs)) {
            throw new BizException(rc.getCode(), rc.getMessage());
        }
    }

    public static void assertNotEmpty(CharSequence cs, RcEnum rc, String... messages) {
        if (StringUtils.isEmpty(cs)) {
            throw new BizException(rc.getCode(), rc.getMessage(messages));
        }
    }

    public static void assertNotEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizException(message);
        }
    }

    public static void assertNotEmpty(Collection<?> collection, String code, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizException(code, message);
        }
    }

    public static void assertNotEmpty(Collection<?> collection, RcEnum rc) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizException(rc.getCode(), rc.getMessage());
        }
    }

    public static void assertNotEmpty(Collection<?> collection, RcEnum rc, String... messages) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizException(rc.getCode(), rc.getMessage(messages));
        }
    }

    public static void assertNotEmpty(Map<?, ?> map, String message) {
        if (MapUtils.isEmpty(map)) {
            throw new BizException(message);
        }
    }

    public static void assertNotEmpty(Map<?, ?> map, String code, String message) {
        if (MapUtils.isEmpty(map)) {
            throw new BizException(code, message);
        }
    }

    public static void assertNotEmpty(Map<?, ?> map, RcEnum rc) {
        if (MapUtils.isEmpty(map)) {
            throw new BizException(rc.getCode(), rc.getMessage());
        }
    }

    public static void assertNotEmpty(Map<?, ?> map, RcEnum rc, String... messages) {
        if (MapUtils.isEmpty(map)) {
            throw new BizException(rc.getCode(), rc.getMessage(messages));
        }
    }

    public static void assertBlank(CharSequence cs, String message) {
        if (StringUtils.isNotBlank(cs)) {
            throw new BizException(message);
        }
    }

    public static void assertBlank(CharSequence cs, String code, String message) {
        if (StringUtils.isNotBlank(cs)) {
            throw new BizException(code, message);
        }
    }

    public static void assertBlank(CharSequence cs, RcEnum rc) {
        if (StringUtils.isNotBlank(cs)) {
            throw new BizException(rc.getCode(), rc.getMessage());
        }
    }

    public static void assertBlank(CharSequence cs, RcEnum rc, String... messages) {
        if (StringUtils.isNotBlank(cs)) {
            throw new BizException(rc.getCode(), rc.getMessage(messages));
        }
    }

    public static void assertNotBlank(CharSequence cs, String message) {
        if (StringUtils.isBlank(cs)) {
            throw new BizException(message);
        }
    }

    public static void assertNotBlank(CharSequence cs, String code, String message) {
        if (StringUtils.isBlank(cs)) {
            throw new BizException(code, message);
        }
    }

    public static void assertNotBlank(CharSequence cs, RcEnum rc) {
        if (StringUtils.isBlank(cs)) {
            throw new BizException(rc.getCode(), rc.getMessage());
        }
    }

    public static void assertNotBlank(CharSequence cs, RcEnum rc, String... messages) {
        if (StringUtils.isBlank(cs)) {
            throw new BizException(rc.getCode(), rc.getMessage(messages));
        }
    }

    public static void assertEquals(Object object1, Object object2, String message) {
        if (!objectEquals(object1, object2)) {
            throw new BizException(message);
        }
    }

    public static void assertEquals(Object object1, Object object2, String code, String message) {
        if (!objectEquals(object1, object2)) {
            throw new BizException(code, message);
        }
    }

    public static void assertEquals(Object object1, Object object2, RcEnum rc) {
        if (!objectEquals(object1, object2)) {
            throw new BizException(rc.getCode(), rc.getMessage());
        }
    }

    public static void assertEquals(Object object1, Object object2, RcEnum rc, String... messages) {
        if (!objectEquals(object1, object2)) {
            throw new BizException(rc.getCode(), rc.getMessage(messages));
        }
    }

    public static void assertNotEquals(Object object1, Object object2, String message) {
        if (objectEquals(object1, object2)) {
            throw new BizException(message);
        }
    }

    public static void assertNotEquals(Object object1, Object object2, String code, String message) {
        if (objectEquals(object1, object2)) {
            throw new BizException(code, message);
        }
    }

    public static void assertNotEquals(Object object1, Object object2, RcEnum rc) {
        if (objectEquals(object1, object2)) {
            throw new BizException(rc.getCode(), rc.getMessage());
        }
    }

    public static void assertNotEquals(Object object1, Object object2, RcEnum rc, String... messages) {
        if (objectEquals(object1, object2)) {
            throw new BizException(rc.getCode(), rc.getMessage(messages));
        }
    }
}
