package main.java.sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Bolest;
import main.model.Virus;
import main.model.Zupanija;
import main.niti.PretragaBolestiNit;
import main.niti.PretragaZupanijaNit;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Klasa koja služi za poslovnu logiku pretrage bolesti
 *
 */

public class PretragaBolestiController implements Initializable {

    @FXML
    private TextField nazivBolesti;

    @FXML
    private TableView<Bolest> tablicaBolesti;

    @FXML
    private TableColumn<Bolest, String> nazivBolestiColumn;

    @FXML
    private TableColumn<Bolest, String> simptomiBolestiColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nazivBolestiColumn.setCellValueFactory(new PropertyValueFactory<Bolest, String>("naziv"));

        simptomiBolestiColumn.setCellValueFactory(new PropertyValueFactory<Bolest, String>("simptomi"));

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<List<Bolest>> result = service.submit(new PretragaBolestiNit());
        List<Bolest> bolesti = new ArrayList<>();

        try {
            bolesti = result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        bolesti = bolesti.stream().filter(s -> !(s instanceof Virus)).collect(Collectors.toList());

        tablicaBolesti.setItems(FXCollections.observableList(bolesti));

    }

    /**
     * Služi za pretragu objekata po nazivu
     *
     */

    public void pretraga() {

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<List<Bolest>> result = service.submit(new PretragaBolestiNit());
        List<Bolest> bolesti = new ArrayList<>();

        try {
            bolesti = result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        List<Bolest> bolestiPoNazivu = bolesti
                .stream()
                .filter(s -> !(s instanceof Virus))
                .filter(s -> s.getNaziv().contains(nazivBolesti.getCharacters()))
                .collect(Collectors.toList());

        tablicaBolesti.setItems(FXCollections.observableList(bolestiPoNazivu));

    }

}
