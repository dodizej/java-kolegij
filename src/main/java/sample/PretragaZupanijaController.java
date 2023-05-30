package main.java.sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Zupanija;
import main.niti.NajviseZarazenihNit;
import main.niti.PretragaZupanijaNit;


import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class PretragaZupanijaController implements Initializable {

    @FXML
    private TextField nazivZupanije;

    @FXML
    private TableView<Zupanija> tablicaZupanija;

    @FXML
    private TableColumn<Zupanija, String> nazivZupanijeColumn;

    @FXML
    private TableColumn<Zupanija, Integer> brojStanovnikaZupanijeColumn;

    @FXML
    private TableColumn<Zupanija, Integer> brojZarazenihZupanijeColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new NajviseZarazenihNit());

        nazivZupanijeColumn.setCellValueFactory(new PropertyValueFactory<Zupanija, String>("naziv"));

        brojStanovnikaZupanijeColumn.setCellValueFactory(new PropertyValueFactory<Zupanija, Integer>("brojStanovnika"));

        brojZarazenihZupanijeColumn.setCellValueFactory(new PropertyValueFactory<Zupanija, Integer>("brojZarazenih"));

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<List<Zupanija>> result = service.submit(new PretragaZupanijaNit());
        List<Zupanija> zupanije = new ArrayList<>();

        try {
            zupanije = result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        zupanije = Glavna.getBazaPodataka().getAllZupanije();

        tablicaZupanija.setItems(FXCollections.observableList(zupanije));

    }

    /**
     * Služi za pretragu objekata po nazivu
     *
     */

    public void pretraga() {

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<List<Zupanija>> result = service.submit(new PretragaZupanijaNit());
        List<Zupanija> zupanije = new ArrayList<>();

        try {
            zupanije = result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        List<Zupanija> zupanijePoNazivu = zupanije
                .stream()
                .filter(s -> s.getNaziv().contains(nazivZupanije.getCharacters()))
                .collect(Collectors.toList());

        tablicaZupanija.setItems(FXCollections.observableList(zupanijePoNazivu));

    }


}
