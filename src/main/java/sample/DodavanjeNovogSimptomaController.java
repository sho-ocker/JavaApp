package main.java.sample;


import hr.java.covidportal.enums.Simptomi;
import hr.java.covidportal.model.Simptom;
import hr.java.covidportal.niti.SpremiSimptomNit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class DodavanjeNovogSimptomaController extends BazaPodataka implements Initializable {

    @FXML
    private TextField nazivSimptoma;

    @FXML
    private ListView<Simptomi> ListViewSimptoma;


    public void dodajSimptom() throws IOException, SQLException, InterruptedException {
        String naziv = nazivSimptoma.getText();
        Simptomi odabraniSimptom;

        odabraniSimptom = ListViewSimptoma.getSelectionModel().getSelectedItem();
        String enumm = Simptomi.valueOf(odabraniSimptom.toString()).getVrijednost();

        Long lastID = (long) listaSimptoma.size() + 1;

        if(naziv.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška u spremanju");
            alert.setContentText("Polje naziv mora biti popunjeno!");
            alert.showAndWait();
            return;
        }

        listaSimptoma.add(new Simptom(naziv, enumm, lastID));

        SpremiSimptomNit nitSimptom = new SpremiSimptomNit
                ("SIMPTOMI", naziv, enumm, lastID);

        service.execute(nitSimptom);

       // spremiNoviSimptom(new Simptom(naziv, enumm, lastID));

    /*    try(FileWriter fw = new FileWriter("dat/simptomi.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("\n" + lastID);
            out.println(naziv);
            out.print(vrijednost);
        } catch (IOException e) {
            System.err.println(e);
        }       */

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spremanje podatka");
        alert.setContentText("Novi simptom je uspješno spremljen!");
        alert.showAndWait();
        logger.info("Dodan novi simptom");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Simptomi> list = FXCollections.observableArrayList(Simptomi.values());
        ListViewSimptoma.setItems(list);
        ListViewSimptoma.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
}
