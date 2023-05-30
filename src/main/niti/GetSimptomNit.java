package main.niti;

import main.java.sample.Glavna;
import main.model.Simptom;

import java.util.concurrent.Callable;

public class GetSimptomNit implements Callable {

    Integer index;

    public GetSimptomNit(Integer index) {
        this.index = index;
    }

    @Override
    public Simptom call() {
        return Glavna.getBazaPodataka().getSimptom(index);
    }

}
