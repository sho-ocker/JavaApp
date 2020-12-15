package main.java.sample;


import hr.java.covidportal.model.Zupanija;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class DodavanjeNoveZupanijeController extends UnosIzDatoteka{

    @FXML
    private TextField nazivZupanije;

    @FXML
    private TextField brojStanovnika;

    @FXML
    private TextField brojZarazenih;


    public void dodajZupaniju(){
        String naziv = nazivZupanije.getText();
        String brStanovnika = brojStanovnika.getText();
        String brZarazenih = brojZarazenih.getText();

        Long lastID = zupanijeIzDat.get(zupanijeIzDat.size()-1).getId() + 1;

        zupanijeIzDat.add(new Zupanija(naziv, Integer.parseInt(brStanovnika), Integer.parseInt(brZarazenih), lastID));

        try(FileWriter fw = new FileWriter("dat/zupanije.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("\n" + lastID);
            out.println(naziv);
            out.println(brStanovnika);
            out.print(brZarazenih);
        } catch (IOException e) {
            System.err.println(e);
        }


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spremanje podatka");
        alert.setContentText("Nova županija je uspješno spremljena!");
        alert.showAndWait();
        logger.info("Dodana nova županija");
    }

}
