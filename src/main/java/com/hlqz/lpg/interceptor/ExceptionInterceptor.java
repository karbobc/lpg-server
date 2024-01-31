package com.hlqz.lpg.interceptor;

import cn.hutool.extra.spring.SpringUtil;
import com.hlqz.lpg.constant.MdcKeyConstants;
import com.hlqz.lpg.exception.BizException;
import com.hlqz.lpg.model.common.ApiResult;
import com.hlqz.lpg.model.enums.RcEnum;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.MDC;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.stream.Collectors;

/**
 * @author Karbob
 * @date 2023-11-21
 *
 * 全局异常拦截
 */
@Slf4j
@RestControllerAdvice
public class ExceptionInterceptor {

    @ExceptionHandler({
        BizException.class,
        NullPointerException.class,
        MissingServletRequestParameterException.class,
        HttpRequestMethodNotSupportedException.class,
        IllegalArgumentException.class,
        MethodArgumentNotValidException.class,
        ConstraintViolationException.class,
        MaxUploadSizeExceededException.class,
        Throwable.class,
    })
    public ApiResult<Void> handleNormalException(Throwable e) {
        final var traceId = MDC.get(MdcKeyConstants.TRACE_ID);
        final var startTime = NumberUtils.toLong(MDC.get(MdcKeyConstants.REQUEST_START_TIME));
        final var endTime = System.currentTimeMillis();
        final var cost = startTime != 0 ? endTime - startTime : 0;
        final ApiResult<Void> result;
        switch (e) {
            case BizException ex -> {
                log.warn("全局异常捕获, 业务异常, cost: {}ms, error: ", cost, ex);
                result = ApiResult.error(ex.getCode(), ex.getMessage(), traceId);
            }
            case NullPointerException ex ->  {
                log.error("全局异常捕获, 空指针异常, cost: {}ms, error: ", cost, ex);
                result = ApiResult.error(RcEnum.BIZ_ERROR.getCode(), ex.getMessage(), traceId);
            }
            case MissingServletRequestParameterException ex -> {
                log.warn("全局异常捕获, 缺少请求参数, cost: {}ms, error: ", cost, ex);
                final var rc = RcEnum.REQUEST_PARAMETER_MISSING;
                result = ApiResult.error(rc.getCode(), rc.getMessage(ex.getParameterName()), traceId);
            }
            case HttpRequestMethodNotSupportedException ex -> {
                log.warn("全局异常捕获, 请求方法不支持, cost: {}ms, error: ", cost, ex);
                final var rc = RcEnum.REQUEST_METHOD_NOT_SUPPORTED;
                result = ApiResult.error(rc.getCode(), rc.getMessage(ex.getMethod()), traceId);
            }
            case IllegalArgumentException ex -> {
                log.warn("全局异常捕获, 请求参数不合法, cost: {}ms, error: ", cost, ex);
                result = ApiResult.error(RcEnum.REQUEST_PARAMETER_NOT_VALID, traceId);
            }
            case MethodArgumentNotValidException ex -> {
                log.warn("全局异常捕获, 参数校验失败, cost: {}ms, error: ", cost, ex);
                final var message = ex.getFieldErrors()
                    .stream()
                    .map(o -> StringUtils.join(o.getField(), ": ", o.getDefaultMessage()))
                    .collect(Collectors.joining("; "));
                final var rc = RcEnum.REQUEST_PARAMETER_NOT_VALID;
                return ApiResult.error(rc.getCode(), message, traceId);
            }
            case ConstraintViolationException ex -> {
                log.warn("全局异常捕获, 参数校验失败, cost: {}ms, error: ", cost, ex);
                final var message = ex.getConstraintViolations()
                    .stream()
                    .map(o -> StringUtils.join( o.getPropertyPath().toString(), ": ", o.getMessage()))
                    .collect(Collectors.joining("; "));
                final var rc = RcEnum.REQUEST_PARAMETER_NOT_VALID;
                return ApiResult.error(rc.getCode(), message, traceId);
            }
            case MaxUploadSizeExceededException ex -> {
                log.warn("全局异常捕获, 上传文件大小超出限制, cost: {}ms, error: ", cost, ex);
                final var rc = RcEnum.MAXIMUM_UPLOAD_SIZE_EXCEEDED;
                final var maxFileSize = SpringUtil.getProperty("spring.servlet.multipart.max-file-size");
                final var maxRequestSize = SpringUtil.getProperty("spring.servlet.multipart.max-request-size");
                return ApiResult.error(rc.getCode(), rc.getMessage(maxFileSize, maxRequestSize), traceId);
            }
            default -> {
                log.error("全局异常捕获, 未处理异常, cost: {}ms, error: ", cost, e);
                result = ApiResult.error(RcEnum.UNKNOWN_ERROR, traceId);
            }
        }
        MDC.clear();
        return result;
    }
}
