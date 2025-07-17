package com.petmatch.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AdocaoDTO(
        @NotNull(message = "ID do cliente é obrigatório")
        Long idCliente,

        @NotEmpty(message = "Deve haver ao menos um pet para adoção")
        List<@NotNull Long> idsPets
) {}

