package com.tech.aop.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Aspect
@Component
public class ExecutionTimeAspect {

    private final Map<String, Long> report = new ConcurrentHashMap<>();

    @Around("@annotation(com.tech.aop.annotation.TrackExecutionTime)")
    public Object measureExecutionTime(
            ProceedingJoinPoint joinPoint)
            throws Throwable {

        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;

        String methodName =
                joinPoint.getSignature().toShortString();

        report.put(methodName, executionTime);

        System.out.println(
                methodName +
                        " executed in " +
                        executionTime +
                        " ms"
        );

        return result;
    }

    // Method to print report
    public void printReport() {
        System.out.println("\nExecution Time Report:");

        report.forEach((method, time) ->
                System.out.println(
                        method + " : " + time + " ms"
                )
        );
    }
}