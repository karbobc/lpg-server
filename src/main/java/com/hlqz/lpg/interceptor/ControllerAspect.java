package com.hlqz.lpg.interceptor;

import cn.hutool.core.util.IdUtil;
import com.hlqz.lpg.constant.HeaderConstants;
import com.hlqz.lpg.constant.MdcKeyConstants;
import com.hlqz.lpg.model.common.ApiResult;
import com.hlqz.lpg.util.JsonUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private void before(JoinPoint point) {
        // 存储请求开始时间
        MDC.put(MdcKeyConstants.REQUEST_START_TIME, String.valueOf(System.currentTimeMillis()));
        // 请求内容处理
        final var request = httpServletRequest instanceof ContentCachingRequestWrapper ?
            (ContentCachingRequestWrapper) httpServletRequest : new ContentCachingRequestWrapper(httpServletRequest);
        final var method = request.getMethod();
        final var url = request.getRequestURL();
        final var queryString = StringUtils.defaultIfBlank(request.getQueryString(), "[Empty Query]");
        // multipart file 特殊处理
        final var args = Stream.of(point.getArgs())
            .map(o -> {
                if (o instanceof MultipartFile file) {
                    return StringUtils.join("file: ", file.getOriginalFilename(), "(", file.getSize(), " byte)");
                }
                if (o instanceof MultipartFile[] files) {
                    return StringUtils.join("files: ", Stream.of(files)
                        .map(file -> StringUtils.join(file.getOriginalFilename(), "(", file.getSize(), " byte)"))
                        .collect(Collectors.joining("|")));
                }
                return o;
            })
            .toList();
        // final var payload = CollectionUtils.isEmpty(args) ? "[Empty Body]" : JsonUtils.toJson(args);
        final var payload = CollectionUtils.isEmpty(args) ? "[Empty Body]" : Arrays.toString(args.toArray());
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
