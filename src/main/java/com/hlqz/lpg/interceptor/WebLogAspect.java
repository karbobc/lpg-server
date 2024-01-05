package com.hlqz.lpg.interceptor;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author Karbob
 * @date 2023-11-20
 */
@Aspect
@Component
public class WebLogAspect {

    @Pointcut("execution(public * com.hlqz.lpg.controller.*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void before() {
    }

    @AfterReturning("webLog()")
    public void afterReturning() {
    }

    @AfterThrowing("webLog()")
    public void afterThrowing() {
    }
}
