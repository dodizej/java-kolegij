package main.niti;

import main.java.sample.Glavna;
import main.model.Simptom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DodavanjeNovogVirusaNit implements Runnable {

    String tempNaziv;
    List<Simptom> odabraniSimptomi = new ArrayList<>();

    public DodavanjeNovogVirusaNit(String tempNaziv, List<Simptom> odabraniSimptomi) {
        this.tempNaziv = tempNaziv;
        this.odabraniSimptomi = odabraniSimptomi;
    }

    @Override
    public void run() {
        try {

            Connection connection = Glavna.getBazaPodataka().otvaranjeVeze();
            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO BOLEST(NAZIV, VIRUS) VALUES (?, ?);");
            statement.setString(1, tempNaziv);
            statement.setBoolean(2, true);
            statement.executeUpdate();

            statement = connection.prepareStatement("SELECT * FROM BOLEST WHERE NAZIV = ?");
            statement.setString(1,tempNaziv);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Long id = resultSet.getLong("ID");

            statement = connection.prepareStatement("INSERT INTO BOLEST_SIMPTOM(BOLEST_ID, SIMPTOM_ID) VALUES (?, ?);");
            for (Simptom temp : odabraniSimptomi) {
                statement.setLong(1, id);
                statement.setLong(2, temp.getId());
                statement.executeUpdate();
            }

            Glavna.getBazaPodataka().zatvaranjeVeze(resultSet, statement, connection);

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
