package main.java.sample;


import hr.java.covidportal.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;


public class DodavanjeNoveOsobeController extends UnosIzDatoteka{

    @FXML
    private TextField imeOsobe;

    @FXML
    private TextField prezimeOsobe;

    @FXML
    private TextField starostOsobe;

    @FXML
    private TextField bolestOsobe;

    @FXML
    private TextField zupanijaOsobe;

    @FXML
    private TextField kontaktiraneOsobe;


    public void dodajOsobu(){
        String ime = imeOsobe.getText();
        String prezime = prezimeOsobe.getText();
        String starost = starostOsobe.getText();
        String bolestString = bolestOsobe.getText();
        String zupanijaString = zupanijaOsobe.getText();
        String[] kontaktiraneList = kontaktiraneOsobe.getText().split(",");
        Zupanija zupanija = new Zupanija();
        Bolest bolest = new Bolest();
        List<Osoba> kontaktirane = new ArrayList<>();


        for(Zupanija zup : zupanijeIzDat){
            if(zup.getNaziv().equals(zupanijaString)) {
                zupanija = zup;
                break;
            }
        }

        for(Bolest bol : bolestiIzDat){
            if(bol.getNaziv().equals(bolestString)){
                bolest = bol;
                break;
            }
        }

        for(Virus vir : virusiIzDat){
            if(vir.getNaziv().equals(bolestString)){
                bolest = vir;
                break;
            }
        }

        for(Osoba oso : osobeIzDat){
            for(String strOso : kontaktiraneList){
                if(oso.getId().toString().equals(strOso))
                    kontaktirane.add(oso);
            }
        }


        Long lastID = osobeIzDat.get(osobeIzDat.size()-1).getId();

        osobeIzDat.add(new Osoba.Builder(ime, prezime)
                .setStarost(Integer.parseInt(starost))
                .setZupanija(zupanija)
                .setZarazenBolescu(bolest)
                .setKontaktiraneOsobe(kontaktirane)
                .setId(lastID+1)
                .build()
        );


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spremanje podatka");
        alert.setContentText("Nova osoba je uspješno spremljena!");
        alert.showAndWait();
        logger.info("Dodana nova osoba");
    }
}
