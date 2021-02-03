package main.java.sample;

import hr.java.covidportal.model.Bolest;
import hr.java.covidportal.model.Osoba;
import hr.java.covidportal.model.Simptom;
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


public class BrisanjeSimptomaController extends BazaPodataka implements Initializable {

    ObservableList<Simptom> pomListaSimptoma = FXCollections.observableArrayList(listaSimptoma);

    @FXML
    private ListView<Simptom> listViewSimptoma;


    public void obrisiSimptom() throws IOException, SQLException, InterruptedException {
        List<Simptom> odabraniSimptomi;
        odabraniSimptomi = listViewSimptoma.getSelectionModel().getSelectedItems();

        Connection veza = connectToDatabase();

        for(Simptom simp : odabraniSimptomi) {
            PreparedStatement st = veza.prepareStatement("DELETE FROM SIMPTOM WHERE NAZIV = ?");
            st.setString(1, simp.getNaziv());
            st.executeUpdate();
            listaSimptoma.remove(simp);
            pomListaSimptoma.remove(simp);
        }
        closeDatabaseConnection(veza);



        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Brisanje podatka");
        alert.setContentText("Simptom je uspješno obrisan!");
        alert.showAndWait();
        logger.info("Obrisan simptom");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Bolest bol : listaBolestiIVirusa){
            for(Simptom simp : listaSimptoma){
                if(bol.getSimptomi().contains(simp))
                    pomListaSimptoma.remove(simp);
            }
        }
        listViewSimptoma.setItems(pomListaSimptoma);
        listViewSimptoma.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
