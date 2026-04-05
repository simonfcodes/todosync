package dev.simoncodes.todosync.todo.dto;

import dev.simoncodes.todosync.id.IdTranslator;

import java.util.UUID;

public record TodoDeleteResponse(
        String id
) {
    public static TodoDeleteResponse of(UUID id) {
        return new TodoDeleteResponse(
                IdTranslator.encodeUuidToBase62(id)
        );
    }
}
