package main.java.sample;

import javafx.fxml.FXML;
import main.iznimke.BolestIstogNaziva;
import main.model.*;

import javax.swing.plaf.nimbus.State;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BazaPodataka {

    public boolean aktivnaVezaSBazomPodataka = false;

    public synchronized Connection otvaranjeVeze() throws SQLException, InterruptedException {

        while (aktivnaVezaSBazomPodataka) {
            wait();
        }
        try {
            aktivnaVezaSBazomPodataka = true;
            Properties properties = new Properties();
            properties.load(new FileReader("databaseLogin.properties"));
            return DriverManager.getConnection(properties.getProperty("bazaPodatakaUrl"), properties.getProperty("korisnickoIme"),
                    properties.getProperty("lozinka"));
        } catch (IOException e) {
            aktivnaVezaSBazomPodataka = false;
            e.printStackTrace();
        }
        return null;
    }

    public synchronized void zatvaranjeVeze(ResultSet resultSet, Statement statement, Connection connection) {

        try {
            aktivnaVezaSBazomPodataka = false;
            notifyAll();
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public synchronized void zatvaranjeVeze(Statement statement, Connection connection) {

        try {
            aktivnaVezaSBazomPodataka = false;
            notifyAll();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public synchronized void zatvaranjeVeze(Statement statement, ResultSet resultSet) {

        try {
            aktivnaVezaSBazomPodataka = false;
            notifyAll();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public synchronized void zatvaranjeVeze(ResultSet resultSet, ResultSet resultSet2, Statement statement1,
                                            Statement statement2, Statement statement3, Connection connection) {

        try {
            aktivnaVezaSBazomPodataka = false;
            notifyAll();
            statement1.close();
            statement2.close();
            statement3.close();
            resultSet.close();
            resultSet2.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void zatvoriResultSet(ResultSet resultSet) {

        try {
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<Simptom> getAllSimptomi() {

        List<Simptom> simptomi = new ArrayList<>();

        try {
            Connection connection = otvaranjeVeze();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM SIMPTOM");
            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String naziv = resultSet.getString("NAZIV");
                String vrijednost = resultSet.getString("VRIJEDNOST");
                simptomi.add(new Simptom(naziv, id, vrijednost));
            }
            zatvaranjeVeze(resultSet,statement,connection);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

        return simptomi;

    }

    public Simptom getSimptom(Integer temp) {

        try {
            Connection connection = otvaranjeVeze();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM SIMPTOM WHERE ID = ?");
            preparedStatement.setLong(1, temp);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Long id = resultSet.getLong("ID");
            String naziv = resultSet.getString("NAZIV");
            String vrijednost = resultSet.getString("VRIJEDNOST");
            zatvaranjeVeze(resultSet,preparedStatement,connection);
            return new Simptom(naziv, id, vrijednost);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void spremiSimptom(Simptom simptom) {

        try {
            Connection connection = otvaranjeVeze();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO SIMPTOM(NAZIV, VRIJEDNOST) VALUES (?,?)");
            preparedStatement.setString(1, simptom.getNaziv());
            preparedStatement.setString(2, simptom.getVrijednost());
            preparedStatement.executeUpdate();
            zatvaranjeVeze(preparedStatement,connection);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public List<Bolest> getAllBolesti() {

        List<Bolest> bolesti = new ArrayList<>();

        try {
            Connection connection = otvaranjeVeze();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM BOLEST");

            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String naziv = resultSet.getString("NAZIV");
                Boolean virus = resultSet.getBoolean("VIRUS");
                List<Long> idSimptoma = new ArrayList<>();

                PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM BOLEST_SIMPTOM WHERE BOLEST_ID = ?");
                statement2.setLong(1, id);
                ResultSet resultSet2 = statement2.executeQuery();
                while (resultSet2.next()) {
                    idSimptoma.add(resultSet2.getLong("SIMPTOM_ID"));
                }
                zatvaranjeVeze(statement2, resultSet2);

                Set<Simptom> simptomiBolesti = getAllSimptomi()
                        .stream()
                        .filter(s -> idSimptoma.contains(s.getId()))
                        .collect(Collectors.toSet());

                if (virus.equals(true)) bolesti.add(new Virus(naziv, id, simptomiBolesti));
                else bolesti.add(new Bolest(naziv, id, simptomiBolesti));
            }
            zatvaranjeVeze(resultSet, statement, connection);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

        return bolesti;

    }

    public Bolest getBolestVirus(Integer temp) {

        try {

            Connection connection = otvaranjeVeze();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM BOLEST WHERE ID = ?");
            statement.setLong(1, temp);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Long id = resultSet.getLong("ID");
            String naziv = resultSet.getString("NAZIV");
            Boolean virus = resultSet.getBoolean("VIRUS");

            PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM BOLEST_SIMPTOM WHERE BOLEST_ID = ?");
            statement2.setLong(1,temp);
            ResultSet resultSet2 = statement2.executeQuery();

            List<Long> idSimptomaBolesti = new ArrayList<>();
            while (resultSet2.next()) {
                idSimptomaBolesti.add(resultSet2.getLong("SIMPTOM_ID"));
            }

            Set<Simptom> simptomiBolesti = this.getAllSimptomi()
                    .stream()
                    .filter(s -> idSimptomaBolesti.contains(s.getId()))
                    .collect(Collectors.toSet());

            if (virus == true) return new Virus(naziv, id, simptomiBolesti);
            else return new Bolest(naziv, id, simptomiBolesti);

        }
        catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void spremiBolest(Bolest bolest) {

        List<Bolest> bolesti = getAllBolesti();

        try {
            Connection connection = otvaranjeVeze();
            Statement statement = connection.createStatement();
            ResultSet imenaResultSet = statement.executeQuery("SELECT * FROM BOLEST");
            List<String> imenaBolesti = new ArrayList<>();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO BOLEST(NAZIV, VIRUS) VALUES (?,?);");
            while (imenaResultSet.next()) {
                imenaBolesti.add(imenaResultSet.getString("NAZIV"));
            }
            if (!imenaBolesti.contains(bolest.getNaziv())) {
                preparedStatement.setString(1, bolest.getNaziv());
                if (bolest instanceof Virus) preparedStatement.setBoolean(2, true);
                else preparedStatement.setBoolean(2, false);
                preparedStatement.executeUpdate();
                preparedStatement = connection
                        .prepareStatement("SELECT ID FROM BOLEST WHERE NAZIV = ?;");
                preparedStatement.setString(1, bolest.getNaziv());
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                Long id = resultSet.getLong("ID");
                preparedStatement =
                        connection.prepareStatement("INSERT INTO BOLEST_SIMPTOM(BOLEST_ID, SIMPTOM_ID) VALUES (?, ?);");
                preparedStatement.setLong(1, id);
                for (Simptom temp : bolest.getSimptomi()) {
                    preparedStatement.setLong(2, temp.getId());
                    preparedStatement.executeUpdate();
                }
            } else throw new BolestIstogNaziva("U bazi vec postoji bolest sa istim nazivom!");
            zatvaranjeVeze(preparedStatement,connection);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public List<Zupanija> getAllZupanije() {

        List<Zupanija> zupanije = new ArrayList<>();

        try {
            Connection connection = otvaranjeVeze();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ZUPANIJA;");
            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String naziv = resultSet.getString("NAZIV");
                Integer brojStanovnika = resultSet.getInt("BROJ_STANOVNIKA");
                Integer brojZarazenih = resultSet.getInt("BROJ_ZARAZENIH_STANOVNIKA");
                zupanije.add(new Zupanija(naziv,id,brojStanovnika,brojZarazenih));
            }
            zatvaranjeVeze(resultSet,statement,connection);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

        return zupanije;

    }

    public Zupanija getZupanija(Integer temp) {

        try {

            Connection connection = otvaranjeVeze();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ZUPANIJA WHERE ID = ?");
            preparedStatement.setLong(1,temp);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Long id = resultSet.getLong("ID");
            String naziv = resultSet.getString("NAZIV");
            Integer brojStanovnika = resultSet.getInt("BROJ_STANOVNIKA");
            Integer brojZarazenih = resultSet.getInt("BROJ_ZARAZENIH_STANOVNIKA");
            zatvaranjeVeze(resultSet,preparedStatement,connection);
            return new Zupanija(naziv,id,brojStanovnika,brojZarazenih);

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void spremiZupaniju(Zupanija zupanija) {

        try {

            Connection connection = otvaranjeVeze();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ZUPANIJA(NAZIV, BROJ_STANOVNIKA, " +
                    "BROJ_ZARAZENIH_STANOVNIKA) VALUES (?,?,?)");
            preparedStatement.setString(1, zupanija.getNaziv());
            preparedStatement.setInt(2, zupanija.getBrojStanovnika());
            preparedStatement.setInt(3, zupanija.getBrojZarazenih());
            preparedStatement.executeUpdate();
            zatvaranjeVeze(preparedStatement, connection);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public List<Osoba> getAllOsobe() {

        List<Osoba> osobe = new ArrayList<>();

        List<Zupanija> zupanije = getAllZupanije();
        List<Bolest> bolesti = getAllBolesti();

        try {

            Connection connection = otvaranjeVeze();
            Statement statement1 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSetOsoba = statement1.executeQuery("SELECT * FROM OSOBA");
            resultSetOsoba.last();
            Integer brojOsoba = resultSetOsoba.getRow();
            Integer counter = 0;
            Set<Long> idOsobeSaKontaktima = new HashSet<>();
            Statement statement2 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSetKontakti = statement2.executeQuery("SELECT * FROM KONTAKTIRANE_OSOBE");
            while (resultSetKontakti.next()) {
                idOsobeSaKontaktima.add(resultSetKontakti.getLong("OSOBA_ID"));
            }
            PreparedStatement statement3 = connection.prepareStatement("SELECT * FROM KONTAKTIRANE_OSOBE WHERE OSOBA_ID = ?");

            while (counter < brojOsoba) {

                resultSetOsoba.beforeFirst();
                while (resultSetOsoba.next()) {
                    List<Long> idUnesenihOsoba = osobe.stream().map(Osoba::getId).collect(Collectors.toList());
                    Long id = resultSetOsoba.getLong("ID");
                    String ime = resultSetOsoba.getString("IME");
                    String prezime = resultSetOsoba.getString("PREZIME");
                    Date date = resultSetOsoba.getDate("DATUM_RODJENJA");
                    Long zupanija_id = resultSetOsoba.getLong("ZUPANIJA_ID");
                    Long bolest_id = resultSetOsoba.getLong("BOLEST_ID");
                    Long starost = (TimeUnit.DAYS.convert(
                            new Date(System.currentTimeMillis()).getTime() - date.getTime(),
                            TimeUnit.MILLISECONDS) / 365);

                    List<Osoba> kontaktiraneOsobe = new ArrayList<>();
                    List<Long> idKontaktiranihOsoba = new ArrayList<>();
                    Zupanija zupanija_osobe = null;
                    Bolest bolest_osobe = null;
                    for (Zupanija temp : zupanije) {
                        if (zupanija_id.equals(temp.getId()))
                            zupanija_osobe = zupanije.stream().filter(s -> s.getId().equals(temp.getId())).findAny().get();
                    }
                    for (Bolest temp : bolesti) {
                        if (bolest_id.equals(temp.getId()))
                            bolest_osobe = bolesti.stream().filter(s -> s.getId().equals(temp.getId())).findAny().get();
                    }

                    statement3.setLong(1, id);
                    ResultSet resultSetIdKontakta = statement3.executeQuery();
                    while (resultSetIdKontakta.next()) {
                        idKontaktiranihOsoba.add(resultSetIdKontakta.getLong("KONTAKTIRANA_OSOBA_ID"));
                    }
                    if ( (!idOsobeSaKontaktima.contains(id) || idKontaktiranihOsoba.stream().allMatch(a -> idUnesenihOsoba.contains(a)))
                        && !idUnesenihOsoba.contains(id) ) {
                        if (idOsobeSaKontaktima.contains(id)) {
                            for (Osoba temp : osobe) {
                                if (idKontaktiranihOsoba.contains(temp.getId())) kontaktiraneOsobe.add(temp);
                            }
                        }
                        osobe.add(new Osoba(id, ime, prezime, starost.intValue(), zupanija_osobe, bolest_osobe, kontaktiraneOsobe));
                        counter++;
                    }
                    zatvoriResultSet(resultSetIdKontakta);

                }

            }

            osobe = osobe.stream().sorted(Comparator.comparing(Osoba::getId)).collect(Collectors.toList());

            zatvaranjeVeze(resultSetOsoba, resultSetKontakti, statement1, statement2, statement3, connection);

            return osobe;

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

        return osobe;

    }

    public Osoba getOsoba(Integer id) {

        List<Osoba> osobe = getAllOsobe();
        return osobe.stream().filter(s -> s.getId().equals(id)).findAny().get();

    }

}
