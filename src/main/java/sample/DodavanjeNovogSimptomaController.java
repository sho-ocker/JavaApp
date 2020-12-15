package main.java.sample;


import hr.java.covidportal.enums.Simptomi;
import hr.java.covidportal.model.Simptom;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class DodavanjeNovogSimptomaController extends UnosIzDatoteka{

    @FXML
    private TextField nazivSimptoma;

    @FXML
    private RadioButton vrijednostButtonRijetko;

    @FXML
    private RadioButton vrijednostButtonSrednje;

    @FXML
    private RadioButton vrijednostButtonCesto;


    public void dodajSimptom(){
        String naziv = nazivSimptoma.getText();
        String vrijednost = null;

        if(vrijednostButtonCesto.isSelected())
            vrijednost = "CESTO";
        else if(vrijednostButtonSrednje.isSelected())
            vrijednost = vrijednostButtonSrednje.getText();
        else if(vrijednostButtonRijetko.isSelected())
            vrijednost = vrijednostButtonRijetko.getText();

        String enumm = Simptomi.valueOf(vrijednost).getVrijednost();

        Long lastID = simptomiIzDat.get(simptomiIzDat.size()-1).getId() + 1;

        simptomiIzDat.add(new Simptom(naziv, enumm, lastID));

        try(FileWriter fw = new FileWriter("dat/simptomi.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("\n" + lastID);
            out.println(naziv);
            out.print(vrijednost);
        } catch (IOException e) {
            System.err.println(e);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spremanje podatka");
        alert.setContentText("Novi simptom je uspješno spremljen!");
        alert.showAndWait();
        logger.info("Dodan novi simptom");
    }

}
