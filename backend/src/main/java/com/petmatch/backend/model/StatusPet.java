package com.petmatch.backend.model;

import java.util.Arrays;

import com.petmatch.backend.exception.ResourceNotFoundException;

public enum StatusPet {
    ADOTADO("Adotado"),
    DISPONIVEL("Disponível"),
    PENDENTE("Pendente");

    private final String label;

    StatusPet(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static StatusPet fromString(String value) {
        return Arrays.stream(values())
                .filter(s -> s.name().equalsIgnoreCase(value) || s.label.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Status inválido: " + value));
    }
}

