package main.genericsi;


import main.model.Bolest;
import main.model.Osoba;
import main.model.Virus;

import java.util.ArrayList;
import java.util.List;

/**
 * Generička klasa koja predstavlja entitet definiran listom virusa i listom osoba koje su zaražene virusima
 *
 * @param <T> Generički tip koji nasljeđuje Virus
 * @param <S> Generički tip koji nasljeđuje Osoba
 */

public class KlinikaZaInfektivneBolesti<T extends Virus, S extends Osoba> {

    List<T> virusi = new ArrayList<>();
    List<S> zarazeneOsobe = new ArrayList<>();

    public KlinikaZaInfektivneBolesti(List<Bolest> bolesti, List<S> sveOsobe) {

        for (Bolest bolest : bolesti) {
            if (bolest instanceof Virus) {
                virusi.add((T)bolest);
            }
        }

        for (S osoba : sveOsobe) {
            if (osoba.getZarazenBolescu() instanceof Virus) zarazeneOsobe.add(osoba);
        }

    }


    public List<T> getVirusi() {
        return virusi;
    }

    public void setVirusi(List<T> virusi) {
        this.virusi = virusi;
    }

    public List<S> getZarazeneOsobe() {
        return zarazeneOsobe;
    }

    public void setZarazeneOsobe(List<S> zarazeneOsobe) {
        this.zarazeneOsobe = zarazeneOsobe;
    }

}
