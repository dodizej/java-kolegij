package main.niti;

import main.java.sample.Glavna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DodavanjeNovogSimptomaNit implements Runnable {

    String tempNaziv, tempVrijednost;

    public DodavanjeNovogSimptomaNit(String tempNaziv, String tempVrijednost) {
        this.tempNaziv = tempNaziv;
        this.tempVrijednost = tempVrijednost;
    }

    @Override
    public void run() {

        try {

            Connection connection = Glavna.getBazaPodataka().otvaranjeVeze();
            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO SIMPTOM(NAZIV, VRIJEDNOST) VALUES (?,?)");
            statement.setString(1, tempNaziv);
            statement.setString(2, tempVrijednost);
            statement.executeUpdate();
            Glavna.getBazaPodataka().zatvaranjeVeze(statement, connection);

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
