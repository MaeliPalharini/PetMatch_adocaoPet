package com.petmatch.backend.service;

import com.petmatch.backend.dto.FormularioAdocaoDTO;
import com.petmatch.backend.exception.ResourceNotFoundException;
import com.petmatch.backend.model.FormularioAdocao;
import com.petmatch.backend.model.Pet;
import com.petmatch.backend.model.User;
import com.petmatch.backend.repository.FormularioAdocaoRepository;
import com.petmatch.backend.repository.PetRepository;
import com.petmatch.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormularioAdocaoService {

    private final FormularioAdocaoRepository repository;
    private final PetRepository             petRepository;
    private final UserRepository            userRepository;

    @Transactional
    public Long processarFormulario(FormularioAdocaoDTO dto) {

        Pet pet = petRepository.findById(dto.petId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pet não encontrado com ID: " + dto.petId()
                ));
        User client = userRepository.findById(dto.clientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente não encontrado com ID: " + dto.clientId()
                ));

        FormularioAdocao entidade = FormularioAdocao.builder()
                .email(dto.email())
                .telefone(dto.telefone())
                .motivo(dto.motivo())
                .ambiente(dto.ambiente())
                .espacoExterno(dto.espacoExterno())
                .teveAnimaisAntes(dto.animaisAntes())
                .ambienteSeguro(dto.ambienteSeguro())
                .client(client)
                .pet(pet)
                .build();

        return repository.save(entidade).getId();
    }
}

