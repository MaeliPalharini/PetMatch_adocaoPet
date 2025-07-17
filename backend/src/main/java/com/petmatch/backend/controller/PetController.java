package com.petmatch.backend.controller;

import com.petmatch.backend.dto.PetDTO;
import com.petmatch.backend.dto.PetResponse;
import com.petmatch.backend.dto.StatusUpdateDTO;
import com.petmatch.backend.exception.ResourceNotFoundException;
import com.petmatch.backend.model.Pet;
import com.petmatch.backend.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping
    public ResponseEntity<Pet> criarPet(@RequestBody @Valid PetDTO dto) {
        Pet novo = petService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @GetMapping
    public ResponseEntity<List<Pet>> listarPets() {
        return ResponseEntity.ok(petService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> buscarPorId(@PathVariable Long id) {
        Pet pet = petService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado com ID: " + id));
        return ResponseEntity.ok(pet);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Long id,
            @RequestBody @Valid StatusUpdateDTO dto
    ) {
        petService.updateStatus(id, dto.status());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPet(@PathVariable Long id) {
        petService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
