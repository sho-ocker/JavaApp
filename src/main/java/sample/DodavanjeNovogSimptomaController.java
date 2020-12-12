package main.java.sample;


import hr.java.covidportal.enums.Simptomi;
import hr.java.covidportal.model.Simptom;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class DodavanjeNovogSimptomaController extends UnosIzDatoteka{

    @FXML
    private TextField nazivSimptoma;

    @FXML
    private TextField vrijednostSimptoma;


    public void dodajSimptom(){
        String naziv = nazivSimptoma.getText();
        String vrijednost = vrijednostSimptoma.getText();
        Boolean zastavica = false;


        for (Simptomi s : Simptomi.values()) {
            if (s.name().equals(vrijednost)) {
                zastavica = true;
                break;
            }
        }
        if(!zastavica){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušajte ponovno");
            alert.setContentText("Ne postoji unesena vrijednost!");
            alert.showAndWait();
            return;
        }

        String enumm = Simptomi.valueOf(vrijednost).getVrijednost();

        Long lastID = simptomiIzDat.get(simptomiIzDat.size()-1).getId();

        simptomiIzDat.add(new Simptom(naziv, enumm, lastID+1));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spremanje podatka");
        alert.setContentText("Novi simptom je uspješno spremljen!");
        alert.showAndWait();
        logger.info("Dodan novi simptom");

    }

}
