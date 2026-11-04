package org.example.lab3.controller;

import jakarta.validation.Valid;
import org.example.lab3.annotation.CurrentUserId;
import org.example.lab3.annotation.PostJsonCreated;
import org.example.lab3.dto.TaskDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @PostJsonCreated
    public Map<String, Object> createTask(
            @Valid @RequestBody TaskDto taskDto,
            @CurrentUserId Long userId
    ) {
        Map<String, Object> response = new HashMap<>();
        response.put("task", taskDto);
        response.put("createdById", userId);
        response.put("status", "SUCCESS");
        return response;
    }
}