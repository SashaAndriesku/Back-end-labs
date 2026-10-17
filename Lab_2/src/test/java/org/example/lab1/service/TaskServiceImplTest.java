package org.example.lab1.service;

import org.example.lab1.dto.TaskDto;
import org.example.lab1.entity.Task;
import org.example.lab1.mapper.TaskMapper;
import org.example.lab1.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskDto taskDto;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Тестова задача");
        task.setDescription("Опис тестової задачі");
        task.setStatus("NEW");

        taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setTitle("Тестова задача");
        taskDto.setDescription("Опис тестової задачі");
        taskDto.setStatus("NEW");
    }



    @Test
    @DisplayName("findAll: повертає список DTO при наявності задач")
    void findAll_tasksExist_returnsListOfTaskDtos() {
        when(taskRepository.findAll()).thenReturn(List.of(task));
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        List<TaskDto> result = taskService.findAll();

        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Тестова задача");
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findAll: повертає порожній список, якщо задач немає")
    void findAll_noTasksExist_returnsEmptyList() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        List<TaskDto> result = taskService.findAll();

        assertThat(result).isEmpty();
        verify(taskRepository, times(1)).findAll();
        verifyNoInteractions(taskMapper);
    }



    @Test
    @DisplayName("findById: повертає TaskDto за існуючим ID")
    void findById_existingId_returnsTaskDto() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        TaskDto result = taskService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById: кидає виняток, якщо ID не знайдено")
    void findById_nonExistingId_throwsRuntimeException() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.findById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Task not found with id: 99");

        verify(taskRepository, times(1)).findById(99L);
        verifyNoInteractions(taskMapper);
    }



    @Test
    @DisplayName("create: зберігає задачу та використовує ArgumentCaptor для перевірки побічного ефекту")
    void create_validTaskDto_savesAndReturnsTaskDto() {
        when(taskMapper.toEntity(taskDto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        TaskDto result = taskService.create(taskDto);

        assertThat(result).isNotNull();

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository, times(1)).save(taskCaptor.capture());

        Task capturedTask = taskCaptor.getValue();
        assertThat(capturedTask.getTitle()).isEqualTo("Тестова задача");
        assertThat(capturedTask.getStatus()).isEqualTo("NEW");
    }



    @Test
    @DisplayName("update: успішно оновлює та повертає задачу за існуючим ID")
    void update_existingId_returnsUpdatedTaskDto() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        TaskDto result = taskService.update(1L, taskDto);

        assertThat(result).isNotNull();
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("update: кидає виняток і не здійснює збереження для неіснуючого ID")
    void update_nonExistingId_throwsExceptionAndNeverSaves() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.update(99L, taskDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Task not found with id: 99");

        verify(taskRepository, times(1)).findById(99L);
        verify(taskRepository, never()).save(any());
    }



    @Test
    @DisplayName("delete: викликає deleteById в репозиторії за вказаним ID")
    void delete_validId_deletesTaskSuccessfully() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.delete(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }
}