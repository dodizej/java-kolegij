package main.java.sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Simptom;
import main.niti.DodavanjeNoveBolestiNit;
import main.niti.DodavanjeNoveZupanijeNit;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class DodavanjeNoveBolestiController implements Initializable {

    private static List<Simptom> odabraniSimptomi = new ArrayList<>();

    @FXML
    private TextField nazivField;

    @FXML
    private ChoiceBox<String> simptomiBox;

    @FXML
    private TableView<Simptom> tablicaSimptoma;

    @FXML
    private TableColumn<Simptom, String> nazivSimptomaCol;

    @FXML
    private TableColumn<Simptom, String> vrijednostSimptomaCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<String> naziviSimptoma = Glavna.getBazaPodataka().getAllSimptomi().stream().map(s -> s.getNaziv()).collect(Collectors.toList());
        simptomiBox.setItems(FXCollections.observableList(naziviSimptoma));

        nazivSimptomaCol.setCellValueFactory(new PropertyValueFactory<Simptom, String>("naziv"));
        vrijednostSimptomaCol.setCellValueFactory(new PropertyValueFactory<Simptom, String>("vrijednost"));

        tablicaSimptoma.setItems(FXCollections.observableList(odabraniSimptomi));

    }

    public void dodajSimptom() {

        if (simptomiBox.getValue() != null) {

            List<Simptom> simptomi = Glavna.getBazaPodataka().getAllSimptomi();

            String boxOdabir = simptomiBox.getValue();
            Simptom simptomOdabir = null;

            for (Simptom simptom : simptomi) {
                if (boxOdabir.equals(simptom.getNaziv())) simptomOdabir = simptom;
            }

            Simptom finalSimptomOdabir = simptomOdabir;
            if (odabraniSimptomi.stream().noneMatch(s -> s.equals(finalSimptomOdabir))) {
                odabraniSimptomi.add(simptomOdabir);
            }

            tablicaSimptoma.setItems(FXCollections.observableList(odabraniSimptomi));

        }

    }

    public void obrisiSimptom() {

        if (simptomiBox.getValue() != null) {

            List<Simptom> simptomi = Glavna.getBazaPodataka().getAllSimptomi();

            String boxOdabir = simptomiBox.getValue();
            Simptom simptomOdabir = null;

            for (Simptom simptom : simptomi) {
                if (boxOdabir.equals(simptom.getNaziv())) simptomOdabir = simptom;
            }

            odabraniSimptomi.remove(simptomOdabir);

            tablicaSimptoma.setItems(FXCollections.observableList(odabraniSimptomi));

        }

    }

    public void spremi() {

        String tempNaziv = nazivField.getText();

        if (!nazivField.getText().isBlank() && !odabraniSimptomi.isEmpty()) {

            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(new DodavanjeNoveBolestiNit(tempNaziv, odabraniSimptomi));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Uspješan unos bolesti.");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Neupješan unos bolesti.");
            alert.setContentText("Sva polja moraju biti popunjena.");
            alert.showAndWait();
        }

    }

}
