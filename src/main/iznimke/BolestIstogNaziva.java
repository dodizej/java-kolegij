package main.iznimke;

import javafx.scene.control.Alert;

public class BolestIstogNaziva extends RuntimeException {

    /**
     * Konstruktor koji prima poruku
     *
     * @param message poruka
     */

    public BolestIstogNaziva(String message) {
        super(message);
    }

    /**
     * Konstruktor koji prima objekt Throwable
     *
     * @param cause objekt tipa Throwable
     */

    public BolestIstogNaziva(Throwable cause) {
        super(cause);
    }

    /**
     * Konstruktor koji prima poruku i objekt Throwable
     *
     * @param message poruka
     * @param cause objekt tipa Throwable
     */

    public BolestIstogNaziva(String message, Throwable cause) {
        super(message, cause);
    }


}
