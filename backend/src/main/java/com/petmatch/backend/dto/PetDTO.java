package com.petmatch.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PetDTO(
        @NotNull @Size(min=2, max=50) String nome,
        String tipo,
        String raca,
        String porte,
        String idade,
        String descricao,
        String fotoUrl,
        String status,
        String localizacao,
        Long ownerId
) {}


