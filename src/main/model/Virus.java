package main.model;

import java.util.Set;

/**
 * Predstavlja entitet virusa koji je definiran nazivom virusa i simptomima virusa
 *
 */

public class Virus extends Bolest implements Zarazno {

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Konstruktor koji prima naziv virusa i simptome virusa
     *
     * @param naziv
     * @param simptomi
     */

    public Virus ( String naziv, Long id, Set<Simptom> simptomi) {
        super(naziv, id, simptomi);
    }

    public void prelazakZarazeNaOsobu(Osoba osoba) {
        osoba.setZarazenBolescu(new Virus(this.getNaziv(), this.id, this.simptomi));
    }

    @Override
    public String toString() {
        return naziv;
    }
}
