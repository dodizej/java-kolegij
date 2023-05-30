package main.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Predstavlja entitet osobe koji je definiran imenom, prezimenom, starosti, županijom, bolesti kojom je zaražen
 * i poljem osoba s kojim je osoba bila u kontaktu
 */

public class Osoba implements Serializable {

    Long id;
    String ime, prezime;
    Integer starost;
    Zupanija zupanija;
    Bolest zarazenBolescu;
    List<Osoba> kontaktiraneOsobe = new ArrayList<>();

    @Override
    public String toString() {
        return  ime + " " + prezime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Osoba osoba = (Osoba) o;
        return getIme().equals(osoba.getIme()) &&
                getPrezime().equals(osoba.getPrezime()) &&
                getStarost().equals(osoba.getStarost()) &&
                getZupanija().equals(osoba.getZupanija()) &&
                getZarazenBolescu().equals(osoba.getZarazenBolescu()) &&
                Objects.equals(getKontaktiraneOsobe(), osoba.getKontaktiraneOsobe());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIme(), getPrezime(), getStarost(), getZupanija(), getZarazenBolescu(), getKontaktiraneOsobe());
    }

    /**
     * Builder koji služi za stvaranje novog objekta Osoba
     */

    public static class Builder  {

        private Long id;
        private String ime;
        private String prezime;
        private Integer starost;
        private Zupanija zupanija;
        private Bolest zarazenBolescu;
        private List<Osoba> kontaktiraneOsobe;

        public Builder() {

        }

        public Builder dodajId(Long id) {
            this.id = id;
            return this;
        }

        public Builder dodajime(String ime) {
            this.ime = ime;
            return this;
        }

        public Builder dodajPrezime(String prezime) {
            this.prezime = prezime;
            return this;
        }

        public Builder dodajStarost(Integer starost) {
            this.starost = starost;
            return this;
        }

        public Builder dodajZupaniju(Zupanija zupanija) {
            this.zupanija = zupanija;
            return this;
        }

        public Builder dodajZarazenBolescu(Bolest zarazenBolescu) {
            this.zarazenBolescu = zarazenBolescu;
            return this;
        }

        public Builder dodajKontaktiraneOsobe(List<Osoba> kontaktiraneOsobe) {
            this.kontaktiraneOsobe = kontaktiraneOsobe;
            return this;
        }

        public Osoba build() {
            Osoba osoba = new Osoba();
            osoba.id = this.id;
            osoba.ime = this.ime;
            osoba.prezime = this.prezime;
            osoba.starost = this.starost;
            osoba.zupanija = this.zupanija;
            osoba.zarazenBolescu = this.zarazenBolescu;
            osoba.kontaktiraneOsobe = this.kontaktiraneOsobe;

            if (zarazenBolescu instanceof Virus && (kontaktiraneOsobe != null) ) {
                for (Osoba temp : kontaktiraneOsobe) {
                    temp.setZarazenBolescu(this.zarazenBolescu);
                }
            }

            return osoba;
        }

    }


    /**
     * Konstruktor klase Osoba
     * @param ime ime osobe
     * @param prezime prezime osobe
     * @param starost starost osobe
     * @param zupanija županija prebivališta osobe
     * @param zarazenBolescu bolest kojom je osoba zaražena
     * @param kontaktiraneOsobe osobe s kojima je osoba bila u kontaktu
     */

    public Osoba(Long id, String ime, String prezime, Integer starost, Zupanija zupanija,
                 Bolest zarazenBolescu, List<Osoba> kontaktiraneOsobe) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.starost = starost;
        this.zupanija = zupanija;
        this.zarazenBolescu = zarazenBolescu;
        this.kontaktiraneOsobe = kontaktiraneOsobe;

        if (zarazenBolescu instanceof Virus && (kontaktiraneOsobe != null) ) {
            for (Osoba temp : kontaktiraneOsobe) {
                temp.setZarazenBolescu(this.zarazenBolescu);
            }
        }

    }


    private Osoba() {

    }

    public Long getId() {
        return id;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public Integer getStarost() {
        return starost;
    }

    public Zupanija getZupanija() {
        return zupanija;
    }

    public Bolest getZarazenBolescu() {
        return zarazenBolescu;
    }

    public List<Osoba> getKontaktiraneOsobe() {
        return kontaktiraneOsobe;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setStarost(Integer starost) {
        this.starost = starost;
    }

    public void setZupanija(Zupanija zupanija) {
        this.zupanija = zupanija;
    }

    public void setZarazenBolescu(Bolest zarazenBolescu) {
        this.zarazenBolescu = zarazenBolescu;
    }

    public void setKontaktiraneOsobe(List<Osoba> kontaktiraneOsobe) {
        this.kontaktiraneOsobe = kontaktiraneOsobe;
    }


}
