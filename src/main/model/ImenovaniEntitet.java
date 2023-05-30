package main.model;

import java.io.Serializable;

/**
 * Predstavlja apstraktnu klasu koju nasljeđuju Bolest, Osoba i Simptom
 * Služi za naziv entiteta
 *
 */

public abstract class ImenovaniEntitet implements Serializable {

    String naziv;
    Long id;

    /**
     * Konstruktor koji prima naziv entiteta
     *
     * @param naziv
     */

    public ImenovaniEntitet(String naziv, Long id) {
        this.naziv = naziv;
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
