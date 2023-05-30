package main.java.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.iznimke.BolestIstogNaziva;
import main.model.*;
import main.sort.CovidSorter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Glavna, polazna klasa u aplikaciji
 *
 */


public class Glavna extends Application {

    private static Stage mainStage;

    public static BazaPodataka bazaPodataka = new BazaPodataka();

    /**
     * Metoda koja inicijalizira početni ekran
     *
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                        .getClassLoader()
                        .getResource("pocetniEkran.fxml")));
        primaryStage.setTitle("Aplikacija");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
        mainStage = primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Metoda koja služi za unos podataka o županijama
     *
     * @return vraća listu županija
     */
    public static List<Zupanija> unosZupanija() {

        //System.out.println("Učitavanje podataka o županijama...");

        List<Zupanija> zupanije = new ArrayList<>();
        Long id;
        String naziv;
        String tempStr;
        Integer brojStanovnika, brojZarazenih;

        File zupanije_input = new File("dat/zupanije.txt");
        try (FileReader fileReader = new FileReader(zupanije_input);
             BufferedReader reader = new BufferedReader(fileReader)) {
            while ( (tempStr = reader.readLine()) != null) {
                id = Long.parseLong(tempStr);
                naziv = reader.readLine();
                brojStanovnika = Integer.parseInt(reader.readLine());
                brojZarazenih = Integer.parseInt(reader.readLine());
                zupanije.add(new Zupanija(naziv,id,brojStanovnika,brojZarazenih));
            }
        }
        catch (IOException e) {
            System.out.println("Greška prilikom unosa!");
            e.printStackTrace();
        }

        return zupanije;

    }


    /**
     * Metoda koja služi za unos podataka o simptomima
     *
     * @return vraća listu simptoma
     */
    public static List<Simptom> unosSimptoma() {

        //System.out.println("Učitavanje podataka o simptomima...");


        List<Simptom> simptomi = new ArrayList<>();
        Long id;
        String naziv, vrijednost, tempStr;

        File simptomi_input = new File("dat/simptomi.txt");
        try (FileReader fileReader = new FileReader(simptomi_input);
             BufferedReader reader = new BufferedReader(fileReader)) {

            while ( (tempStr = reader.readLine()) != null) {
                id = Long.parseLong(tempStr);
                naziv = reader.readLine();
                vrijednost = reader.readLine();
                simptomi.add(new Simptom(naziv,id,vrijednost));
            }

        }
        catch (IOException e) {
            System.out.println("Greška prilikom unosa!");
            e.printStackTrace();
        }


        return simptomi;
    }


    /**
     * Metoda koja služi za unos podataka o bolestima
     *
     * @param simptomi prima listu simptoma radi povezivanja simptoma sa bolestima
     * @return vraća listu bolesti
     */
    public static List<Bolest> unosBolesti(List<Simptom> simptomi) {

        //System.out.println("Učitavanje podataka o bolestima...");

        List<Bolest> bolesti = new ArrayList<>();
        String tempIdStr;

        File bolesti_input = new File("dat/bolesti.txt");
        try (FileReader fileReader = new FileReader(bolesti_input);
             BufferedReader reader = new BufferedReader(fileReader)) {

            while ( (tempIdStr = reader.readLine()) != null) {
                Long id = Long.parseLong(tempIdStr);
                String naziv = reader.readLine();
                String simptomi_string = reader.readLine();
                Set<Simptom> simptomiBolesti = new HashSet<>();

                List<String> idSimptomaBolestiString =
                        Arrays.asList(simptomi_string.split(","));

                for (String temp : idSimptomaBolestiString) {
                    for (Simptom simptom : simptomi) {
                        if (simptom.getId().equals(Long.parseLong(temp))) simptomiBolesti.add(simptom);
                    }
                }

                bolesti.add(new Bolest(naziv, id, simptomiBolesti));
            }

        }
        catch (IOException e) {
            System.out.println("Greška prilikom unosa!");
            e.printStackTrace();
        }

        return bolesti;

    }


    /**
     * Metoda koja služi za unos podataka o virusima
     *
     * @param simptomi prima listu simptoma radi povezivanja simptoma sa virusima
     * @return vraća listu virusa
     */
    public static List<Virus> unosVirusa(List<Simptom> simptomi) {

        //System.out.println("Učitavanje podataka o virusima...");

        List<Virus> virusi = new ArrayList<>();
        String tempIdStr;

        File virusi_input = new File("dat/virusi.txt");
        try (FileReader fileReader = new FileReader(virusi_input);
             BufferedReader reader = new BufferedReader(fileReader)) {

            while ( (tempIdStr = reader.readLine()) != null) {
                Long id = Long.parseLong(tempIdStr);
                String naziv = reader.readLine();
                String simptomi_string = reader.readLine();
                Set<Simptom> simptomiVirusa = new HashSet<>();

                List<String> idSimptomaVirusaString =
                        Arrays.asList(simptomi_string.split(","));

                for (String temp : idSimptomaVirusaString) {
                    for (Simptom simptom : simptomi) {
                        if (simptom.getId().equals(Long.parseLong(temp))) simptomiVirusa.add(simptom);
                    }
                }

                virusi.add(new Virus(naziv, id, simptomiVirusa));
            }

        }
        catch (IOException e) {
            System.out.println("Greška prilikom unosa!");
            e.printStackTrace();
        }

        return virusi;

    }


