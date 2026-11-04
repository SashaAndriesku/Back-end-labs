package org.example.lab3.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.lab3.annotation.validator.TaskDatesValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TaskDatesValidator.class)
public @interface ValidTaskDates {
    String message() default "Дата завершення (dueDate) не може бути раніше за дату створення (createdAt)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}