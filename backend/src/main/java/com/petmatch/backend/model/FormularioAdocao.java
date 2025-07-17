package com.petmatch.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "formularios_adocao")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FormularioAdocao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false, length = 1000)
    private String motivo;

    @Column(nullable = false)
    private String ambiente;

    @Column(name = "espaco_externo", nullable = false)
    private boolean espacoExterno;

    @Column(name = "animais_antes", nullable = false)
    private boolean teveAnimaisAntes;

    @Column(name = "ambiente_seguro", nullable = false)
    private boolean ambienteSeguro;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
}
