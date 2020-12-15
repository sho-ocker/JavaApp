package main.java.sample;


import hr.java.covidportal.model.Bolest;
import hr.java.covidportal.model.Simptom;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class DodavanjeNoveBolestiController extends UnosIzDatoteka implements Initializable{

    @FXML
    private TextField nazivBolesti;

    @FXML
    private ListView<Simptom> ListViewSimptoma;


    public void dodajBolest(){
        String naziv = nazivBolesti.getText();
        ObservableList<Simptom> odabraniSimptomi;

        odabraniSimptomi = ListViewSimptoma.getSelectionModel().getSelectedItems();

        Long lastID = bolestiIzDat.get(bolestiIzDat.size()-1).getId() + 1;

        bolestiIzDat.add(new Bolest(naziv, odabraniSimptomi, lastID));
        try(FileWriter fw = new FileWriter("dat/bolesti.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("\n" + lastID);
            out.println(naziv);
            int br=0;
            for(Simptom simp : odabraniSimptomi){
                br++;
                out.print(simp.getId());
                if(br == odabraniSimptomi.size())
                    break;
                out.print(",");
            }
        } catch (IOException e) {
            System.err.println(e);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spremanje podatka");
        alert.setContentText("Nova bolest je uspješno spremljena!");
        alert.showAndWait();
        logger.info("Dodana nova bolest");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ListViewSimptoma.setItems(simptomiIzDat);
        ListViewSimptoma.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
