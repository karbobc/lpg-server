package com.hlqz.lpg.interceptor;

import com.hlqz.lpg.exception.BizException;
import com.hlqz.lpg.model.common.ApiResult;
import com.hlqz.lpg.model.enums.RcEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(BizException.class)
    public ApiResult<Void> handleBizException(HttpServletRequest request, BizException e) {
        log.warn("业务异常, JSESSIONID: {}", request.getSession().getId(), e);
        return ApiResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ApiResult<Void> handleBizException(HttpServletRequest request, NullPointerException e) {
        log.error("空指针异常, JSESSIONID: {}", request.getSession().getId(), e);
        return ApiResult.error(RcEnum.BIZ_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResult<Void> handleMissingServletRequestParameterException(HttpServletRequest request,
                                                                         MissingServletRequestParameterException e) {
        log.warn("缺少请求参数: JSESSIONID: {}", request.getSession().getId(), e);
        RcEnum codeMessage = RcEnum.REQUEST_PARAMETER_MISSING;
        return ApiResult.error(codeMessage.getCode(), codeMessage.getMessage(e.getParameterName()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResult<Void> handleHttpRequestMethodNotSupportedException(HttpServletRequest request,
                                                                        HttpRequestMethodNotSupportedException e) {
        log.warn("请求方法不支持: JSESSIONID: {}", request.getSession().getId(), e);
        RcEnum codeMessage = RcEnum.REQUEST_METHOD_NOT_SUPPORTED;
        return ApiResult.error(codeMessage.getCode(), codeMessage.getMessage(e.getMethod()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResult<Void> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException e) {
        log.warn("请求参数不合法, JSESSIONID: {}", request.getSession().getId(), e);
        return ApiResult.error(RcEnum.REQUEST_PARAMETER_NOT_VALID);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ApiResult<Void> handleValidationException(HttpServletRequest request, Exception e) {
        log.warn("参数校验失败, JSESSIONID: {}", request.getSession().getId(), e);
        // 转化错误信息
        String errorMessage;
        if (e instanceof MethodArgumentNotValidException e1) {
            errorMessage = e1.getFieldErrors()
                .stream()
                .map(o -> StringUtils.join(o.getField(), ": ", o.getDefaultMessage()))
                .collect(Collectors.joining("; "));
        } else if (e instanceof ConstraintViolationException e2) {
            errorMessage = e2.getConstraintViolations()
                .stream()
                .map(o -> StringUtils.join(o.getPropertyPath().toString(), ": ", o.getMessage()))
                .collect(Collectors.joining("; "));
        } else {
            errorMessage = e.getMessage();
        }
        return ApiResult.error(RcEnum.REQUEST_PARAMETER_NOT_VALID.getCode(), errorMessage);
    }

    @ExceptionHandler(Throwable.class)
    public ApiResult<Void> handleException(HttpServletRequest request, Throwable e) {
        log.error("全局异常捕获, 未处理异常, JSESSIONID: {}", request.getSession().getId(), e);
        return ApiResult.error(RcEnum.UNKNOWN_ERROR);
    }
}
