package dev.simoncodes.todosync.dto;

import java.util.UUID;

public record UserResponseDto(UUID userId, String email) {}