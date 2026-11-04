package org.example.lab3;

import org.example.lab3.config.CurrentUserIdArgumentResolver;
import org.example.lab3.controller.TaskController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@Import(CurrentUserIdArgumentResolver.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Успішне створення (201 Created) з валідною датою та X-User-Id")
    void testValidRequest_returns201CreatedAndUserId() throws Exception {
        String validJson = """
            {
              "title": "Clean Architecture Task",
              "createdAt": "2026-03-01T10:00:00",
              "dueDate": "2026-03-05T18:00:00"
            }
            """;

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-User-Id", "42")
                        .content(validJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.createdById").value(42))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    @DisplayName("Помилка валідації (400 Bad Request), коли dueDate раніше ніж createdAt")
    void testInvalidDates_returns400BadRequest() throws Exception {
        String invalidJson = """
            {
              "title": "Invalid Dates Task",
              "createdAt": "2026-03-10T10:00:00",
              "dueDate": "2026-03-01T10:00:00"
            }
            """;

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-User-Id", "42")
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}