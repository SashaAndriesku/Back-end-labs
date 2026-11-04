package org.example.lab4.aspect;

import org.example.lab4.annotation.RequireRole;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AccessControlAspect {

    @Getter
    @Setter
    private String currentUserRole = "GUEST";

    @Before("@annotation(requireRole)")
    public void checkAccess(JoinPoint joinPoint, RequireRole requireRole) {
        log.info("[SECURITY] Checking access role. Required: {}, Current: {}", requireRole.value(), currentUserRole);
        if (!requireRole.value().equals(currentUserRole)) {
            throw new SecurityException("Access denied! Required role: " + requireRole.value());
        }
    }
}