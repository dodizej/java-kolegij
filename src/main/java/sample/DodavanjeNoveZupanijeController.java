package main.java.sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import main.model.Osoba;
import main.model.Zupanija;
import javafx.scene.control.TextField;
import main.niti.DodavanjeNoveZupanijeNit;
import main.niti.NajviseZarazenihNit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DodavanjeNoveZupanijeController implements Initializable {

    @FXML
    private TextField naziv;

    @FXML
    private TextField brojStanovnika;

    @FXML
    private TextField brojZarazenih;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void spremi() {

        String tempNaziv, tempBrojStanovnika, tempBrojZarazenih;

        tempNaziv = naziv.getText();
        tempBrojStanovnika = brojStanovnika.getText();
        tempBrojZarazenih = brojZarazenih.getText();

        if (!naziv.getText().isBlank() && !brojStanovnika.getText().isBlank() && !brojZarazenih.getText().isBlank() &&
        tempBrojStanovnika.matches("[0-9]+") && tempBrojZarazenih.matches("[0-9]+")) {

            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(new DodavanjeNoveZupanijeNit(tempNaziv,tempBrojZarazenih,tempBrojStanovnika));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Uspješan unos županije.");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Neupješan unos županije.");
            alert.setContentText("Sva polja moraju biti popunjena i ispravnog formata.");
            alert.showAndWait();
        }

    }

}
