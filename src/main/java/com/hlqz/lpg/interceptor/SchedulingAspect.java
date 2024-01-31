package com.hlqz.lpg.interceptor;

import cn.hutool.core.util.IdUtil;
import com.hlqz.lpg.constant.MdcKeyConstants;
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

    @Pointcut("execution(* com.hlqz.lpg.task.AbstractTask.execute(..))")
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
        } finally {
            MDC.clear();
        }
        return null;
    }
}
