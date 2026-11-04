package org.example.lab4.aspect;

import org.example.lab4.annotation.AuditLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AuditLogAspect {

    @AfterReturning(pointcut = "@annotation(auditLog)", returning = "result")
    public void logAudit(JoinPoint joinPoint, AuditLog auditLog, Object result) {
        log.info("[AUDIT] Action: '{}' successfully logged for method '{}'. Result: {}",
                auditLog.action(),
                joinPoint.getSignature().getName(),
                result);
    }
}