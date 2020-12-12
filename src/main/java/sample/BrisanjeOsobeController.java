package main.java.sample;

import hr.java.covidportal.model.Osoba;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class BrisanjeOsobeController extends UnosIzDatoteka{

    @FXML
    private TextField imeOsobe;

    @FXML
    private TextField prezimeOsobe;


    public void obrisiOsobu(){
        String ime = imeOsobe.getText();
        String prezime = prezimeOsobe.getText();

        Boolean zastavica = false;

        for(Osoba oso : osobeIzDat){
            if(oso.getIme().equals(ime) || oso.getPrezime().equals(prezime)){
                zastavica = true;
                break;
            }
        }


        if(!zastavica){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušajte ponovno");
            alert.setContentText("Ne postoji tražena osoba!");
            alert.showAndWait();
            return;
        }

        osobeIzDat.removeIf(oso -> oso.getIme().equals(ime) || oso.getPrezime().equals(prezime));


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Brisanje podatka");
        alert.setContentText("Osoba je uspješno obrisana!");
        alert.showAndWait();
        logger.info("Obrisana osoba");
    }

}
