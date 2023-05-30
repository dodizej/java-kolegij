package main.java.sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Simptom;
import main.model.Zupanija;
import main.niti.PretragaSimptomaNit;
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

public class PretragaSimptomaController implements Initializable {

    @FXML
    private TextField nazivSimptoma;

    @FXML
    private TableView<Simptom> tablicaSimptoma;

    @FXML
    private TableColumn<Simptom, String> nazivSimptomaColumn;

    @FXML
    private TableColumn<Simptom, String> vrijednostSimptomaColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nazivSimptomaColumn.setCellValueFactory(new PropertyValueFactory<Simptom, String>("naziv"));

        vrijednostSimptomaColumn.setCellValueFactory(new PropertyValueFactory<Simptom, String>("vrijednost"));

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<List<Simptom>> result = service.submit(new PretragaSimptomaNit());
        List<Simptom> simptomi = new ArrayList<>();

        try {
            simptomi = result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        tablicaSimptoma.setItems(FXCollections.observableList(simptomi));

    }

    /**
     * Služi za pretragu objekata po nazivu
     *
     */

    public void pretraga() {

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<List<Simptom>> result = service.submit(new PretragaSimptomaNit());
        List<Simptom> simptomi = new ArrayList<>();

        try {
            simptomi = result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        List<Simptom> simptomiPoNazivu = simptomi
                .stream()
                .filter(s -> s.getNaziv().contains(nazivSimptoma.getCharacters()))
                .collect(Collectors.toList());

        tablicaSimptoma.setItems(FXCollections.observableList(simptomiPoNazivu));

    }

}
