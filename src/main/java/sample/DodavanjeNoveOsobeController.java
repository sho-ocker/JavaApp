package main.java.sample;


import hr.java.covidportal.model.*;
import hr.java.covidportal.niti.SpremiOsobuNit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class DodavanjeNoveOsobeController extends BazaPodataka implements Initializable {

    @FXML
    private TextField imeOsobe;

    @FXML
    private TextField prezimeOsobe;

    @FXML
    private DatePicker starostOsobe;

    @FXML
    private ListView<Bolest> ListViewBolesti;

    @FXML
    private ListView<Zupanija> ListViewZupanija;

    @FXML
    private ListView<Osoba> ListViewOsoba;


    public void dodajOsobu() throws SQLException, IOException, InterruptedException {
        String ime = imeOsobe.getText();
        String prezime = prezimeOsobe.getText();
        LocalDate datum = starostOsobe.getValue();

        Zupanija odabranaZupanija;
        Bolest odabranaBolest;
        ObservableList<Osoba> odabraniKontakti;


        odabranaZupanija = ListViewZupanija.getSelectionModel().getSelectedItem();
        odabranaBolest = ListViewBolesti.getSelectionModel().getSelectedItem();
        odabraniKontakti = ListViewOsoba.getSelectionModel().getSelectedItems();

        Long lastID = listaOsoba.get(listaOsoba.size()-1).getId()+1;



        if(ime.isEmpty() || prezime.isEmpty() || datum == null || odabranaZupanija == null || odabranaBolest == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška u spremanju");
            alert.setContentText("Sva polja moraju biti popunjena!");
            alert.showAndWait();
            return;
        }

        Osoba pom = new Osoba.Builder(ime, prezime)
                .setDatumRodenja(datum)
                .setStarost()
                .setZupanija(odabranaZupanija)
                .setZarazenBolescu(odabranaBolest)
                .setKontaktiraneOsobe(odabraniKontakti)
                .setId(lastID)
                .build();

        listaOsoba.add(new Osoba.Builder(ime, prezime)
                .setDatumRodenja(datum)
                .setStarost()
                .setZupanija(odabranaZupanija)
                .setZarazenBolescu(odabranaBolest)
                .setKontaktiraneOsobe(odabraniKontakti)
                .setId(lastID)
                .build()
        );

        SpremiOsobuNit nitOsoba = new SpremiOsobuNit("OSOBA", pom);
        service.execute(nitOsoba);

     /*   spremiNovuOsobu(new Osoba.Builder(ime, prezime)
                .setDatumRodenja(datum)
                .setZupanija(odabranaZupanija)
                .setZarazenBolescu(odabranaBolest)
                .setKontaktiraneOsobe(odabraniKontakti)
                .setId(lastID)
                .build());  */

   /*     try(FileWriter fw = new FileWriter("dat/osobe.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("\n" + lastID);
            out.println(ime);
            out.println(prezime);
            out.println(starost);
            out.println(odabranaZupanija.getId());
            if(odabranaBolest instanceof Virus)
                out.print("virus,");
            else
                out.print("bolest,");
            out.println(odabranaBolest.getId());
            int br=0;
            for(Osoba oso : odabraniKontakti) {
                br++;
                out.print(oso.getId());
                if(br == odabraniKontakti.size())
                    break;
                out.print(",");
            }

        } catch (IOException e) {
            System.err.println(e);
        }   */

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spremanje podatka");
        alert.setContentText("Nova osoba je uspješno spremljena!");
        alert.showAndWait();
        logger.info("Dodana nova osoba");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ListViewZupanija.setItems(listaZupanija);
        ListViewZupanija.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ListViewBolesti.setItems(listaBolestiIVirusa);
        ListViewBolesti.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ListViewOsoba.setItems(listaOsoba);
        ListViewOsoba.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
