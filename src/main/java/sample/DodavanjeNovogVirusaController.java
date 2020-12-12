package main.java.sample;

import hr.java.covidportal.model.Simptom;
import hr.java.covidportal.model.Virus;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;


public class DodavanjeNovogVirusaController extends UnosIzDatoteka{

    @FXML
    private TextField nazivVirusa;

    @FXML
    private TextField IDsimptoma;


    public void dodajVirus(){
        String naziv = nazivVirusa.getText();
        String[] listaID = IDsimptoma.getText().split(",");
        List<Simptom> listaSimptoma = new ArrayList<>();
        Boolean zastavica = false;

        for(String stringSimp : listaID)
            if(Integer.parseInt(stringSimp) > simptomiIzDat.get(simptomiIzDat.size()-1).getId()){
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

        Long lastID = virusiIzDat.get(virusiIzDat.size()-1).getId();

        virusiIzDat.add(new Virus(naziv, listaSimptoma, lastID+1));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spremanje podatka");
        alert.setContentText("Nova bolest je uspješno spremljena!");
        alert.showAndWait();
        logger.info("Dodan novi virus");
    }

}
