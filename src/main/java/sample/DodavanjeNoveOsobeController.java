package main.java.sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.*;
import main.niti.DodavanjeNoveBolestiNit;
import main.niti.DodavanjeNoveOsobeNit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class DodavanjeNoveOsobeController implements Initializable {

    List<Osoba> odabraneOsobe = new ArrayList<>();

    @FXML
    private TextField ime;

    @FXML
    private TextField prezime;

    @FXML
    private TextField starost;

    @FXML
    private ChoiceBox<String> zupanijaChoiceBox;

    @FXML
    private ChoiceBox<String> bolestChoiceBox;

    @FXML
    private ChoiceBox<String> osobaChoiceBox;

    @FXML
    private TableView<Osoba> osobaTableView;

    @FXML
    private TableColumn<Osoba, String> osobaImeCol;

    @FXML
    private TableColumn<Osoba, String> osobaPrezimeCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<String> naziviZupanija = Glavna.getBazaPodataka().getAllZupanije()
                .stream()
                .map(s -> s.getNaziv())
                .collect(Collectors.toList());
        zupanijaChoiceBox.setItems(FXCollections.observableList(naziviZupanija));

        List<Bolest> tempBolestiVirusi = Glavna.getBazaPodataka().getAllBolesti();
        List<String> bolestiVirusi = tempBolestiVirusi
                        .stream()
                        .map(s -> s.getNaziv())
                        .collect(Collectors.toList());
        bolestChoiceBox.setItems(FXCollections.observableList(bolestiVirusi));

        List<String> imenaOsoba = Glavna.getBazaPodataka().getAllOsobe()
                        .stream()
                        .map(s -> s.getIme() + " " + s.getPrezime())
                        .collect(Collectors.toList());
        osobaChoiceBox.setItems(FXCollections.observableList(imenaOsoba));

        osobaImeCol.setCellValueFactory(new PropertyValueFactory<Osoba, String>("ime"));
        osobaPrezimeCol.setCellValueFactory(new PropertyValueFactory<Osoba, String>("prezime"));
        osobaTableView.setItems(FXCollections.observableList(odabraneOsobe));

    }

    public void dodajKontakt() {

        if (osobaChoiceBox.getValue() != null) {

            List<Osoba> osobe = Glavna.getBazaPodataka().getAllOsobe();

            String boxOdabir = osobaChoiceBox.getValue();
            Osoba odabranaOsoba = null;

            for (Osoba osoba : osobe) {
                if (boxOdabir.equals((osoba.getIme()) + " " + osoba.getPrezime())) odabranaOsoba = osoba;
            }

            Osoba finalodabranaOsoba = odabranaOsoba;
            if (odabraneOsobe.stream().noneMatch(s -> s.equals(finalodabranaOsoba))) {
                odabraneOsobe.add(odabranaOsoba);
            }

            osobaTableView.setItems(FXCollections.observableList(odabraneOsobe));

        }

    }

    public void obrisiKontakt() {

        if (osobaChoiceBox.getValue() != null) {

            List<Osoba> osobe = Glavna.getBazaPodataka().getAllOsobe();

            String boxOdabir = osobaChoiceBox.getValue();
            Osoba odabranaOsoba = null;

            for (Osoba osoba : osobe) {
                if (boxOdabir.equals((osoba.getIme()) + " " + osoba.getPrezime())) odabranaOsoba = osoba;
            }

            odabraneOsobe.remove(odabranaOsoba);

            osobaTableView.setItems(FXCollections.observableList(odabraneOsobe));

        }

    }

    public void spremi() {

        if (!ime.getText().isBlank() && !prezime.getText().isBlank() && !starost.getText().isBlank() &&
                isDateValid(starost.getText()) && (zupanijaChoiceBox.getValue() != null) &&
                (bolestChoiceBox.getValue() != null) ) {

            String tempIme = ime.getText();
            String tempPrezime = prezime.getText();
            String tempDatumRodjenja = starost.getText();
            Long tempZupanija = null;
            Long tempBolestVirus = null;
            Long tempId = null;

            for (Zupanija zupanija : Glavna.getBazaPodataka().getAllZupanije()) {
                if (zupanijaChoiceBox.getValue().equals(zupanija.getNaziv())) tempZupanija = zupanija.getId();
            }

            for (Bolest temp : Glavna.getBazaPodataka().getAllBolesti()) {
                if (temp.getNaziv().equals(bolestChoiceBox.getValue())) tempBolestVirus = temp.getId();
            }

            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(new DodavanjeNoveOsobeNit(tempIme, tempPrezime, tempDatumRodjenja,
                     tempZupanija,  tempBolestVirus, tempId,  odabraneOsobe));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Uspješan unos osobe.");
            alert.showAndWait();

            List<String> imenaOsoba = Glavna.getBazaPodataka().getAllOsobe()
                            .stream()
                            .map(s -> s.getIme() + " " + s.getPrezime())
                            .collect(Collectors.toList());
            osobaChoiceBox.setItems(FXCollections.observableList(imenaOsoba));

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Neupješan unos osobe.");
            alert.setContentText("Sva polja moraju biti popunjena i ispravnog formata.");
            alert.showAndWait();
        }

    }

    public boolean isDateValid(String dateStr) {
        SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd");

        if (dateStr != null) {
            try {
                Date ret = sdf.parse(dateStr.trim());
                if (sdf.format(ret).equals(dateStr.trim())) {
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
