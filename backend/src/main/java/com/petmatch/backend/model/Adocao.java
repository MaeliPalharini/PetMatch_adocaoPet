package com.petmatch.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "adocoes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Adocao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private User cliente;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "adocao_pets",
            joinColumns = @JoinColumn(name = "adocao_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id")
    )
    private List<Pet> pets;

    @Column(name = "data_adocao", nullable = false, updatable = false)
    private LocalDate dataAdocao;

    @PrePersist
    private void onCreate() {
        this.dataAdocao = LocalDate.now();
    }
}

