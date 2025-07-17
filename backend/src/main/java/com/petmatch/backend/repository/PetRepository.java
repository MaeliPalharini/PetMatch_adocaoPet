package com.petmatch.backend.repository;

import com.petmatch.backend.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}


