package main.java.sample;

import hr.java.covidportal.model.Simptom;
import hr.java.covidportal.model.Virus;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class DodavanjeNovogVirusaController extends BazaPodataka implements Initializable {

    @FXML
    private TextField nazivVirusa;

    @FXML
    private ListView<Simptom> ListViewSimptoma;


    public void dodajVirus() throws SQLException, IOException, InterruptedException {
        String naziv = nazivVirusa.getText();
        ObservableList<Simptom> odabraniSimptomi;
        Long lastIDBolesti;

        odabraniSimptomi = ListViewSimptoma.getSelectionModel().getSelectedItems();
        lastIDBolesti = listaBolesti.get(listaBolesti.size()-1).getId() + 1;


        listaBolesti.add(new Virus(naziv, odabraniSimptomi, true, lastIDBolesti));
        spremiNovuBolest(new Virus(naziv, odabraniSimptomi, true, lastIDBolesti));

    /*    try(FileWriter fw = new FileWriter("dat/virusi.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("\n" + lastID);
            out.println(naziv);
            int br=0;
            for(Simptom simp : odabraniSimptomi){
                br++;
                out.print(simp.getId());
                if(br == odabraniSimptomi.size())
                    break;
                out.print(",");
            }
        } catch (IOException e) {
            System.err.println(e);
        }   */

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spremanje podatka");
        alert.setContentText("Nova bolest je uspješno spremljena!");
        alert.showAndWait();
        logger.info("Dodan novi virus");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ListViewSimptoma.setItems(listaSimptoma);
        ListViewSimptoma.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
