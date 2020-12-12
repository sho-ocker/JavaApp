package main.java.sample;

import hr.java.covidportal.model.Bolest;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class BrisanjeBolestiController extends UnosIzDatoteka{

    @FXML
    private TextField nazivBolesti;


    public void obrisiBolest(){
        String naziv = nazivBolesti.getText();

        Boolean zastavica = false;

        for(Bolest bol : bolestiIzDat) {
            if (bol.getNaziv().equals(naziv)){
                zastavica = true;
                break;
            }
        }

        if(!zastavica){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušajte ponovno");
            alert.setContentText("Ne postoji tražena bolest!");
            alert.showAndWait();
            return;
        }

        bolestiIzDat.removeIf(bolest -> bolest.getNaziv().equals(naziv));


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Brisanje podatka");
        alert.setContentText("Bolest je uspješno obrisana!");
        alert.showAndWait();
        logger.info("Obrisana bolest");
    }

}
