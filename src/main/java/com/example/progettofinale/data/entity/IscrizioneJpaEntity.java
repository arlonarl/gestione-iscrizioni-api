package com.example.progettofinale.data.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "iscrizione",
        uniqueConstraints = @UniqueConstraint(columnNames = {"studente_id", "corso_id"})
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IscrizioneJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "studente_id", nullable = false)
    private StudenteJpaEntity studente;

    @ManyToOne
    @JoinColumn(name = "corso_id", nullable = false)
    private CorsoJpaEntity corso;

}
