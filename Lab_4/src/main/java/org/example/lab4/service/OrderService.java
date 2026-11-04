package org.example.lab4.service;

import org.example.lab4.annotation.AuditLog;
import org.example.lab4.annotation.ExecutionTimer;
import org.example.lab4.annotation.RequireRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    @RequireRole("ADMIN")
    @ExecutionTimer
    @AuditLog(action = "CREATE_ORDER")
    public String createOrder(String item, int amount) {
        log.info("Processing order creation: item={}, amount={}", item, amount);
        return "ORDER-" + item + "-" + amount;
    }

    public void processOrderWithSelfInvocation(String item, int amount) {
        log.info("--- Executing via self-invocation (this.createOrder) ---");
        this.createOrder(item, amount);
    }
}