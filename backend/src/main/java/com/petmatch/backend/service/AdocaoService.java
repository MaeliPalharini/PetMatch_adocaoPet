package com.petmatch.backend.service;

import com.petmatch.backend.dto.AdocaoDTO;
import com.petmatch.backend.exception.ResourceNotFoundException;
import com.petmatch.backend.model.Adocao;
import com.petmatch.backend.model.Pet;
import com.petmatch.backend.model.StatusPet;
import com.petmatch.backend.model.User;
import com.petmatch.backend.repository.AdocaoRepository;
import com.petmatch.backend.repository.PetRepository;
import com.petmatch.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdocaoService {

    private final AdocaoRepository adocaoRepository;
    private final PetRepository     petRepository;
    private final UserRepository    userRepository;

    @Transactional
    public Long registrarAdocao(AdocaoDTO dto) {
        User cliente = userRepository.findById(dto.idCliente())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente não encontrado com ID: " + dto.idCliente()
                ));

        // Cria a entidade Adoção e associa todos os pets de uma vez
        Adocao adocao = new Adocao();
        adocao.setCliente(cliente);

        List<Pet> pets = dto.idsPets().stream()
                .map(petId -> petRepository.findById(petId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Pet não encontrado com ID: " + petId
                        ))
                )
                .peek(pet -> {
                    pet.setStatus(StatusPet.PENDENTE);
                    petRepository.save(pet);
                })
                .toList();

        adocao.setPets(pets);
        Adocao salvo = adocaoRepository.save(adocao);
        return salvo.getId();
    }

    public AdocaoDTO buscarPorId(Long id) {
        Adocao adocao = adocaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Adoção não encontrada com ID: " + id
                ));

        return new AdocaoDTO(
                adocao.getCliente().getId(),
                adocao.getPets().stream()
                        .map(Pet::getId)
                        .toList()
        );
    }

    public List<AdocaoDTO> buscarPorCliente(Long clienteId) {
        userRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente não encontrado com ID: " + clienteId
                ));

        return adocaoRepository.findByClienteId(clienteId).stream()
                .map(adocao -> new AdocaoDTO(
                        clienteId,
                        adocao.getPets().stream().map(Pet::getId).toList()
                ))
                .toList();
    }
}
