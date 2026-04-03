package dev.simoncodes.todosync.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @Email String email,
        @NotBlank String password
) { }