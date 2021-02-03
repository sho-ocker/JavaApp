package main.java.sample;

import hr.java.covidportal.model.*;
import hr.java.covidportal.niti.SpremiSimptomNit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BazaPodataka {

    protected static ObservableList<Zupanija> listaZupanija = FXCollections.observableArrayList();
    protected static ObservableList<Simptom> listaSimptoma = FXCollections.observableArrayList();
    protected static ObservableList<Virus> listaVirusa = FXCollections.observableArrayList();
    protected static ObservableList<Bolest> listaBolesti = FXCollections.observableArrayList();
    protected static ObservableList<Osoba> listaOsoba = FXCollections.observableArrayList();
    protected static ObservableList<Bolest> listaBolestiIVirusa = FXCollections.observableArrayList();

    protected static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String databaseConfigurationFile = "src\\main\\resources\\properties";

    public static boolean aktivnaVezaSBazomPodataka = false;
    public static ExecutorService service = Executors.newCachedThreadPool();


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected static synchronized Connection connectToDatabase() throws SQLException, IOException {

        Properties svojstva = new Properties();
        svojstva.load(new FileReader(databaseConfigurationFile));

        String urlBazePodataka = svojstva.getProperty("bazaPodatakaUrl");
        String korisnickoIme = svojstva.getProperty("korisnickoIme");
        String lozinka = svojstva.getProperty("lozinka");

        Connection veza = DriverManager.getConnection(urlBazePodataka, korisnickoIme, lozinka);

        return veza;
    }


    protected static void closeDatabaseConnection(Connection veza) throws SQLException {
        veza.close();
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    protected static List<Simptom> dohvatiSveSimptome() throws SQLException, IOException, InterruptedException {
        List<Simptom> lista = new ArrayList<>();

        Connection veza = connectToDatabase();

        Statement stmt = veza.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM SIMPTOM");

        while (rs.next()) {
            int id = rs.getInt("id");
            String naziv = rs.getString("naziv");
            String vrijednost = rs.getString("vrijednost");
            lista.add(new Simptom(naziv, vrijednost, (long) id));
        }
        closeDatabaseConnection(veza);

        return lista;
    }



    protected static Simptom dohvatiSimptom(Integer trazeniId) throws SQLException, IOException, InterruptedException {
        Connection veza = connectToDatabase();

        Statement stmt = veza.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM SIMPTOM WHERE ID = '" + trazeniId + "'");

        while(rs.next()) {
            int id = rs.getInt("id");
            String naziv = rs.getString("naziv");
            String vrijednost = rs.getString("vrijednost");
            return new Simptom(naziv, vrijednost, (long) id);
        }
        return null;
    }



    protected static void spremiNoviSimptom(Simptom noviSimptom) throws SQLException, IOException, InterruptedException {
        Connection veza = connectToDatabase();

        PreparedStatement upit = veza.prepareStatement
                ("INSERT INTO SIMPTOM(id, naziv, vrijednost) VALUES ((SELECT MAX(id) FROM SIMPTOM + 1),?,?)");
        upit.setString(1, noviSimptom.getNaziv());
        upit.setString(2, noviSimptom.getVrijednost());
        upit.executeUpdate();

        closeDatabaseConnection(veza);
    }



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    protected static List<Bolest> dohvatiSveBolesti() throws SQLException, IOException, InterruptedException {
        List<Bolest> listaBolesti = new ArrayList<>();
        List<Simptom> listaSimptoma = dohvatiSveSimptome();

        List<List<Simptom>> trazeniSimptomi = new ArrayList<>();
        int i = 0;

        Connection veza = connectToDatabase();

        Statement stmt = veza.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM BOLEST");

        Statement stmtt = veza.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );
        ResultSet rss = stmtt.executeQuery("SELECT * FROM BOLEST_SIMPTOM");

        while(rs.next()){
            int id = rs.getInt("id");
            String naziv = rs.getString("naziv");
            Boolean virus = rs.getBoolean("virus");

            while(rss.next()){
                int bolest_id = rss.getInt("bolest_id");

                if(bolest_id != id) {
                    rss.previous();
                    break;
                }

                int simptom_id = rss.getInt("simptom_id");

                trazeniSimptomi.add(new ArrayList<>());
                for(Simptom simp : listaSimptoma){
                    if(simp.getId() == simptom_id)
                        trazeniSimptomi.get(i).add(simp);
                }
            }


            if(virus) {
                listaVirusa.add(new Virus(naziv, trazeniSimptomi.get(i), virus, (long) id));
            }else {
                listaBolesti.add(new Bolest(naziv, trazeniSimptomi.get(i), virus, (long) id));
            }
            listaBolestiIVirusa.add(new Bolest(naziv, trazeniSimptomi.get(i), virus, (long)id));
            i++;
        }
        closeDatabaseConnection(veza);
        return listaBolesti;
    }



    protected static Bolest dohvatiBolest(Integer trazeniId) throws SQLException, IOException, InterruptedException {
        List<Simptom> listaSimptoma = dohvatiSveSimptome();
        List<Simptom> trazeniSimptomi = new ArrayList<>();

        Connection veza = connectToDatabase();

        Statement stmt = veza.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM BOLEST WHERE ID = '" + trazeniId + "'");

        Statement stmtt = veza.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );
        ResultSet rss = stmtt.executeQuery("SELECT * FROM BOLEST_SIMPTOM");

        while(rs.next()){
            int id = rs.getInt("id");
            String naziv = rs.getString("naziv");
            Boolean virus = rs.getBoolean("virus");

            while(rss.next()){
                int bolest_id = rss.getInt("bolest_id");

                if(bolest_id == id) {
                    int simptom_id = rss.getInt("simptom_id");

                    for(Simptom simp : listaSimptoma){
                        if(simp.getId() == simptom_id)
                            trazeniSimptomi.add(simp);
                    }
                }
            }

            closeDatabaseConnection(veza);
            return new Bolest(naziv, trazeniSimptomi, virus, (long)id);
        }
        return null;
    }



    protected static void spremiNovuBolest(Bolest novaBolest) throws SQLException, IOException, InterruptedException {
        Connection veza = connectToDatabase();

        PreparedStatement upit = veza.prepareStatement
                ("INSERT INTO BOLEST(id, naziv, virus) VALUES ((SELECT MAX(id) FROM BOLEST + 1),?,?)");
        upit.setString(1, novaBolest.getNaziv());
        upit.setString(2, novaBolest.getVirus().toString());
        upit.executeUpdate();

        upit = veza.prepareStatement
                ("INSERT INTO BOLEST_SIMPTOM(bolest_id, simptom_id) VALUES (?,?)");

        for(Simptom simp : novaBolest.getSimptomi()) {
            upit.setString(1, novaBolest.getId().toString());
            upit.setString(2, simp.getId().toString());
            upit.executeUpdate();
        }

        closeDatabaseConnection(veza);
    }



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    protected static List<Zupanija> dohvatiSveZupanije() throws SQLException, IOException, InterruptedException {
        List<Zupanija> lista = new ArrayList<>();

        Connection veza = connectToDatabase();

        Statement stmt = veza.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM ZUPANIJA");

        while(rs.next()){
            int id = rs.getInt("id");
            String naziv = rs.getString("naziv");
            Integer br_stanovnika = rs.getInt("broj_stanovnika");
            Integer br_zarazenih = rs.getInt("broj_zarazenih_stanovnika");
            lista.add(new Zupanija(naziv, br_stanovnika, br_zarazenih, (long)id));
        }
        closeDatabaseConnection(veza);
        return lista;
    }




    protected static Zupanija dohvatiZupaniju(Integer trazeniId) throws SQLException, IOException, InterruptedException {
        Connection veza = connectToDatabase();

        Statement stmt = veza.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM ZUPANIJA WHERE ID = '" + trazeniId + "'");

        while(rs.next()) {
            int id = rs.getInt("id");
            String naziv = rs.getString("naziv");
            Integer br_stanovnika = rs.getInt("broj_stanovnika");
            Integer br_zarazenih = rs.getInt("broj_zarazenih_stanovnika");
            return new Zupanija(naziv, br_stanovnika, br_zarazenih, (long)id);
        }
        return null;
    }




    protected static void spremiNovuZupaniju(Zupanija novaZupanija) throws SQLException, IOException, InterruptedException {
        Connection veza = connectToDatabase();

        PreparedStatement upit = veza.prepareStatement
                ("INSERT INTO ZUPANIJA(id, naziv, broj_stanovnika, broj_zarazenih_stanovnika) VALUES " +
                        "((SELECT MAX(id) FROM ZUPANIJA + 1),?,?,?)");
        upit.setString(1, novaZupanija.getNaziv());
        upit.setString(2, novaZupanija.getBrojStanovnika().toString());
        upit.setString(3, novaZupanija.getBrojZarazenih().toString());
        upit.executeUpdate();


        closeDatabaseConnection(veza);
    }



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    protected static List<Osoba> dohvatiSveOsobe() throws SQLException, IOException, InterruptedException {
        List<Osoba> listaPrije = new ArrayList<>();
        List<Osoba> listaPoslije = new ArrayList<>();
        List<List<Osoba>> listaKont = new ArrayList<>();
        int i = 0;

        Connection veza = connectToDatabase();

        Statement stmt = veza.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );
        ResultSet rs = stmt.executeQuery("SELECT * FROM OSOBA");

        Statement stmt2 = veza.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );
        ResultSet rs2 = stmt2.executeQuery("SELECT * FROM KONTAKTIRANE_OSOBE");

        while(rs.next()) {
            int id = rs.getInt("id");
            String ime = rs.getString("ime");
            String prezime = rs.getString("prezime");
            Date date = (Date) rs.getDate("datum_rodjenja");
            Instant instant = Instant.ofEpochMilli(date.getTime());
            LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
            int zupanija_id = rs.getInt("zupanija_id");
            int bolest_id = rs.getInt("bolest_id");

            listaPrije.add(new Osoba.Builder(ime, prezime)
                    .setZupanija(dohvatiZupaniju(zupanija_id))
                    .setZarazenBolescu(dohvatiBolest(bolest_id))
                    .setDatumRodenja(localDate)
                    .setId((long) id)
            //        .setKontaktiraneOsobe(listaKont.get(i))
                    .build());

        }

        rs.beforeFirst();
        while(rs.next()) {
            int id = rs.getInt("id");
            String ime = rs.getString("ime");
            String prezime = rs.getString("prezime");
            Date date = (Date) rs.getDate("datum_rodjenja");
            Instant instant = Instant.ofEpochMilli(date.getTime());
            LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
            int zupanija_id = rs.getInt("zupanija_id");
            int bolest_id = rs.getInt("bolest_id");

            while(rs2.next()) {
                int osoba_id = rs2.getInt("osoba_id");

                if (id != osoba_id) {
                    rs2.previous();
                    listaKont.add(new ArrayList<>());
                    break;
                }

                int kontaktirana_osoba_id = rs2.getInt("kontaktirana_osoba_id");

                listaKont.add(new ArrayList<>());
                for (Osoba oso : listaPrije) {
                    if (oso.getId() == kontaktirana_osoba_id)
                        listaKont.get(i).add(oso);
                }
            }

            listaPoslije.add(new Osoba.Builder(ime, prezime)
                    .setZupanija(dohvatiZupaniju(zupanija_id))
                    .setZarazenBolescu(dohvatiBolest(bolest_id))
                    .setDatumRodenja(localDate)
                    .setId((long) id)
                    .setKontaktiraneOsobe(listaKont.get(i))
                    .build());
            i++;
        }
        closeDatabaseConnection(veza);
        return listaPoslije;
    }



    protected static Osoba dohvatiOsobu(Integer trazeniId) throws SQLException, IOException, InterruptedException {
        Connection veza = connectToDatabase();

        Statement stmt = veza.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM OSOBA WHERE ID = '" + trazeniId + "'");

        while(rs.next()) {
            int id = rs.getInt("id");
            String ime = rs.getString("ime");
            String prezime = rs.getString("prezime");
            Date date = (Date)rs.getDate("datum_rodjenja");
            Instant instant = Instant.ofEpochMilli(date.getTime());
            LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
            int zupanija_id = rs.getInt("zupanija_id");
            int bolest_id = rs.getInt("bolest_id");
            return new Osoba.Builder(ime, prezime)
                    .setZupanija(dohvatiZupaniju(zupanija_id))
                    .setZarazenBolescu(dohvatiBolest(bolest_id))
                    .setDatumRodenja(localDate)
                    .setId((long)id)
                    .build();
        }
        return null;
    }



    protected static void spremiNovuOsobu(Osoba novaOsoba) throws SQLException, IOException, InterruptedException {
        Connection veza = connectToDatabase();

        PreparedStatement upit = veza.prepareStatement
                ("INSERT INTO OSOBA(id, ime, prezime, datum_rodjenja, zupanija_id, bolest_id) VALUES " +
                        "((SELECT MAX(id) FROM OSOBA + 1),?,?,?,?,?)");
        upit.setString(1, novaOsoba.getIme());
        upit.setString(2, novaOsoba.getPrezime());
        upit.setString(3, novaOsoba.getDatum_rodenja().toString());
        upit.setString(4, novaOsoba.getZupanija().getId().toString());
        upit.setString(5, novaOsoba.getZarazenBolescu().getId().toString());
        upit.executeUpdate();

        upit = veza.prepareStatement
                ("INSERT INTO KONTAKTIRANE_OSOBE(osoba_id, kontaktirana_osoba_id) VALUES (?,?)");

        for(Osoba oso : novaOsoba.getKontaktiraneOsobe()) {
            upit.setString(1, novaOsoba.getId().toString());
            upit.setString(2, oso.getId().toString());
            upit.executeUpdate();
        }
        closeDatabaseConnection(veza);
    }



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    public static void unos() throws SQLException, IOException, InterruptedException {
        logger.info("Pokrenuta aplikacija");
        listaSimptoma = FXCollections.observableArrayList(dohvatiSveSimptome());
        listaBolesti = FXCollections.observableArrayList(dohvatiSveBolesti());
        listaZupanija = FXCollections.observableArrayList(dohvatiSveZupanije());
        listaOsoba = FXCollections.observableArrayList(dohvatiSveOsobe());
        logger.info("Učitani podaci");
        System.out.println("Učitani svi podaci!");
    }
}
