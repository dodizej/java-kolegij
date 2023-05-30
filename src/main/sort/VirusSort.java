package main.sort;

import main.model.Virus;

import java.util.Comparator;

/**
 * Klasa koja se koristi za sortiranje virusa suprotno od poretka abecede
 *
 */

public class VirusSort implements Comparator<Virus> {

    /**
     * Uspoređuje nazive virusa
     *
     * @param v1 prvi virus
     * @param v2 drugi virus
     * @return -1 0 1
     */

    public int compare(Virus v1, Virus v2) {
        return -1 * v1.getNaziv().compareTo(v2.getNaziv());
    }

}
