package com.example.progettofinale.data.jpa;

import com.example.progettofinale.data.entity.CorsoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CorsoJpaRepository extends JpaRepository<CorsoJpaEntity, Integer> {
    @Query(
            value = """
                    SELECT c.id, c.titolo, c.descrizione, c.cfu, CAST(COUNT(i.id) AS integer) AS "numeroIscritti"
                    FROM corso c
                    LEFT JOIN iscrizione i ON i.corso_id = c.id
                    GROUP BY c.id, c.titolo, c.descrizione, c.cfu
                    ORDER BY "numeroIscritti" DESC
                    """,
            nativeQuery = true
    )
    List<CorsoClassificaIscrittiProjection> findClassificaIscrittiNative();

    boolean existsByDocente_Id(Integer docenteId);
}
