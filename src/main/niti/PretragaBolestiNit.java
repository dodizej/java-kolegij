package main.niti;

import main.java.sample.BazaPodataka;
import main.java.sample.Glavna;
import main.model.Bolest;
import main.model.Simptom;
import main.model.Zupanija;
import java.util.List;
import java.util.concurrent.Callable;

public class PretragaBolestiNit implements Callable {

    @Override
    public List<Bolest> call() {

        return Glavna.getBazaPodataka().getAllBolesti();

    }

}
