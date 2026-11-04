package org.example.lab4;

import org.example.lab4.aspect.AccessControlAspect;
import org.example.lab4.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Lab4Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab4Application.class, args);
    }

    @Bean
    public CommandLineRunner testAop(OrderService orderService, AccessControlAspect accessControlAspect) {
        return args -> {
            System.out.println("\n=======================================================");
            System.out.println("  TEST 1: Successful execution (Role: ADMIN)");
            System.out.println("=======================================================");
            accessControlAspect.setCurrentUserRole("ADMIN");
            orderService.createOrder("LAPTOP-999", 2);

            System.out.println("\n=======================================================");
            System.out.println("  TEST 2: Self-Invocation Demonstration (this.createOrder)");
            System.out.println("=======================================================");
            orderService.processOrderWithSelfInvocation("PHONE-123", 1);

            System.out.println("\n=======================================================");
            System.out.println("  TEST 3: Access Denied Error (Role: USER)");
            System.out.println("=======================================================");
            accessControlAspect.setCurrentUserRole("USER");
            try {
                orderService.createOrder("TABLET-555", 3);
            } catch (SecurityException e) {
                System.out.println("-> Caught security exception: " + e.getMessage());
            }
            System.out.println("=======================================================\n");
        };
    }
}