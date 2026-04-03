package com.tech.aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityLoggingAspect {

    // BEFORE Advice — Logging before method execution
    @Before("execution(* com.tech.aop.services.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println(
                "Method started: " +
                        joinPoint.getSignature().getName()
        );
    }

    // AFTER Advice — Logging after method execution
    @After("execution(* com.tech.aop.services.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println(
                "Method finished: " +
                        joinPoint.getSignature().getName()
        );
    }
    // AROUND Advice — Security check
    @Around("execution(* com.tech.aop.services.*.*(..))")
    public Object checkSecurity(
            ProceedingJoinPoint joinPoint)
            throws Throwable {

        // Simulated token
        String token = getToken();

        if (!isValidToken(token)) {
            throw new RuntimeException(
                    "Invalid Security Token"
            );
        }

        return joinPoint.proceed();
    }
    // Dummy method to simulate token retrieval
    private String getToken() {
        return "VALID_TOKEN";
    }

    // Token validation logic
    private boolean isValidToken(String token) {
        return "VALID_TOKEN".equals(token);
    }

}
