package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j // log 사용을 위한 annotation
@Component // bean 등록을 위한 annotation
@Aspect // AOP bean 등록을 위한 annotation
public class TimeTraceAspect {
    
    @Pointcut("@annotation(com.example.demo.aop.TimeTrace)") // @TimeTrace annotation
    private void timeTracePointcut() {}

    @Around("timeTracePointcut()")
    public Object traceTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            log.info("{} - Time Elapsed = {}s",
                joinPoint.getSignature().toShortString(),
                stopWatch.getTotalTimeSeconds());
        }
    }

}
