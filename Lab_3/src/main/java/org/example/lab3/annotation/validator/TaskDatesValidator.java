package org.example.lab3.annotation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.lab3.annotation.ValidTaskDates;
import org.example.lab3.dto.TaskDto;

public class TaskDatesValidator implements ConstraintValidator<ValidTaskDates, TaskDto> {

    @Override
    public boolean isValid(TaskDto dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getCreatedAt() == null || dto.getDueDate() == null) {
            return true;
        }
        return !dto.getDueDate().isBefore(dto.getCreatedAt());
    }
}