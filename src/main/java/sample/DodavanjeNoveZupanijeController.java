package main.java.sample;


import hr.java.covidportal.model.Zupanija;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;



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

        Long lastID = zupanijeIzDat.get(zupanijeIzDat.size()-1).getId();

        zupanijeIzDat.add(new Zupanija(naziv, Integer.parseInt(brStanovnika), Integer.parseInt(brZarazenih), lastID+1));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spremanje podatka");
        alert.setContentText("Nova županija je uspješno spremljena!");
        alert.showAndWait();
        logger.info("Dodana nova županija");
    }

}
