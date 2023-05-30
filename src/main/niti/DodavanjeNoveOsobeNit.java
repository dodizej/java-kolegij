package main.niti;

import main.java.sample.Glavna;
import main.model.Osoba;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DodavanjeNoveOsobeNit implements Runnable {

    String tempIme;
    String tempPrezime;
    String tempDatumRodjenja;
    Long tempZupanija;
    Long tempBolestVirus;
    Long tempId;
    List<Osoba> odabraneOsobe = new ArrayList<>();

    public DodavanjeNoveOsobeNit(String tempIme, String tempPrezime, String tempDatumRodjenja,
                                 Long tempZupanija, Long tempBolestVirus,
                                 Long tempId, List<Osoba> odabraneOsobe) {
        this.tempIme = tempIme;
        this.tempPrezime = tempPrezime;
        this.tempDatumRodjenja = tempDatumRodjenja;
        this.tempZupanija = tempZupanija;
        this.tempBolestVirus = tempBolestVirus;
        this.tempId = tempId;
        this.odabraneOsobe = odabraneOsobe;
    }


    @Override
    public void run() {

        try {

            Connection connection = Glavna.getBazaPodataka().otvaranjeVeze();
            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO OSOBA(IME, PREZIME, DATUM_RODJENJA, ZUPANIJA_ID, BOLEST_ID) " +
                            "VALUES (?,?,?,?,?) ");

            statement.setString(1, tempIme);
            statement.setString(2, tempPrezime);
            statement.setString(3, tempDatumRodjenja);
            statement.setLong(4, tempZupanija);
            statement.setLong(5, tempBolestVirus);
            statement.executeUpdate();

            statement = connection.prepareStatement("SELECT * FROM OSOBA");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String ime = resultSet.getString("IME");
                String prezime = resultSet.getString("PREZIME");
                if (ime.equals(tempIme) && prezime.equals(tempPrezime)) tempId = resultSet.getLong("ID");
            }

            if (!odabraneOsobe.isEmpty()) {
                statement = connection.prepareStatement("INSERT INTO KONTAKTIRANE_OSOBE VALUES(?, ?)");
                for (Osoba temp : odabraneOsobe) {
                    statement.setLong(1, tempId);
                    statement.setLong(2, temp.getId());
                    statement.executeUpdate();
                }
            }

            Glavna.getBazaPodataka().zatvaranjeVeze(resultSet, statement, connection);

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
