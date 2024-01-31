package com.hlqz.lpg.interceptor;

import cn.hutool.core.util.IdUtil;
import com.hlqz.lpg.constant.HeaderConstants;
import com.hlqz.lpg.constant.MdcKeyConstants;
import com.hlqz.lpg.model.common.ApiResult;
import com.hlqz.lpg.util.JsonUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.UnsupportedEncodingException;

/**
 * @author Karbob
 * @date 2023-11-20
 */
@Slf4j
@Aspect
@Component
public class ControllerAspect {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Pointcut("execution(public * com.hlqz.lpg.controller.*.*(..))")
    private void pointcut() {
    }

    @Before("pointcut()")
    private void before() {
        // 存储请求开始时间
        MDC.put(MdcKeyConstants.REQUEST_START_TIME, String.valueOf(System.currentTimeMillis()));
        // 请求内容处理
        final var request = httpServletRequest instanceof ContentCachingRequestWrapper ?
            (ContentCachingRequestWrapper) httpServletRequest : new ContentCachingRequestWrapper(httpServletRequest);
        final var method = request.getMethod();
        final var url = request.getRequestURL();
        final var queryString = StringUtils.defaultIfBlank(request.getQueryString(), "[Empty Query]");
        // TODO 过滤 multipart file
        var payload = "[Empty Content]";
        if (request.getContentLength() > 0) {
            try {
                // 超过 8192 字节的入参进行截断
                payload = new String(request.getContentAsByteArray(), 0, Math.min(request.getContentLength(), 8192), request.getCharacterEncoding());
            } catch (UnsupportedEncodingException e) {
                payload = "[Unsupported Content]";
            }
        }
        final var traceId = StringUtils.defaultIfBlank(request.getHeader(HeaderConstants.X_REQUEST_ID), IdUtil.fastSimpleUUID());
        // 存储链路 ID
        MDC.put(MdcKeyConstants.TRACE_ID, traceId);
        log.info("request start, {} {}, query: {}, payload: {}", method, url, queryString, payload);
    }

    @AfterReturning(value = "pointcut()", returning = "object")
    private void afterReturning(Object object) {
        if (object instanceof ApiResult<?> result) {
            final var traceId = MDC.get(MdcKeyConstants.TRACE_ID);
            result.setRequestId(traceId);
        }
        final var startTime = NumberUtils.toLong(MDC.get(MdcKeyConstants.REQUEST_START_TIME));
        final var endTime = System.currentTimeMillis();
        final var cost = endTime - startTime;
        log.info("request end, result: {}, cost: {}ms", JsonUtils.toJson(object), cost);
        MDC.clear();
    }
}
