package dev.simoncodes.todosync.todo.dto;

import dev.simoncodes.todosync.entity.Priority;
import dev.simoncodes.todosync.todo.UpdateTodoRequestDeserializer;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.time.Instant;
import java.util.Optional;

@JsonDeserialize(using = UpdateTodoRequestDeserializer.class)
public record UpdateTodoRequest(
        String title,
        Optional<String> description,
        Boolean isComplete,
        Priority priority,
        Optional<Instant> dueDate
) { }