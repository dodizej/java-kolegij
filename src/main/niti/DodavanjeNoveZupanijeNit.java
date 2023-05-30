package main.niti;

import main.java.sample.Glavna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DodavanjeNoveZupanijeNit implements Runnable {

    String tempNaziv, tempBrojZarazenih, tempBrojStanovnika;

    public DodavanjeNoveZupanijeNit(String tempNaziv, String tempBrojZarazenih, String tempBrojStanovnika) {
        this.tempNaziv = tempNaziv;
        this.tempBrojZarazenih = tempBrojZarazenih;
        this.tempBrojStanovnika = tempBrojStanovnika;
    }

    @Override
    public void run() {
        try {

            Connection connection = Glavna.getBazaPodataka().otvaranjeVeze();
            PreparedStatement statement =
                    connection.prepareStatement("INSERT INTO ZUPANIJA(NAZIV, BROJ_STANOVNIKA, BROJ_ZARAZENIH_STANOVNIKA) " +
                            "VALUES(?, ?, ?);");
            statement.setString(1, tempNaziv);
            statement.setInt(2, Integer.parseInt(tempBrojStanovnika));
            statement.setInt(3, Integer.parseInt(tempBrojZarazenih));
            statement.executeUpdate();
            Glavna.getBazaPodataka().zatvaranjeVeze(statement, connection);

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
