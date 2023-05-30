package main.niti;

import main.java.sample.BazaPodataka;
import main.java.sample.Glavna;
import main.model.Zupanija;
import java.util.List;
import java.util.concurrent.Callable;

public class PretragaZupanijaNit implements Callable {

    @Override
    public List<Zupanija> call() {

        return Glavna.getBazaPodataka().getAllZupanije();

    }

}
