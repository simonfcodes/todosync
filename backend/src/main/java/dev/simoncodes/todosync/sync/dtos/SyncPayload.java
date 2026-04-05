package dev.simoncodes.todosync.sync.dtos;

import dev.simoncodes.todosync.sync.enums.ActionType;
import dev.simoncodes.todosync.sync.enums.EntityType;

public record SyncPayload(
        EntityType entity,
        ActionType action,
        Object data
) {
}
