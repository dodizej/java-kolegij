package main.iznimke;

/**
 * Predstavlja iznimku kada je unesena bolest koja ima iste simptoma kao već unesena bolest
 *
 */

public class BolestIstihSimptoma extends Exception {

    /**
     * Konstruktor koji prima poruku
     *
     * @param message poruka
     */

    public BolestIstihSimptoma(String message) {
        super(message);
    }

    /**
     * Konstruktor koji prima poruku i objekt Throwable
     *
     * @param message poruka
     * @param cause objekt tipa Throwable
     */

    public BolestIstihSimptoma(String message, Throwable cause) {
        super(message,cause);
    }

    /**
     * Konstruktor koji prima objekt Throwable
     *
     * @param cause objekt tipa Throwable
     */

    public BolestIstihSimptoma(Throwable cause) {
        super(cause);
    }

}
