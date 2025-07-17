package com.petmatch.backend.repository;

import com.petmatch.backend.model.Adocao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdocaoRepository extends JpaRepository<Adocao, Long> {
    List<Adocao> findByClienteId(Long clienteId);
}



