package dev.simoncodes.todosync.user.dto;

import java.util.UUID;

public record UserResponseDto(UUID userId, String email) {}