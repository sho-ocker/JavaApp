package main.java.sample;

import hr.java.covidportal.model.Zupanija;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class BrisanjeZupanijeController extends UnosIzDatoteka{

    @FXML
    private TextField nazivZupanije;


    public void obrisiZupaniju(){
        String naziv = nazivZupanije.getText();

        Boolean zastavica = false;

        for(Zupanija zup : zupanijeIzDat){
            if(zup.getNaziv().equals(naziv)) {
                zastavica = true;
                break;
            }
        }

        if(!zastavica){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušajte ponovno");
            alert.setContentText("Ne postoji tražena županija!");
            alert.showAndWait();
            return;
        }

        zupanijeIzDat.removeIf(zupanija -> zupanija.getNaziv().equals(naziv));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Brisanje podatka");
        alert.setContentText("Županija je uspješno obrisana!");
        alert.showAndWait();
        logger.info("Obrisana županija");
    }

}
