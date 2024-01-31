package com.hlqz.lpg.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author Karbob
 * @date 2023-11-21
 */
@Getter
@AllArgsConstructor
public enum RcEnum {

    /**
     * 成功
     */
    OK("200", "OK"),

    /**
     * 业务异常
     */
    BIZ_ERROR("400", "Biz Error!"),

    /**
     * 未知错误
     */
    UNKNOWN_ERROR("500", "Service Error!"),

    /**
     * 请求参数缺失
     */
    REQUEST_PARAMETER_MISSING("10001", "请求参数 '{}' 缺失!"),

    /**
     * 请求方法不支持
     */
    REQUEST_METHOD_NOT_SUPPORTED("10002", "不支持 '{}' 请求!"),

    /**
     * 请求参数不合法
     */
    REQUEST_PARAMETER_NOT_VALID("10003", "参数校验失败!"),

    /**
     * 上传文件大小超过限制
     */
    MAXIMUM_UPLOAD_SIZE_EXCEEDED("10004", "文件大小超过 '{}' 或所有文件大小之和超过 '{}'"),
    ;

    /**
     * 全局错误码
     */
    private final String code;

    /**
     * 错误详情
     */
    private final String message;

    private static final Pattern PLACEHOLDER = Pattern.compile("\\{}");

    public String getMessage(String... messages) {
        String result = this.message;
        if (!Objects.isNull(messages)) {
            for (String message : messages) {
                result = PLACEHOLDER.matcher(result).replaceFirst(message == null ? StringUtils.EMPTY : message);
            }
        }
        return result;
    }
}
