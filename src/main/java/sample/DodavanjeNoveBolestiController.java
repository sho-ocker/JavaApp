package main.java.sample;


import hr.java.covidportal.model.Bolest;
import hr.java.covidportal.model.Simptom;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;


public class DodavanjeNoveBolestiController extends UnosIzDatoteka{

    @FXML
    private TextField nazivBolesti;

    @FXML
    private TextField IDsimptoma;


    public void dodajBolest(){
        String naziv = nazivBolesti.getText();
        String[] listaID = IDsimptoma.getText().split(",");
        List<Simptom> listaSimptoma = new ArrayList<>();
        Boolean zastavica = false;

        for(String stringSimp : listaID)
            if(Integer.parseInt(stringSimp) > simptomiIzDat.get(simptomiIzDat.size()-1).getId()) {
                zastavica = true;
                break;
            }


        if(zastavica){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušajte ponovno");
            alert.setContentText("Ne postoji uneseni simptom!");
            alert.showAndWait();
            return;
        }

        for(Simptom simp : simptomiIzDat) {
            for (String stringSimp : listaID) {
                if (simp.getId().toString().equals(stringSimp)) {
                    listaSimptoma.add(simp);
                }
            }
        }

        Long lastID = bolestiIzDat.get(bolestiIzDat.size()-1).getId();

        bolestiIzDat.add(new Bolest(naziv, listaSimptoma, lastID+1));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spremanje podatka");
        alert.setContentText("Nova bolest je uspješno spremljena!");
        alert.showAndWait();
        logger.info("Dodana nova bolest");
    }

}
