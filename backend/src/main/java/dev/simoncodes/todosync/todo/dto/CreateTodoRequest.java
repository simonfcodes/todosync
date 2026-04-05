package dev.simoncodes.todosync.todo.dto;

import jakarta.annotation.Nullable;
import dev.simoncodes.todosync.entity.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record CreateTodoRequest(
        @NotBlank
        @Size(min = 1, message = "Todo list titles require at least one character")
        String title,
        String description,
        String listId,
        Instant dueDate,
        Priority priority
) {}