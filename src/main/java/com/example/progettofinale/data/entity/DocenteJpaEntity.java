package com.example.progettofinale.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "docente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocenteJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30, nullable = false)
    private String nome;

    @Column(length = 30, nullable = false)
    private String cognome;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 80)
    private String settore;

    @OneToMany(mappedBy = "docente")
    private Set<CorsoJpaEntity> corsi;
}
