package main.java.sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class PrviEkranController {

    /**
     * Postavlja ekran za pretragu županija
     *
     */

    public void pretragaZupanija() throws Exception {
        Parent pretragaZupanijaParent =
                FXMLLoader.load(getClass().getClassLoader().getResource("zupanijeEkran.fxml"));
        Scene pretragaZupanijaScreen = new Scene(pretragaZupanijaParent,700,500);
        Glavna.getMainStage().setScene(pretragaZupanijaScreen);
    }

    /**
     * Postavlja ekran za pretragu simptoma
     *
     */

    public void pretragaSimptoma() throws Exception {
        Parent pretragaZupanijaParent =
                FXMLLoader.load(getClass().getClassLoader().getResource("simptomiEkran.fxml"));
        Scene pretragaSimptomaScreen = new Scene(pretragaZupanijaParent,700,500);
        Glavna.getMainStage().setScene(pretragaSimptomaScreen);
    }

    /**
     * Postavlja ekran za pretragu bolesti
     *
     */

    public void pretragaBolesti() throws Exception {
        Parent pretragaBolestiParent =
                FXMLLoader.load(getClass().getClassLoader().getResource("bolestiEkran.fxml"));
        Scene pretragaBolestiScreen = new Scene(pretragaBolestiParent,700,500);
        Glavna.getMainStage().setScene(pretragaBolestiScreen);
    }

    /**
     * Postavlja ekran za pretragu virusa
     *
     */

    public void pretragaVirusa() throws Exception {
        Parent pretragaVirusaParent =
                FXMLLoader.load(getClass().getClassLoader().getResource("virusiEkran.fxml"));
        Scene pretragaVirusaScreen = new Scene(pretragaVirusaParent,700,500);
        Glavna.getMainStage().setScene(pretragaVirusaScreen);
    }

    /**
     * Postavlja ekran za pretragu osoba
     *
     */

    public void pretragaOsoba() throws Exception {
        Parent pretragaOsobaParent =
                FXMLLoader.load(getClass().getClassLoader().getResource("osobeEkran.fxml"));
        Scene pretragaOsobaScreen = new Scene(pretragaOsobaParent,700,500);
        Glavna.getMainStage().setScene(pretragaOsobaScreen);
    }

    public void novaZupanija() throws Exception {
        Parent parent =
                FXMLLoader.load(getClass().getClassLoader().getResource("dodavanjeNoveZupanije.fxml"));
        Scene scene = new Scene(parent,700,500);
        Glavna.getMainStage().setScene(scene);
    }

    public void noviSimptom() throws Exception {
        Parent parent =
                FXMLLoader.load(getClass().getClassLoader().getResource("dodavanjeNovogSimptoma.fxml"));
        Scene scene = new Scene(parent,700,500);
        Glavna.getMainStage().setScene(scene);
    }

    public void novaBolest() throws Exception {
        Parent parent =
                FXMLLoader.load(getClass().getClassLoader().getResource("dodavanjeNoveBolesti.fxml"));
        Scene scene = new Scene(parent,700,500);
        Glavna.getMainStage().setScene(scene);
    }

    public void noviVirus() throws Exception {
        Parent parent =
                FXMLLoader.load(getClass().getClassLoader().getResource("dodavanjeNovogVirusa.fxml"));
        Scene scene = new Scene(parent,700,500);
        Glavna.getMainStage().setScene(scene);
    }

    public void novaOsoba() throws Exception {
        Parent parent =
                FXMLLoader.load(getClass().getClassLoader().getResource("dodavanjeNoveOsobe.fxml"));
        Scene scene = new Scene(parent,700,500);
        Glavna.getMainStage().setScene(scene);
    }

}
