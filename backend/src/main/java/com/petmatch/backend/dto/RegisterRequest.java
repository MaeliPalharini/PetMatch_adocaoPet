package com.petmatch.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @Email(message = "E-mail inválido")
        String userEmail,

        @NotBlank(message = "Senha é obrigatória")
        String password,

        @NotBlank(message = "Role é obrigatória")
        @Pattern(regexp = "USER|ADMIN", message = "Role deve ser USER ou ADMIN")
        String role
) {}
