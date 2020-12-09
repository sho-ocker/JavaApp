package main.java.sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PocetniEkranController extends Main implements Initializable{

    @FXML
    private MenuBar glavniIzbornik;

    public void prikaziEkranZaPretraguZupanija() throws IOException {
        logger.info("Pokrenut ekran za pretragu županija");
        Parent pretragaZupanijaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("pretragaZupanija.fxml"));
        Scene pretragaZupanijaScene = new Scene(pretragaZupanijaFrame);

        Stage stage = (Stage) glavniIzbornik.getScene().getWindow();

        Main.setMainStage(stage);
        Main.getMainStage().setScene(pretragaZupanijaScene);
    }

    public void prikaziEkranZaPretraguSimptoma() throws IOException {
        logger.info("Pokrenut ekran za pretragu simptoma");
        Parent pretragaSimptomaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("pretragaSimptoma.fxml"));
        Scene pretragaSimptomaScene = new Scene(pretragaSimptomaFrame);

        Stage stage = (Stage) glavniIzbornik.getScene().getWindow();

        Main.setMainStage(stage);
        Main.getMainStage().setScene(pretragaSimptomaScene);
    }

    public void prikaziEkranZaPretraguBolesti() throws IOException {
        logger.info("Pokrenut ekran za pretragu bolesti");
        Parent pretragaBolestiFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("pretragaBolesti.fxml"));
        Scene pretragaBolestiScene = new Scene(pretragaBolestiFrame);

        Stage stage = (Stage) glavniIzbornik.getScene().getWindow();

        Main.setMainStage(stage);
        Main.getMainStage().setScene(pretragaBolestiScene);
    }

    public void prikaziEkranZaPretraguVirusa() throws IOException {
        logger.info("Pokrenut ekran za pretragu virusa");
        Parent pretragaVirusaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("pretragaVirusa.fxml"));
        Scene pretragaVirusaScene = new Scene(pretragaVirusaFrame);

        Stage stage = (Stage) glavniIzbornik.getScene().getWindow();

        Main.setMainStage(stage);
        Main.getMainStage().setScene(pretragaVirusaScene);
    }

    public void prikaziEkranZaPretraguOsoba() throws IOException {
        logger.info("Pokrenut ekran za pretragu osoba");
        Parent pretragaOsobaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("pretragaOsoba.fxml"));
        Scene pretragaOsobaScene = new Scene(pretragaOsobaFrame);

        Stage stage = (Stage) glavniIzbornik.getScene().getWindow();

        Main.setMainStage(stage);
        Main.getMainStage().setScene(pretragaOsobaScene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
