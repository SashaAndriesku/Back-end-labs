package org.example.lab1.mapper;

import org.example.lab1.dto.TaskDto;
import org.example.lab1.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task entity);
    Task toEntity(TaskDto dto);
}