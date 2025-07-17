package com.petmatch.backend.dto;

import com.petmatch.backend.model.Pet;
import com.petmatch.backend.model.StatusPet;

public record PetResponse(Long id, String nome, StatusPet status) {
    public static PetResponse fromEntity(Pet pet) {
        return new PetResponse(pet.getId(), pet.getNome(), pet.getStatus());
    }
}

