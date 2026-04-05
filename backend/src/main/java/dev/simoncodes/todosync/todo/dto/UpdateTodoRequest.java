package dev.simoncodes.todosync.todo.dto;

import dev.simoncodes.todosync.entity.Priority;

import java.time.Instant;
import java.util.Optional;

public record UpdateTodoRequest(
        String title,
        Optional<String> description,
        Boolean isComplete,
        Priority priority,
        Optional<Instant> dueDate
) {}