package main.java.sample;

import hr.java.covidportal.model.Virus;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class BrisanjeVirusaController extends UnosIzDatoteka{

    @FXML
    private TextField nazivVirusa;


    public void obrisiBolest(){
        String naziv = nazivVirusa.getText();

        Boolean zastavica = false;

        for(Virus vir : virusiIzDat){
            if(vir.getNaziv().equals(naziv)) {
                zastavica = true;
                break;
            }
        }

        if(!zastavica){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušajte ponovno");
            alert.setContentText("Ne postoji traženi virus!");
            alert.showAndWait();
            return;
        }

        virusiIzDat.removeIf(bolest -> bolest.getNaziv().equals(naziv));


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Brisanje podatka");
        alert.setContentText("Virus je uspješno obrisan!");
        alert.showAndWait();
        logger.info("Obrisan virus");
    }

}
