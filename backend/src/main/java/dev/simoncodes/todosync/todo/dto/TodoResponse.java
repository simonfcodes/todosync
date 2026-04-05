package dev.simoncodes.todosync.todo.dto;

import dev.simoncodes.todosync.entity.Priority;
import dev.simoncodes.todosync.entity.Todo;
import dev.simoncodes.todosync.entity.TodoList;
import dev.simoncodes.todosync.id.IdTranslator;

import java.time.Instant;
import java.util.UUID;

public record TodoResponse(
        String id,
        String listId,
        String title,
        String description,
        boolean isComplete,
        Priority priority,
        Instant dueDate,
        Instant createdAt,
        Instant updatedAt
) {
    public static TodoResponse from(Todo entity) {
        TodoList todoList = entity.getTodoList();
        return new TodoResponse(
                IdTranslator.encodeUuidToBase62(entity.getId()),
                todoList == null ? null : IdTranslator.encodeUuidToBase62(todoList.getId()),
                entity.getTitle(),
                entity.getDescription(),
                entity.getComplete(),
                entity.getPriority(),
                entity.getDueDate(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}