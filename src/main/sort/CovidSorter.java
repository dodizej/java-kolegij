package main.sort;

import main.model.Zupanija;

import java.util.Comparator;


/**
 * Klasa koja se koristi za sortiranje zupanija pa njihovom postotku zarazenih osoba
 *
 */

public class CovidSorter implements Comparator<Zupanija> {


    /**
     * Metoda koja uspoređuje koja od županija ima veći postotak zaraženih osoba
     *
     * @param o1 prva županija
     * @param o2 druga županija
     * @return int koji može biti  -1  0  1
     */

    @Override
    public int compare(Zupanija o1, Zupanija o2) {
        Double prosjek1 = o1.getBrojZarazenih().doubleValue()/o1.getBrojStanovnika();
        Double prosjek2 = o2.getBrojZarazenih().doubleValue()/o2.getBrojStanovnika();
        return prosjek1.compareTo(prosjek2);
    }

}
