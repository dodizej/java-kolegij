package main.java.sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import main.enumm.VrijednostSimptoma;
import main.niti.DodavanjeNovogSimptomaNit;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DodavanjeNovogSimptomaController implements Initializable {

    @FXML
    private TextField nazivSimptom;

    @FXML
    private TextField vrijednostSimptom;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void spremiSimptom() {

        String tempNaziv, tempVrijednost;
        tempNaziv = nazivSimptom.getText();
        tempVrijednost = vrijednostSimptom.getText();


        if (!tempNaziv.isBlank() && !tempVrijednost.isBlank() &&
                (tempVrijednost.equals(VrijednostSimptoma.prvo.getVrijednost()) ||
                        tempVrijednost.equals(VrijednostSimptoma.drugo.getVrijednost()) ||
                        tempVrijednost.equals(VrijednostSimptoma.trece.getVrijednost()) ||
                        tempVrijednost.equals(VrijednostSimptoma.cetvrto.getVrijednost()) ||
                                tempVrijednost.equals(VrijednostSimptoma.peto.getVrijednost())  ) )  {

            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(new DodavanjeNovogSimptomaNit(tempNaziv, tempVrijednost));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Uspješan unos simptoma.");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Neupješan unos simptoma.");
            alert.setContentText("Sva polja moraju biti popunjena i ispravnih vrijednosti.");
            alert.showAndWait();
        }

    }

}


