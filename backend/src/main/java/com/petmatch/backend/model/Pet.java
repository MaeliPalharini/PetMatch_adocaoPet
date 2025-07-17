package com.petmatch.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false)
    private String tipo;

    private String raca;
    private String porte;
    private String idade;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private String fotoUrl;

    @Column(nullable = false)
    private String localizacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPet status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
}
