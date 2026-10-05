package com.example.progettofinale.presentation.mapper;

import com.example.progettofinale.domain.model.Corso;
import com.example.progettofinale.domain.model.CorsoClassificaIscritti;
import com.example.progettofinale.presentation.dto.CorsoClassificaResponse;
import com.example.progettofinale.presentation.dto.CorsoRequest;
import com.example.progettofinale.presentation.dto.CorsoResponse;

public final class CorsoDtoMapper {
    private CorsoDtoMapper() {
    }

    public static Corso toDomain(CorsoRequest request) {
        return new Corso(
                request.titolo(),
                request.descrizione(),
                request.cfu(),
                request.docenteId()
        );
    }

    public static CorsoResponse toResponse(Corso corso) {
        return new CorsoResponse(
                corso.getId(),
                corso.getTitolo(),
                corso.getDescrizione(),
                corso.getCfu(),
                DocenteDtoMapper.toResponse(corso.getDocente())
        );
    }

    public static CorsoClassificaResponse toClassificaResponse(CorsoClassificaIscritti classifica) {
        Corso corso = classifica.getCorso();
        return new CorsoClassificaResponse(
                corso.getId(),
                corso.getTitolo(),
                corso.getDescrizione(),
                corso.getCfu(),
                classifica.getNumeroIscritti()
        );
    }
}
