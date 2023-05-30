package main.enumm;

/**
 * Predstavlja enum vrijednosti simptoma
 *
 */

public enum VrijednostSimptoma {

    prvo("Produktivni"), drugo("Intenzivno"), trece("Visoka"), cetvrto("Jaka"), peto("Produktivni");

    String vrijednost;

    /**
     * Konstruktor koji prima vrijednost simptoma
     *
     * @param vrijednost
     */

    private VrijednostSimptoma(String vrijednost) {
        this.vrijednost = vrijednost;
    }

    public String getVrijednost() {
        return vrijednost;
    }

}
