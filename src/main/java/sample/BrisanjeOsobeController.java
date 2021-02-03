package main.java.sample;

import hr.java.covidportal.model.Osoba;
import hr.java.covidportal.model.Zupanija;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class BrisanjeOsobeController extends BazaPodataka implements Initializable {

    @FXML
    private ListView<Osoba> listViewOsoba;


    public void obrisiOsobu() throws IOException, SQLException, InterruptedException {
        List<Osoba> odabraneOsobe;
        odabraneOsobe = listViewOsoba.getSelectionModel().getSelectedItems();

        Connection veza = connectToDatabase();

        for(Osoba oso : odabraneOsobe) {
            PreparedStatement st1 = veza.prepareStatement("DELETE FROM KONTAKTIRANE_OSOBE WHERE OSOBA_ID = ?");
            st1.setString(1, oso.getId().toString());
            st1.executeUpdate();
            PreparedStatement st2 = veza.prepareStatement("DELETE FROM OSOBA WHERE ID = ?");
            st2.setString(1, oso.getId().toString());
            st2.executeUpdate();
            listaOsoba.remove(oso);
        }
        closeDatabaseConnection(veza);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Brisanje podatka");
        alert.setContentText("Osoba je uspješno obrisana!");
        alert.showAndWait();
        logger.info("Obrisana osoba");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listViewOsoba.setItems(listaOsoba);
        listViewOsoba.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
