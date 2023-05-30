package main.niti;

import main.java.sample.Glavna;
import main.model.Osoba;
import java.util.concurrent.Callable;

public class GetOsobaNit implements Callable {

    Integer index;

    public GetOsobaNit(Integer index) {
        this.index = index;
    }

    @Override
    public Osoba call() throws Exception {
        return Glavna.getBazaPodataka().getOsoba(index);
    }

}