    /**
     * Metoda koja služi za unos podataka o osobama
     *
     * @param zupanije prima listu županija radi postavljanja županije prebivališta osobe
     * @param bolesti prima listu bolesti/virusa radi posavljanja zaraženosti osobe bolesti/virusom
     * @return vraća listu osoba
     */
    public static List<Osoba> unosOsoba(List<Zupanija> zupanije, List<Bolest> bolesti, List<Virus> virusi) {

        //System.out.println("Učitavanje osoba...");

        List<Osoba> osobe = new ArrayList<>();
        String idstr;

        File osobe_input = new File("dat/osobe.txt");
        try (FileReader fileReader = new FileReader(osobe_input);
             BufferedReader reader = new BufferedReader(fileReader)) {

            while ( (idstr = reader.readLine()) != null)  {

                Long id = Long.parseLong(idstr);
                String ime, prezime;
                Integer starost;
                Long idZupanije, idBolesti;
                Zupanija zupanija = null;
                Bolest zarazenBolescu = null;
                String kontaktiraneOsobeStr, tempIdStr;
                List<Osoba> kontaktiraneOsobe = new ArrayList<>();


                ime = reader.readLine();
                prezime = reader.readLine();
                starost = Integer.parseInt(reader.readLine());
                idZupanije = Long.parseLong(reader.readLine());
                for (Zupanija tempZupanija : zupanije) {
                    if (tempZupanija.getId().equals(idZupanije)) zupanija = tempZupanija;
                }

                tempIdStr = reader.readLine();
                idBolesti = Long.parseLong(Arrays.asList(tempIdStr.split(",")).get(1));
                if (Arrays.asList(tempIdStr.split(",")).get(0).equals("b")) {
                    for (Bolest tempBolest : bolesti) {
                        if (tempBolest.getId().equals(idBolesti)) zarazenBolescu = tempBolest;
                    }
                } else if (Arrays.asList(tempIdStr.split(",")).get(0).equals("v")) {
                    for (Virus tempVirus : virusi) {
                        if (tempVirus.getId().equals(idBolesti)) zarazenBolescu = tempVirus;
                    }
                }



                kontaktiraneOsobeStr = reader.readLine();
                if (!osobe.isEmpty()) {
                    List<String> kontaktiraneOsobeId = Arrays.asList(kontaktiraneOsobeStr.split(","));
                    for (String tempStr : kontaktiraneOsobeId) {
                        for (Osoba osoba : osobe) {
                            if (osoba.getId().equals(Long.parseLong(tempStr))) kontaktiraneOsobe.add(osoba);
                        }
                    }
                }

                Osoba temp = new Osoba.Builder()
                        .dodajId(id)
                        .dodajime(ime)
                        .dodajPrezime(prezime)
                        .dodajStarost(starost)
                        .dodajZupaniju(zupanija)
                        .dodajZarazenBolescu(zarazenBolescu)
                        .dodajKontaktiraneOsobe(kontaktiraneOsobe)
                        .build();

                osobe.add(temp);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return osobe;

    }


    /**
     * Metoda koja služi za ispis svih podataka o osobama na konzolu
     *
     * @param osobe prima listu osoba radi ispisa
     */
    public static void popisOsoba(List<Osoba> osobe) {

        System.out.println("Popis osoba:");

        for (int i = 0; i < osobe.size(); i++) {
            System.out.println("Ime i prezime: " + osobe.get(i).getIme() + " " + osobe.get(i).getPrezime());
            System.out.println("Starost: " + osobe.get(i).getStarost());
            System.out.println("Županija prebivališta: " + (osobe.get(i).getZupanija()).getNaziv() );
            System.out.println("Zaražen bolešću: " + (osobe.get(i).getZarazenBolescu()).getNaziv());
            System.out.println("Kontaktirane osobe:");


            if ( (osobe.get(i).getKontaktiraneOsobe()) != null ) {
                for (int j = 0; j < osobe.get(i).getKontaktiraneOsobe().size(); j++) {
                    System.out.println(osobe.get(j).getIme() + " " + osobe.get(j).getPrezime());
                }

            } else System.out.println("Nema kontaktiranih osoba.");

        }


    }


    public static Stage getMainStage() {
        return mainStage;
    }

    public static BazaPodataka getBazaPodataka() {
        return bazaPodataka;
    }

}