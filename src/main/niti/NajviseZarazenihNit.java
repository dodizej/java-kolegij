package main.niti;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.java.sample.BazaPodataka;
import main.java.sample.Glavna;
import main.java.sample.PretragaZupanijaController;
import main.java.sample.PrviEkranController;
import main.model.Zupanija;

import java.util.Comparator;

public class NajviseZarazenihNit implements Runnable {

    @Override
    public void run() {

        while (true) {

            Zupanija zupanija = Glavna.getBazaPodataka().getAllZupanije()
                    .stream()
                    .max(Comparator.comparing(s -> (float) s.getBrojZarazenih()/s.getBrojStanovnika()))
                    .get();

            String naziv = zupanija.getNaziv();
            Float postotak = (float) zupanija.getBrojZarazenih() / zupanija.getBrojStanovnika() * 100;

            System.out.println("Županija s najviše zaraženih stanovnika je " + naziv + ". Postotak: " + postotak + "%");

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(10), e -> {
                        Glavna.getMainStage().setTitle(naziv);
                    })
            );
            timeline.play();

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }



}
