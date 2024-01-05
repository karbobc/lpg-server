package com.hlqz.lpg.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.hlqz.lpg.model.enums.RcEnum;
import com.hlqz.lpg.util.DateTimeUtils;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Karbob
 * @date 2023-11-21
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {

    /**
     * 全局错误码
     */
    private String code;

    /**
     * 接口响应状态, 成功 or 失败, 默认 true
     */
    private Boolean success;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 回传数据
     */
    private T data;

    /**
     * 接口响应时间, yyyy-MM-dd HH:mm:ss 格式
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = DateTimeUtils.PATTERN_DATETIME)
    private LocalDateTime datetime;

    /**
     * 接口响应时间戳, 13 位
     */
    private Long timestamp;

    public ApiResult() {
        LocalDateTime now = LocalDateTime.now();
        this.datetime = now;
        this.timestamp = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        this.success = Boolean.TRUE;
    }

    public ApiResult(String code, String message) {
        this();
        this.code = code;
        this.message = message;
    }

    public ApiResult(String code, String message, Boolean success) {
        this(code, message);
        this.success = success;
    }

    public ApiResult(String code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    public ApiResult(@NonNull RcEnum rc) {
        this(rc.getCode(), rc.getMessage());
        this.success = rc == RcEnum.OK;
    }

    public ApiResult(@NonNull RcEnum rc, Boolean success) {
        this(rc);
        this.success = success;
    }

    public ApiResult(@NonNull RcEnum rc, T data) {
        this(rc.getCode(), rc.getMessage(), data);
    }

    @NonNull
    public static ApiResult<Void> ok() {
        return new ApiResult<>(RcEnum.OK);
    }

    @NonNull
    public static ApiResult<Void> ok(String message) {
        return new ApiResult<>(RcEnum.OK.getCode(), message);
    }

    @NonNull
    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>(RcEnum.OK, data);
    }

    @NonNull
    public static ApiResult<Void> error() {
        return new ApiResult<>(RcEnum.BIZ_ERROR, Boolean.FALSE);
    }

    @NonNull
    public static ApiResult<Void> error(String message) {
        return new ApiResult<>(RcEnum.BIZ_ERROR.getCode(), message, Boolean.FALSE);
    }

    @NonNull
    public static ApiResult<Void> error(String code, String message) {
        return new ApiResult<>(code, message, Boolean.FALSE);
    }

    @NonNull
    public static ApiResult<Void> error(@NonNull RcEnum rc) {
        return new ApiResult<>(rc, Boolean.FALSE);
    }
}
