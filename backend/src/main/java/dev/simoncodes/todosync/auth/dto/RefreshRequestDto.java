package dev.simoncodes.todosync.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefreshRequestDto(
        @JsonProperty("refresh_token")
        String refreshToken
) {
}
