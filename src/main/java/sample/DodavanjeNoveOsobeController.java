package main.java.sample;


import hr.java.covidportal.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;


public class DodavanjeNoveOsobeController extends UnosIzDatoteka implements Initializable {

    @FXML
    private TextField imeOsobe;

    @FXML
    private TextField prezimeOsobe;

    @FXML
    private TextField starostOsobe;

    @FXML
    private ListView<Bolest> ListViewBolesti;

    @FXML
    private ListView<Zupanija> ListViewZupanija;

    @FXML
    private ListView<Osoba> ListViewOsoba;


    public void dodajOsobu(){
        String ime = imeOsobe.getText();
        String prezime = prezimeOsobe.getText();
        String starost = starostOsobe.getText();

        Zupanija odabranaZupanija;
        Bolest odabranaBolest;
        ObservableList<Osoba> odabraniKontakti;

        odabranaZupanija = ListViewZupanija.getSelectionModel().getSelectedItem();
        odabranaBolest = ListViewBolesti.getSelectionModel().getSelectedItem();
        odabraniKontakti = ListViewOsoba.getSelectionModel().getSelectedItems();

        Long lastID = osobeIzDat.get(osobeIzDat.size()-1).getId() + 1;

        osobeIzDat.add(new Osoba.Builder(ime, prezime)
                .setStarost(Integer.parseInt(starost))
                .setZupanija(odabranaZupanija)
                .setZarazenBolescu(odabranaBolest)
                .setKontaktiraneOsobe(odabraniKontakti)
                .setId(lastID)
                .build()
        );

        try(FileWriter fw = new FileWriter("dat/osobe.txt", true);
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
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spremanje podatka");
        alert.setContentText("Nova osoba je uspješno spremljena!");
        alert.showAndWait();
        logger.info("Dodana nova osoba");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList <Bolest> sveBolesti = FXCollections.observableArrayList();
        for(Virus vir : virusiIzDat)
            sveBolesti.add(vir);
        for(Bolest bol : bolestiIzDat)
            sveBolesti.add(bol);
        ListViewZupanija.setItems(zupanijeIzDat);
        ListViewZupanija.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ListViewBolesti.setItems(sveBolesti);
        ListViewBolesti.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ListViewOsoba.setItems(osobeIzDat);
        ListViewOsoba.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
