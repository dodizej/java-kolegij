package main.java.sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Osoba;
import main.model.Simptom;
import main.niti.PretragaBolestiNit;
import main.niti.PretragaOsobaNit;
import main.niti.PretragaSimptomaNit;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class PretragaOsobaController implements Initializable {

    @FXML
    private TextField ime;

    @FXML
    private TextField prezime;

    @FXML
    private TableView<Osoba> tablicaOsoba;

    @FXML
    private TableColumn<Osoba, String> imeColumn;

    @FXML
    private TableColumn<Osoba, String> prezimeColumn;

    @FXML
    private TableColumn<Osoba, Integer> starostColumn;

    @FXML
    private TableColumn<Osoba, String> zupanijaColumn;

    @FXML
    private TableColumn<Osoba, String> bolestVirusColumn;

    @FXML
    private TableColumn<Osoba, String> kontaktiraneOsobeColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        imeColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("ime"));
        prezimeColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("prezime"));
        starostColumn.setCellValueFactory(new PropertyValueFactory<Osoba, Integer>("starost"));
        zupanijaColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("zupanija"));
        bolestVirusColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("zarazenBolescu"));
        kontaktiraneOsobeColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("kontaktiraneOsobe"));

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<List<Osoba>> result = service.submit(new PretragaOsobaNit());
        List<Osoba> osobe = new ArrayList<>();

        try {
            osobe = result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        tablicaOsoba.setItems(FXCollections.observableList(osobe));

    }

    /**
     * Služi za pretragu objekata po imenu i prezimenu
     *
     */

    public void pretraga() {

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<List<Osoba>> result = service.submit(new PretragaOsobaNit());
        List<Osoba> osobe = new ArrayList<>();

        try {
            osobe = result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        List<Osoba> osobePoImenuPrezimenu = osobe
                .stream()
                .filter(s -> s.getIme().contains(ime.getCharacters()) && s.getPrezime().contains(prezime.getCharacters()))
                .collect(Collectors.toList());

        tablicaOsoba.setItems(FXCollections.observableList(osobePoImenuPrezimenu));

    }


}
