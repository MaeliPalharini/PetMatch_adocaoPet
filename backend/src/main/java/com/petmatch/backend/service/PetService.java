package com.petmatch.backend.service;

import com.petmatch.backend.dto.PetDTO;
import com.petmatch.backend.exception.ResourceNotFoundException;
import com.petmatch.backend.model.Pet;
import com.petmatch.backend.model.StatusPet;
import com.petmatch.backend.model.User;
import com.petmatch.backend.repository.PetRepository;
import com.petmatch.backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public PetService(PetRepository petRepository,
                      UserRepository userRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    public Pet save(PetDTO dto) {
        Pet pet = new Pet();

        pet.setNome(dto.nome());
        pet.setTipo(dto.tipo());
        pet.setRaca(dto.raca());
        pet.setPorte(dto.porte());
        pet.setIdade(dto.idade());
        pet.setDescricao(dto.descricao());
        pet.setFotoUrl(dto.fotoUrl());
        pet.setStatus(StatusPet.fromString(dto.status()));
        pet.setLocalizacao(dto.localizacao());

        User owner;
        if (dto.ownerId() != null) {
            owner = userRepository.findById(dto.ownerId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Usuário (owner) não encontrado com ID: " + dto.ownerId()
                    ));
        } else {
            Authentication authentication = SecurityContextHolder
                    .getContext()
                    .getAuthentication();
            String email = authentication.getName();
            owner = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        }
        pet.setOwner(owner);

        return petRepository.save(pet);
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Optional<Pet> findById(Long id) {
        return petRepository.findById(id);
    }

    public void updateStatus(Long id, String statusStr) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado com ID: " + id));
        pet.setStatus(StatusPet.fromString(statusStr));
        petRepository.save(pet);
    }

    public void delete(Long id) {
        if (!petRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pet não encontrado com ID: " + id);
        }
        petRepository.deleteById(id);
    }
}
