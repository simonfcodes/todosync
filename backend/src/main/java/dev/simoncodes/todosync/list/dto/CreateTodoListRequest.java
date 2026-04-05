package dev.simoncodes.todosync.list.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTodoListRequest(
        @Size(min = 1, message = "A list requires at least one character in its name")
        @NotBlank
        String name
) {}