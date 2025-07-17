package com.petmatch.backend.controller;

import com.petmatch.backend.dto.AdocaoDTO;
import com.petmatch.backend.service.AdocaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adocoes")
public class AdocaoController {

    private final AdocaoService adocaoService;

    public AdocaoController(AdocaoService adocaoService) {
        this.adocaoService = adocaoService;
    }

    @PostMapping
    public ResponseEntity<String> registrarAdocao(@RequestBody AdocaoDTO dto) {
        adocaoService.registrarAdocao(dto);
        return ResponseEntity.ok("Adoção registrada com sucesso!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdocaoDTO> buscarAdocaoPorId(@PathVariable Long id) {
        AdocaoDTO dto = adocaoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }
}

