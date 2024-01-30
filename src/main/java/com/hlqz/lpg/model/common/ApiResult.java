package com.hlqz.lpg.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hlqz.lpg.model.enums.RcEnum;
import lombok.Data;
import org.springframework.lang.NonNull;

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
     * 请求 ID
     */
    private String requestId;

    /**
     * 回传数据
     */
    private T data;

    public ApiResult() {
    }

    public ApiResult(String code, String message) {
        this.code = code;
        this.message = message;
        this.success = Boolean.TRUE;
    }

    public ApiResult(String code, String message, Boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public ApiResult(String code, String message, Boolean success, String requestId) {
        this(code, message, success);
        this.requestId = requestId;
    }

    public ApiResult(String code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    public ApiResult(@NonNull RcEnum rc) {
        this(rc.getCode(), rc.getMessage(), rc == RcEnum.OK);
    }

    public ApiResult(@NonNull RcEnum rc, Boolean success) {
        this(rc.getCode(), rc.getMessage(), success);
    }

    public ApiResult(@NonNull RcEnum rc, Boolean success, String requestId) {
        this(rc, success);
        this.requestId = requestId;
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
    public static ApiResult<Void> error(String code, String message, String requestId) {
        return new ApiResult<>(code, message, Boolean.FALSE, requestId);
    }

    @NonNull
    public static ApiResult<Void> error(@NonNull RcEnum rc) {
        return new ApiResult<>(rc, Boolean.FALSE);
    }

    @NonNull
    public static ApiResult<Void> error(@NonNull RcEnum rc, String requestId) {
        return new ApiResult<>(rc, Boolean.FALSE, requestId);
    }
}
