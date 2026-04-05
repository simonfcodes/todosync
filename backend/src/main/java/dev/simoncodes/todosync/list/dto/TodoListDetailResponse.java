package dev.simoncodes.todosync.list.dto;

import dev.simoncodes.todosync.entity.Todo;
import dev.simoncodes.todosync.entity.TodoList;
import dev.simoncodes.todosync.id.IdTranslator;
import dev.simoncodes.todosync.todo.dto.TodoResponse;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record TodoListDetailResponse(
        String id,
        String name,
        Instant createdAt,
        List<TodoResponse> todos
) {
    public static TodoListDetailResponse from(TodoList entity, List<Todo> todos) {
        return new TodoListDetailResponse(
                IdTranslator.encodeUuidToBase62(entity.getId()),
                entity.getName(),
                entity.getCreatedAt(),
                todos.stream().map(TodoResponse::from).toList()
        );
    }
}
