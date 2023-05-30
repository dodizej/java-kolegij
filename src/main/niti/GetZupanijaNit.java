package main.niti;

import main.java.sample.Glavna;
import main.model.Zupanija;

import java.util.concurrent.Callable;

public class GetZupanijaNit implements Callable {

    Integer index;

    public GetZupanijaNit(Integer index) {
        this.index = index;
    }

    @Override
    public Zupanija call() throws Exception {
        return Glavna.getBazaPodataka().getZupanija(index);
    }

}
