package main.niti;

import main.java.sample.BazaPodataka;
import main.java.sample.Glavna;
import main.model.Osoba;
import main.model.Simptom;
import main.model.Zupanija;
import java.util.List;
import java.util.concurrent.Callable;

public class PretragaOsobaNit implements Callable {

    @Override
    public List<Osoba> call() {

        return Glavna.getBazaPodataka().getAllOsobe();

    }

}
