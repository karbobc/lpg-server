package com.hlqz.lpg.util;

import com.hlqz.lpg.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import tools.jackson.core.json.JsonReadFeature;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ext.javatime.deser.LocalDateDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalTimeSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Karbob
 * @date 2023-11-21
 */
public class JsonUtils {

    private static final ObjectMapper om;
    static {
        // Long 序列化为 String, 避免接口回传到前端丢失精度
        SimpleModule longToStringModule = new SimpleModule();
        longToStringModule.addSerializer(Long.class, ToStringSerializer.instance);

        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtils.PATTERN_DATETIME);

        om = JsonMapper.builder()
            .addModule(longToStringModule)
            .addModule(buildJavaTimeModule())
            .defaultDateFormat(sdf)
            // 反序列化为 Map, Collection 类型时候, 使用 BigDecimal 替代 Float, 否则在反序列化为 Object 时候会丢失精度
            .configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true)
            // 避免反系列化未知列时出现异常
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            // 允许单引号的 json 格式
            .configure(JsonReadFeature.ALLOW_SINGLE_QUOTES, true)
            // 允许末尾多余的英文逗号
            .configure(JsonReadFeature.ALLOW_TRAILING_COMMA, true)
            .build();
    }

    private JsonUtils() {
    }

    /**
     * 构造 Java Time 序列化模块
     */
    private static SimpleModule buildJavaTimeModule() {
        SimpleModule javaTimeModule = new SimpleModule();
        javaTimeModule.addSerializer(LocalDateTime.class,
            new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateTimeUtils.PATTERN_DATETIME)));
        javaTimeModule.addSerializer(LocalDate.class,
            new LocalDateSerializer(DateTimeFormatter.ofPattern(DateTimeUtils.PATTERN_DATE)));
        javaTimeModule.addSerializer(LocalTime.class,
            new LocalTimeSerializer(DateTimeFormatter.ofPattern(DateTimeUtils.PATTERN_TIME)));
        javaTimeModule.addDeserializer(LocalDateTime.class,
            new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DateTimeUtils.PATTERN_DATETIME)));
        javaTimeModule.addDeserializer(LocalDate.class,
            new LocalDateDeserializer(DateTimeFormatter.ofPattern(DateTimeUtils.PATTERN_DATE)));
        javaTimeModule.addDeserializer(LocalTime.class,
            new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DateTimeUtils.PATTERN_TIME)));
        return javaTimeModule;
    }

    /**
     * 获取 ObjectMapper 实例, 深拷贝
     * @return ObjectMapper 实例
     */
    public static ObjectMapper getInstance() {
        return om.rebuild().build();
    }

    /**
     * 循环构建 JavaType。 <br />
     * WARNING: 不支持 Map<?, ?> 类型的数据嵌套。 <br />
     * 如 Outer<Inner<SubInner>> 格式的对象, 使用时，将参数依次传递。 <br />
     * createType(Outer.class, Inner.class, SubInner.class), 得到 Outer<Inner<SubInner>> 格式的 JavaType。
     * @return 按照参数顺序构建的 JavaType
     */
    public static JavaType createType(Class<?>... clazz) {
        int len;
        if (clazz == null || (len = clazz.length) == 0) {
            return null;
        }
        if (len == 1) {
            return om.getTypeFactory().constructType(clazz[0]);
        }
        JavaType javaType = om.getTypeFactory().constructParametricType(clazz[len - 2], clazz[len - 1]);
        for (int i = (len - 2); i > 0; i--) {
            javaType = om.getTypeFactory().constructParametricType(clazz[i - 1], javaType);
        }
        return javaType;
    }

    /**
     * 将对象序列化为 json 字符串
     *
     * @param object 任意对象
     * @return json 字符串, 可能为空
     * @throws BizException 序列化失败抛出异常
     */
    public static String toJson(Object object) {
        return toJson(om, object);
    }

    /**
     * 将对象序列化为 json 字符串
     *
     * @param om 自定义 ObjectMapper
     * @param object 任意对象
     * @return json 字符串, 可能为空
     * @throws BizException 序列化失败抛出异常
     */
    public static String toJson(ObjectMapper om, Object object) {
        if (Objects.isNull(om) || Objects.isNull(object)) {
            return null;
        }
        if (object instanceof String) {
            return (String) object;
        }
        try {
            return om.writeValueAsString(object);
        } catch (Exception e) {
            throw new BizException("JSON 序列化失败!", e);
        }
    }

    /**
     * 将 json 字符串反序列化为指定的类型 T 对象。 <br />
     * fromJson(json, Outer.class), 得到 Outer 格式的对象。 <br />
     * fromJson(json, Outer.class, Inner.class, SubInner.class), 得到 Outer<Inner<SubInner>> 格式的对象。
     *
     * @param json json 字符串
     * @return 反序列化对象, 可能为空
     * @throws BizException 反序列失败抛出异常
     */
    public static <T> T fromJson(String json, Class<?>... clazz) {
        return fromJson(om, json, clazz);
    }

    /**
     * 将 json 字符串反序列化为指定的类型 T 对象。
     *
     * @param json json 字符串
     * @return 反序列化对象, 可能为空
     * @throws BizException 反序列失败抛出异常
     */
    public static <T> T fromJson(String json, JavaType javaType) {
        return fromJson(om, json, javaType);
    }

    /**
     * 将 json 字符串反序列化为指定的类型 T 对象。 <br />
     * fromJson(om, json, Outer.class), 得到 Outer 格式的对象。 <br />
     * fromJson(om, json, Outer.class, Inner.class, SubInner.class), 得到 Outer<Inner<SubInner>> 格式的对象。
     *
     * @param om 自定义 ObjectMapper
     * @param json json 字符串
     * @return 反序列化对象, 可能为空
     * @throws BizException 反序列失败抛出异常
     */
    public static <T> T fromJson(ObjectMapper om, String json, Class<?>... clazz) {
        JavaType javaType;
        try {
            javaType = createType(clazz);
        } catch (Exception e) {
            throw new BizException("JSON 构造 JavaType 失败!", e);
        }
        return fromJson(om, json, javaType);
    }

    /**
     * 将 json 字符串反序列化为指定的类型 T 对象。
     *
     * @param om 自定义 ObjectMapper
     * @param json json 字符串
     * @return 反序列化对象, 可能为空
     * @throws BizException 反序列失败抛出异常
     */
    public static <T> T fromJson(ObjectMapper om, String json, JavaType javaType) {
        if (Objects.isNull(om) || StringUtils.isBlank(json) || Objects.isNull(javaType)) {
            return null;
        }
        try {
            return om.readValue(json, javaType);
        } catch (Exception e) {
            throw new BizException("JSON 反序列化失败!", e);
        }
    }

    /**
     * 将 json 字符串反序列化为指定的类型 HashMap<String, Object> 对象。
     *
     * @param json json 字符串
     * @return 反序列化为 Map 类型
     * @throws BizException 反序列失败抛出异常
     */
    public static Map<String, Object> toMap(String json) {
        return toMap(om, json, Object.class);
    }

    /**
     * 将 json 字符串反序列化为指定的类型 HashMap<String, T> 对象。
     *
     * @param json json 字符串
     * @return 反序列化为 Map 类型
     * @throws BizException 反序列失败抛出异常
     */
    public static <T> Map<String, T> toMap(String json, Class<T> valueClass) {
        return toMap(om, json, HashMap.class, valueClass);
    }

    /**
     * 将 json 字符串反序列化为指定的类型 MapClass<String, T> 对象。
     * @param json json 字符串
     * @return 反序列化为 Map 类型
     * @throws BizException 反序列失败抛出异常
     */
    public static <T> Map<String, T> toMap(String json, Class<? extends Map> mapClass, Class<T> valueClass) {
        return toMap(om, json, mapClass, valueClass);
    }

    /**
     * 将 json 字符串反序列化为指定的类型 HashMap<String, Object> 对象。
     * @param om 自定义 ObjectMapper
     * @param json json 字符串
     * @return 反序列化为 Map 类型
     * @throws BizException 反序列失败抛出异常
     */
    public static Map<String, Object> toMap(ObjectMapper om, String json) {
        return toMap(om, json, Object.class);
    }

    /**
     * 将 json 字符串反序列化为指定的类型 HashMap<String, T> 对象。
     * @param om 自定义 ObjectMapper
     * @param json json 字符串
     * @return 反序列化为 Map 类型
     * @throws BizException 反序列失败抛出异常
     */
    public static <T> Map<String, T> toMap(ObjectMapper om, String json, Class<T> valueClass) {
        return toMap(om, json, HashMap.class, valueClass);
    }

    /**
     * 将 json 字符串反序列化为指定的类型 MapClass<String, T> 对象。
     * @param om 自定义 ObjectMapper
     * @param json json 字符串
     * @return 反序列化为 Map 类型
     * @throws BizException 反序列失败抛出异常
     */
    public static <T> Map<String, T> toMap(ObjectMapper om, String json, Class<? extends Map> mapClass, Class<T> valueClass) {
        if (Objects.isNull(om) || StringUtils.isBlank(json) || Objects.isNull(mapClass) || Objects.isNull(valueClass)) {
            return Collections.emptyMap();
        }
        try {
            JavaType javaType = om.getTypeFactory().constructMapType(mapClass, String.class, valueClass);
            return om.readValue(json, javaType);
        } catch (Exception e) {
            throw new BizException("JSON 反序列化为 Map 对象失败!", e);
        }
    }
}
