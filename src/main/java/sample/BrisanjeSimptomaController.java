package main.java.sample;

import hr.java.covidportal.model.Simptom;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class BrisanjeSimptomaController extends UnosIzDatoteka{

    @FXML
    private TextField nazivSimptoma;


    public void obrisiSimptom(){
        String naziv = nazivSimptoma.getText();

        Boolean zastavica = false;

        for(Simptom simp : simptomiIzDat){
            if(simp.getNaziv().equals(naziv)) {
                zastavica = true;
                break;
            }
        }

        if(!zastavica){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušajte ponovno");
            alert.setContentText("Ne postoji traženi simptom!");
            alert.showAndWait();
            return;
        }

        simptomiIzDat.removeIf(simptom -> simptom.getNaziv().equals(naziv));


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Brisanje podatka");
        alert.setContentText("Simptom je uspješno obrisan!");
        alert.showAndWait();
        logger.info("Obrisan simptom");
    }

}
