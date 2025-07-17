package com.petmatch.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FormularioAdocaoDTO(
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone,

        @NotBlank(message = "Motivo é obrigatório")
        String motivo,

        @NotBlank(message = "Campo ambiente é obrigatório")
        String ambiente,

        @NotNull
        Boolean espacoExterno,

        @NotNull
        Boolean animaisAntes,

        @NotNull
        Boolean ambienteSeguro,

        @NotNull
        Long petId,

        @JsonProperty("clientId")
        @NotNull
        Long clientId
) {}

