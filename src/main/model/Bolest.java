package main.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Predstavlja entitet bolesti koja se definirana nazivom i simptomima
 *
 */

public class Bolest extends ImenovaniEntitet implements Serializable {

    Set<Simptom> simptomi;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bolest bolest = (Bolest) o;
        return getNaziv().equals(bolest.getNaziv()) && getSimptomi().equals(bolest.simptomi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSimptomi(), getNaziv());
    }

    /**
     * Konstruktor koji prima naziv i polje simptoma
     *
     * @param naziv naziv bolesti
     * @param simptomi simptomi bolesti
     */

    public Bolest(String naziv, Long id, Set<Simptom> simptomi) {
        super(naziv, id);
        this.simptomi = simptomi;
    }

    @Override
    public String toString() {
        return naziv;
    }

    public Set<Simptom> getSimptomi() {
        return simptomi;
    }


    public void setSimptomi(Set<Simptom> simptomi) {
        this.simptomi = simptomi;
    }


}
