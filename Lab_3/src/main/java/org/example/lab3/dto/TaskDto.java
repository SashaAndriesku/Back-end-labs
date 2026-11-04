package org.example.lab3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.lab3.annotation.ValidTaskDates;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidTaskDates
public class TaskDto {
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }
}