package com.example.progettofinale.domain.model;

public class Corso {
    private Integer id;
    private String titolo;
    private String descrizione;
    private Integer cfu;
    private Integer docenteId;
    private Docente docente;

    public Corso() {}
    public Corso(String titolo, String descrizione, Integer cfu) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.cfu = cfu;
    }

    public Corso(String titolo, String descrizione, Integer cfu, Integer docenteId) {
        this(titolo, descrizione, cfu);
        this.docenteId = docenteId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getCfu() {
        return cfu;
    }

    public void setCfu(Integer cfu) {
        this.cfu = cfu;
    }

    public Integer getDocenteId() {
        if (docente != null) {
            return docente.getId();
        }
        return docenteId;
    }

    public void setDocenteId(Integer docenteId) {
        this.docenteId = docenteId;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
        this.docenteId = docente == null ? null : docente.getId();
    }
}
