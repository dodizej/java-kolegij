package main.niti;

import main.java.sample.BazaPodataka;
import main.java.sample.Glavna;
import main.model.Simptom;
import main.model.Zupanija;
import java.util.List;
import java.util.concurrent.Callable;

public class PretragaSimptomaNit implements Callable {

    @Override
    public List<Simptom> call() {

        return Glavna.getBazaPodataka().getAllSimptomi();

    }

}
