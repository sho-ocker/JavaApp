package main.java.sample;

import hr.java.covidportal.model.Zupanija;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrikaziZupanijuController {

    @FXML
    private Label id;

    @FXML
    private Label naziv;


    public void prikaziZupaniju(Zupanija zupanija){
        this.id.setText(zupanija.getId().toString());
        this.naziv.setText(zupanija.getNaziv());
    }
}