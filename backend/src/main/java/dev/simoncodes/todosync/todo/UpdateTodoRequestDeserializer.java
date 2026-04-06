package dev.simoncodes.todosync.todo;

import dev.simoncodes.todosync.entity.Priority;
import dev.simoncodes.todosync.todo.dto.UpdateTodoRequest;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;

import java.time.Instant;
import java.util.Optional;


public class UpdateTodoRequestDeserializer extends StdDeserializer<UpdateTodoRequest> {

    public UpdateTodoRequestDeserializer() {
        super(UpdateTodoRequest.class);
    }

    @Override
    public UpdateTodoRequest deserialize(JsonParser jp, DeserializationContext dc) {
        JsonNode node = jp.readValueAsTree();
        String title = node.has("title") ? node.get("title").asString() : null;
        Optional<String> description = resolveOptionalString(node, "description");
        Boolean isComplete = node.has("isComplete") ? node.get("isComplete").asBoolean() : null;
        Priority priority = node.has("priority") ? Priority.valueOf(node.get("priority").asString()) : null;
        Optional<Instant> dueDate = resolveOptionalInstant(node, "dueDate");

        return new UpdateTodoRequest(
                title,
                description,
                isComplete,
                priority,
                dueDate
        );
    }

    private Optional<String> resolveOptionalString(JsonNode node, String parameter) {
        if (!node.has(parameter)) return null;
        if (node.get(parameter).isNull()) return Optional.empty();
        return Optional.of(node.get(parameter).asString());
    }

    private Optional<Instant> resolveOptionalInstant(JsonNode node, String parameter) {
        if (!node.has(parameter)) return null;
        if (node.get(parameter).isNull()) return Optional.empty();
        return Optional.of(Instant.parse(node.get(parameter).asString()));
    }
}
