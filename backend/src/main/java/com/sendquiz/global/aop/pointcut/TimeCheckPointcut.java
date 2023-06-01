package com.sendquiz.global.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class TimeCheckPointcut {

    @Pointcut("execution(* com.sendquiz..presentation.*.*(..))")
    public void allPresentation() {}

    @Pointcut("execution(* com.sendquiz.quiz.application.QuizQuery.sendRandomQuizList(..))")
    public void sendRandomQuizList() {}
}
