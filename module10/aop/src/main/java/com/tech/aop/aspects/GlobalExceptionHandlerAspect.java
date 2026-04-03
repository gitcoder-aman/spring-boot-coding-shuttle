package com.tech.aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GlobalExceptionHandlerAspect {

    @AfterThrowing(
            pointcut = "execution(* com.tech.aop..*(..))",
            throwing = "exception"
    )
    public void handleException(
            JoinPoint joinPoint,
            Exception exception
    ){
        System.out.println("Exception occurred in: "+joinPoint.getSignature().getName());
        System.out.println("Exception message: "+exception.getMessage());
    }
}
