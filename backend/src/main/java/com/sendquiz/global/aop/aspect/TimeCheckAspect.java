package com.sendquiz.global.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class TimeCheckAspect {

    @Around("com.sendquiz.global.aop.pointcut.TimeCheckPointcut.allPresentation()")
    public Object presentationTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long methodStartTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        String name = joinPoint.getSignature().getName();
        long methodEndTime = System.currentTimeMillis();
        log.info("presentation methodName={}, time={}", name, methodEndTime - methodStartTime);
        return result;
    }

    @Around("com.sendquiz.global.aop.pointcut.TimeCheckPointcut.sendRandomQuizList()")
    public Object sendRandomQuizListTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long methodStartTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        String name = joinPoint.getSignature().getName();
        long methodEndTime = System.currentTimeMillis();
        log.info("sendRandomQuizListTimeLog time={}", methodEndTime - methodStartTime);
        return result;
    }
}
