package com.hlqz.lpg.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author Karbob
 * @date 2023-11-20
 */
@Slf4j
@Aspect
@Component
public class ControllerLogAspect {

    @Pointcut("execution(public * com.hlqz.lpg.controller.*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before() {
        long start = System.currentTimeMillis();
    }

    @AfterReturning("pointcut()")
    public void afterReturning() {
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
    }
}
