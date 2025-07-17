package com.petmatch.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record StatusUpdateDTO(
        @NotBlank
        String status
) { }

