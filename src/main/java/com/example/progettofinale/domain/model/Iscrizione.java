package com.example.progettofinale.domain.model;

public class Iscrizione {
    private Integer id;
    private Studente studente;
    private Corso corso;

    public Iscrizione() {
    }

    public Iscrizione(Studente studente, Corso corso) {
        this.studente = studente;
        this.corso = corso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Studente getStudente() {
        return studente;
    }

    public void setStudente(Studente studente) {
        this.studente = studente;
    }

    public Corso getCorso() {
        return corso;
    }

    public void setCorso(Corso corso) {
        this.corso = corso;
    }
}
