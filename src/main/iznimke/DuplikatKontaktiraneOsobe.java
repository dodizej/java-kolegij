package main.iznimke;

/**
 * Predstavlja iznimku kada je unesena osoba koja je već unsena u popis kontaktiranih osoba
 *
 */

public class DuplikatKontaktiraneOsobe extends Exception {

    /**
     * Konstruktor koji prima poruku
     *
     * @param message poruka
     */

    public DuplikatKontaktiraneOsobe(String message) {
        super(message);
    }

    /**
     * Konstruktor koji prima objekt Throwable
     *
     * @param cause objekt tipa Throwable
     */

    public DuplikatKontaktiraneOsobe(Throwable cause) {
        super(cause);
    }

    /**
     * Konstruktor koji prima poruku i objekt Throwable
     *
     * @param message poruka
     * @param cause objekt tipa Throwable
     */

    public DuplikatKontaktiraneOsobe(String message, Throwable cause) {
        super(message, cause);
    }

}
