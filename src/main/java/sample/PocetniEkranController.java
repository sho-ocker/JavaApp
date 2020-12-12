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

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void dodavanjeNoveZupanijeEkran() throws IOException {
        logger.info("Pokrenut ekran za dodavanje nove županije");
        Parent dodajZupanijuFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("dodavanjeNoveZupanije.fxml"));
        Scene dodajZupanijuScene = new Scene(dodajZupanijuFrame);

        Stage stage = (Stage) glavniIzbornik.getScene().getWindow();

        Main.setMainStage(stage);
        Main.getMainStage().setScene(dodajZupanijuScene);
    }

    public void dodavanjeNovogSimptomaEkran() throws IOException {
        logger.info("Pokrenut ekran za dodavanje novog simptoma");
        Parent dodajSimptomFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("dodavanjeNovogSimptoma.fxml"));
        Scene dodajSimptomScene = new Scene(dodajSimptomFrame);

        Stage stage = (Stage) glavniIzbornik.getScene().getWindow();

        Main.setMainStage(stage);
        Main.getMainStage().setScene(dodajSimptomScene);
    }

    public void dodavanjeNoveBolestiEkran() throws IOException {
        logger.info("Pokrenut ekran za dodavanje nove bolesti");
        Parent dodajBolestFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("dodavanjeNoveBolesti.fxml"));
        Scene dodajBolestScene = new Scene(dodajBolestFrame);

        Stage stage = (Stage) glavniIzbornik.getScene().getWindow();

        Main.setMainStage(stage);
        Main.getMainStage().setScene(dodajBolestScene);
    }

    public void dodavanjeNovogVirusaEkran() throws IOException {
        logger.info("Pokrenut ekran za dodavanje novog virusa");
        Parent dodajVirusFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("dodavanjeNovogVirusa.fxml"));
        Scene dodajVirusScene = new Scene(dodajVirusFrame);

        Stage stage = (Stage) glavniIzbornik.getScene().getWindow();

        Main.setMainStage(stage);
        Main.getMainStage().setScene(dodajVirusScene);
    }

    public void dodavanjeNoveOsobeEkran() throws IOException {
        logger.info("Pokrenut ekran za dodavanje nove osobe");
        Parent dodajOsobuFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("dodavanjeNoveOsobe.fxml"));
        Scene dodajOsobuScene = new Scene(dodajOsobuFrame);

        Stage stage = (Stage) glavniIzbornik.getScene().getWindow();

        Main.setMainStage(stage);
        Main.getMainStage().setScene(dodajOsobuScene);
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void brisanjeZupanijeEkran() throws IOException {
        logger.info("Pokrenut ekran za brisanje županije");
        Parent dodajZupanijuFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("brisanjeZupanije.fxml"));
        Scene dodajZupanijuScene = new Scene(dodajZupanijuFrame);

        Stage stage = (Stage) glavniIzbornik.getScene().getWindow();

        Main.setMainStage(stage);
        Main.getMainStage().setScene(dodajZupanijuScene);
    }

    public void brisanjeSimptomaEkran() throws IOException {
        logger.info("Pokrenut ekran za brisanje simptoma");
        Parent dodajSimptomFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("brisanjeSimptoma.fxml"));
        Scene dodajSimptomScene = new Scene(dodajSimptomFrame);

        Stage stage = (Stage) glavniIzbornik.getScene().getWindow();

        Main.setMainStage(stage);
        Main.getMainStage().setScene(dodajSimptomScene);
    }

    public void brisanjeBolestiEkran() throws IOException {
        logger.info("Pokrenut ekran za brisanje bolesti");
        Parent dodajBolestFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("brisanjeBolesti.fxml"));
        Scene dodajBolestScene = new Scene(dodajBolestFrame);

        Stage stage = (Stage) glavniIzbornik.getScene().getWindow();

        Main.setMainStage(stage);
        Main.getMainStage().setScene(dodajBolestScene);
    }

    public void brisanjeVirusaEkran() throws IOException {
        logger.info("Pokrenut ekran za brisanje virusa");
        Parent dodajVirusFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("brisanjeVirusa.fxml"));
        Scene dodajVirusScene = new Scene(dodajVirusFrame);

        Stage stage = (Stage) glavniIzbornik.getScene().getWindow();

        Main.setMainStage(stage);
        Main.getMainStage().setScene(dodajVirusScene);
    }

    public void brisanjeOsobeEkran() throws IOException {
        logger.info("Pokrenut ekran za brisanje osobe");
        Parent dodajOsobuFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("brisanjeOsobe.fxml"));
        Scene dodajOsobuScene = new Scene(dodajOsobuFrame);

        Stage stage = (Stage) glavniIzbornik.getScene().getWindow();

        Main.setMainStage(stage);
        Main.getMainStage().setScene(dodajOsobuScene);
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
