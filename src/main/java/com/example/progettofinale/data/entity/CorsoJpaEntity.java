package com.example.progettofinale.data.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "corso")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CorsoJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30, nullable = false)
    private String titolo;

    @Column(length = 255)
    private String descrizione;

    @Column(nullable = false)
    private Integer cfu;

    @ManyToOne
    @JoinColumn(name = "docente_id")
    private DocenteJpaEntity docente;

    @OneToMany(mappedBy = "corso", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<IscrizioneJpaEntity> iscrizioni;
}
