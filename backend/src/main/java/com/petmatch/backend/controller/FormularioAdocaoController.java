package com.petmatch.backend.controller;

import com.petmatch.backend.dto.FormularioAdocaoDTO;
import com.petmatch.backend.service.FormularioAdocaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/formularios")
@RequiredArgsConstructor
public class FormularioAdocaoController {

    private final FormularioAdocaoService formularioAdocaoService;

    @PostMapping
    public ResponseEntity<Void> enviarFormulario(
            @Valid @RequestBody FormularioAdocaoDTO dto
    ) {
        Long idCriado = formularioAdocaoService.processarFormulario(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(idCriado)
                .toUri();

        return ResponseEntity.created(location).build();
    }
}


