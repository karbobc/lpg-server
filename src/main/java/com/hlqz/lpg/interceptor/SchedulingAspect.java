package com.hlqz.lpg.interceptor;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.hlqz.lpg.constant.MdcKeyConstants;
import com.hlqz.lpg.service.util.NtfyUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * @author Karbob
 * @date 2024-01-31
 */
@Slf4j
@Aspect
@Component
public class SchedulingAspect {

    @Pointcut("execution(protected * com.hlqz.lpg.task.AbstractTask.execute(..))")
    private void pointcut() {}

    @Around("pointcut()")
    private Object around(ProceedingJoinPoint point) {
        final var traceId = IdUtil.fastSimpleUUID();
        MDC.put(MdcKeyConstants.TRACE_ID, traceId);
        log.info("scheduling start, class: {}", point.getTarget().getClass().getSimpleName());
        try {
            final var result = point.proceed();
            log.info("scheduling end");
            return result;
        } catch (Throwable e) {
            log.error("scheduling error, ", e);
            NtfyUtils.sendMessage(StrUtil.format("scheduling error, traceId: {}", traceId));
        } finally {
            MDC.clear();
        }
        return null;
    }
}
