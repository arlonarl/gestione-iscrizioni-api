package com.example.progettofinale.domain.model;

public class CorsoClassificaIscritti {
    private Corso corso;
    private Integer numeroIscritti;

    public CorsoClassificaIscritti() {
    }

    public CorsoClassificaIscritti(Corso corso, Integer numeroIscritti) {
        this.corso = corso;
        this.numeroIscritti = numeroIscritti;
    }

    public Corso getCorso() {
        return corso;
    }

    public void setCorso(Corso corso) {
        this.corso = corso;
    }

    public Integer getNumeroIscritti() {
        return numeroIscritti;
    }

    public void setNumeroIscritti(Integer numeroIscritti) {
        this.numeroIscritti = numeroIscritti;
    }
}
