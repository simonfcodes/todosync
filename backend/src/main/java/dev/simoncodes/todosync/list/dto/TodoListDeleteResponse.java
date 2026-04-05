package dev.simoncodes.todosync.list.dto;

import dev.simoncodes.todosync.id.IdTranslator;

import java.util.UUID;

public record TodoListDeleteResponse(
        String id
) {
    public static TodoListDeleteResponse of(UUID listId) {
        return new TodoListDeleteResponse(
                IdTranslator.encodeUuidToBase62(listId)
        );
    }
}
