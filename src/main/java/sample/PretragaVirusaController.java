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
import main.niti.PretragaBolestiNit;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class PretragaVirusaController implements Initializable {

    @FXML
    private TextField nazivVirusa;

    @FXML
    private TableView<Virus> tablicaVirusa;

    @FXML
    private TableColumn<Virus, String> nazivVirusaColumn;

    @FXML
    private TableColumn<Virus, String> simptomiVirusaColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nazivVirusaColumn.setCellValueFactory(new PropertyValueFactory<Virus, String>("naziv"));

        simptomiVirusaColumn.setCellValueFactory(new PropertyValueFactory<Virus, String>("simptomi"));

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<List<Bolest>> result = service.submit(new PretragaBolestiNit());
        List<Bolest> bolesti = new ArrayList<>();

        try {
            bolesti = result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        List<Virus> virusi = new ArrayList<>();
        for (Bolest temp : bolesti) {
            if (temp instanceof Virus) virusi.add( (Virus) temp);
        }

        tablicaVirusa.setItems(FXCollections.observableList(virusi));

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

        List<Virus> virusi = new ArrayList<>();
        for (Bolest temp : bolesti) {
            if (temp instanceof Virus) virusi.add( (Virus) temp);
        }

        List<Virus> virusiPoNazivu = virusi
                .stream()
                .filter(s -> s.getNaziv().contains(nazivVirusa.getCharacters()))
                .filter(s -> s instanceof Virus)
                .collect(Collectors.toList());

        tablicaVirusa.setItems(FXCollections.observableList(virusiPoNazivu));

    }

}
