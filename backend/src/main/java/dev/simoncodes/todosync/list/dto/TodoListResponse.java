package dev.simoncodes.todosync.list.dto;

import dev.simoncodes.todosync.entity.TodoList;
import dev.simoncodes.todosync.id.IdTranslator;

import java.time.Instant;
import java.util.UUID;

public record TodoListResponse(
        String listId,
        String name,
        Instant createdAt
) {
    public static TodoListResponse from(TodoList entity) {
        return new TodoListResponse(
                IdTranslator.encodeUuidToBase62(entity.getId()),
                entity.getName(),
                entity.getCreatedAt()
        );
    }
}