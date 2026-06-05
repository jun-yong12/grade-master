package kr.ac.tukorea.grade.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * AOP — 서비스 메서드 실행 시간 측정
 * kr.ac.tukorea.grade.service 패키지의 모든 메서드에 적용
 */
@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {

    @Around("execution(* kr.ac.tukorea.grade.service.*.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();
        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;
            log.info("[AOP] {} 실행 시간: {}ms", methodName, elapsed);
            return result;
        } catch (Throwable e) {
            long elapsed = System.currentTimeMillis() - start;
            log.warn("[AOP] {} 예외 발생 ({}ms): {}", methodName, elapsed, e.getMessage());
            throw e;
        }
    }
}
