package com.petmatch.backend.repository;

import com.petmatch.backend.model.FormularioAdocao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormularioAdocaoRepository extends JpaRepository<FormularioAdocao, Long> {
}

