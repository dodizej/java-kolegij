package main.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predstavlja entitet simptoma koji je definiran nazivom i vrijednosću
 */

public class Simptom extends ImenovaniEntitet implements Serializable {

    String vrijednost;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Simptom simptom = (Simptom) o;
        return getVrijednost().equals(simptom.getVrijednost()) || getNaziv().equals(simptom.getNaziv());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVrijednost(), getNaziv());
    }

    /**
     * Konstruktor koji prima naziv simptoma i vrijednost simptoma
     *
     * @param naziv naziv simptoma
     * @param vrijednost vrijednost simptoma
     */

    public Simptom(String naziv, Long id, String vrijednost) {
        super(naziv, id);
        this.vrijednost = vrijednost;
    }

    public String getVrijednost() {
        return vrijednost;
    }

    @Override
    public String toString() {
        return naziv;
    }
}
