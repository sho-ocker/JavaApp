package main.java.sample;

import hr.java.covidportal.model.Bolest;
import hr.java.covidportal.model.Osoba;
import hr.java.covidportal.model.Simptom;
import hr.java.covidportal.model.Virus;
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


public class BrisanjeBolestiController extends BazaPodataka implements Initializable {

    ObservableList<Bolest> pomListaBolesti = FXCollections.observableArrayList(listaBolestiIVirusa);

    @FXML
    private ListView<Bolest> listViewBolesti;


    public void obrisiBolest() throws IOException, SQLException, InterruptedException {
        List<Bolest> odabraneBolesti;
        odabraneBolesti = listViewBolesti.getSelectionModel().getSelectedItems();

        Connection veza = connectToDatabase();

        for(Bolest bol : odabraneBolesti) {
            PreparedStatement st1 = veza.prepareStatement("DELETE FROM BOLEST_SIMPTOM WHERE BOLEST_ID =?");
            st1.setString(1, bol.getId().toString());
            st1.executeUpdate();
            PreparedStatement st2 = veza.prepareStatement("DELETE FROM BOLEST WHERE NAZIV = ?");
            st2.setString(1, bol.getNaziv());
            st2.executeUpdate();
            pomListaBolesti.remove(bol);
            listaBolestiIVirusa.remove(bol);
            if(bol instanceof Virus) {
                listaVirusa.remove(bol);
            }else{
                listaBolesti.remove(bol);
            }
        }
        closeDatabaseConnection(veza);


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Brisanje podatka");
        alert.setContentText("Bolest je uspješno obrisana!");
        alert.showAndWait();
        logger.info("Obrisana bolest");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Osoba oso : listaOsoba){
            for(Bolest bol : listaBolestiIVirusa){
                if(oso.getZarazenBolescu().equals(bol))
                    pomListaBolesti.remove(bol);
            }
        }
        listViewBolesti.setItems(pomListaBolesti);
        listViewBolesti.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
