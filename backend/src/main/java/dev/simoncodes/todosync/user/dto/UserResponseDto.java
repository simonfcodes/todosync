package dev.simoncodes.todosync.user.dto;

import dev.simoncodes.todosync.entity.User;
import dev.simoncodes.todosync.id.IdTranslator;

import java.util.UUID;

public record UserResponseDto(String id, String email) {
    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                IdTranslator.encodeUuidToBase62(user.getId()),
                user.getEmail()
        );
    }
}