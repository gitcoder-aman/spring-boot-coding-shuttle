package com.tech.aop.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

//    @Before("execution(* orderPackage(..))")
//    @Before("execution(* com.tech.aop.services.impl.*.orderPackage(..))")
    @Before("execution(* com.tech.aop.services.impl.*.*(..))")
    public void beforeOrderPackage(JoinPoint joinPoint) {
        log.info("Before called from LoggingAspect kind, {}", joinPoint.getKind());
        log.info("Before called from LoggingAspect signature, {}", joinPoint.getSignature());
    }

    @After("myLoggingAndAopMethodsPointCut()")
    public void afterMyLoggingAndAopMethodsPointCut() {
        log.info("After My Logging Annotation calls");
    }

    @Before("within(com.tech.aop..*)")
    public void beforeServiceImplCalls() {
        log.info("Service Impl calls");
    }

    @Before("myLoggingAndAopMethodsPointCut()")
    public void beforeTransactionalAnnotationCalls() {
        log.info("Before My Logging Annotation calls");
    }


    @Pointcut("@annotation(com.tech.aop.aspects.MyLogging) && within(com.tech.aop..*)")
    public void myLoggingAndAopMethodsPointCut() {
    }

}
