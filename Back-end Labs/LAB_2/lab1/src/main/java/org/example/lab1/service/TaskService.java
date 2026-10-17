package org.example.lab1.service;

import org.example.lab1.dto.TaskDto;
import java.util.List;

public interface TaskService {
    List<TaskDto> findAll();
    TaskDto findById(Long id);
    TaskDto create(TaskDto dto);
    TaskDto update(Long id, TaskDto dto);
    void delete(Long id);
}