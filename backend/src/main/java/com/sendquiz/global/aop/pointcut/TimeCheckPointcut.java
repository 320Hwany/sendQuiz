package com.sendquiz.global.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class TimeCheckPointcut {

    @Pointcut("execution(* com.sendquiz..repository.*.*(..))")
    public void allRepository() {}

    @Pointcut("execution(* com.sendquiz..application.*.*(..))")
    public void allService() {}

    @Pointcut("execution(* com.sendquiz..presentation.*.*(..))")
    public void allPresentation() {}
}
