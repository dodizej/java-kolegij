package main.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predstavlja entitet županije koji je definiran nazivom županije i brojem stanovnika županije
 */

public class Zupanija extends ImenovaniEntitet implements Serializable {

    Integer brojStanovnika;
    Integer brojZarazenih;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zupanija zupanija = (Zupanija) o;
        return getBrojStanovnika().equals(zupanija.getBrojStanovnika()) && getNaziv().equals(zupanija.getNaziv())
                && getBrojZarazenih().equals(zupanija.brojZarazenih);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBrojStanovnika(), getNaziv(), getBrojZarazenih());
    }

    /**
     * Konstruktor koji prima naziv i broj stanovnika županije
     *
     * @param naziv naziv županije
     * @param brojStanovnika broj stanovnika županije
     */

    public Zupanija(String naziv, Long id, Integer brojStanovnika, Integer brojZarazenih) {
        super(naziv, id);
        this.brojStanovnika = brojStanovnika;
        this.brojZarazenih = brojZarazenih;
    }

    @Override
    public String toString() {
        return naziv;
    }


    public Integer getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(Integer brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public Integer getBrojZarazenih() {
        return brojZarazenih;
    }

    public void setBrojZarazenih(Integer brojZarazenih) {
        this.brojZarazenih = brojZarazenih;
    }

}
