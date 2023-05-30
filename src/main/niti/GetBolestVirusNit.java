package main.niti;

import main.java.sample.Glavna;
import main.model.Bolest;
import java.util.concurrent.Callable;

public class GetBolestVirusNit implements Callable {

    Integer index;

    public GetBolestVirusNit(Integer index) {
        this.index = index;
    }

    @Override
    public Bolest call() throws Exception {
        return Glavna.getBazaPodataka().getBolestVirus(index);
    }

}
