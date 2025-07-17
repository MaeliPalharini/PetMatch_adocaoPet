package com.petmatch.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Email(message = "E-mail inválido")
        String userEmail,

        @NotBlank(message = "Senha é obrigatória")
        String password
) {}
