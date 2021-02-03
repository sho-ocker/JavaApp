package main.java.sample;


import hr.java.covidportal.model.Bolest;
import hr.java.covidportal.model.Simptom;
import hr.java.covidportal.model.Virus;
import hr.java.covidportal.niti.SpremiBolestNit;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class DodavanjeNoveBolestiController extends BazaPodataka implements Initializable{

    @FXML
    private TextField nazivBolesti;

    @FXML
    private ListView<Simptom> ListViewSimptoma;

    @FXML
    private CheckBox isVirus;


    public void dodajBolest() throws SQLException, IOException, InterruptedException {
        String naziv = nazivBolesti.getText();
        ObservableList<Simptom> odabraniSimptomi = null;
        Long id;

        odabraniSimptomi = ListViewSimptoma.getSelectionModel().getSelectedItems();

        if(naziv.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška u spremanju");
            alert.setContentText("Polje naziv mora biti popunjeno!");
            alert.showAndWait();
            return;
        }

        id = (long) listaBolestiIVirusa.size() + 1;
        if(isVirus.isSelected()){
            listaVirusa.add(new Virus(naziv, odabraniSimptomi, isVirus.isSelected(), (long) listaVirusa.size() + 1));
            listaBolestiIVirusa.add(new Virus(naziv, odabraniSimptomi, isVirus.isSelected(), id));

            Virus pom1 = new Virus(naziv, odabraniSimptomi, isVirus.isSelected(), id);
            SpremiBolestNit nitBolesti = new SpremiBolestNit("VIRUS", pom1);
            service.execute(nitBolesti);
         //   spremiNovuBolest(new Bolest(naziv, odabraniSimptomi, isVirus.isSelected(), id));
        }else {
            listaBolesti.add(new Bolest(naziv, odabraniSimptomi, isVirus.isSelected(), (long) listaBolesti.size() + 1));
            listaBolestiIVirusa.add(new Bolest(naziv, odabraniSimptomi, isVirus.isSelected(), id));

            Bolest pom2 = new Bolest(naziv, odabraniSimptomi, isVirus.isSelected(), id);
            SpremiBolestNit nitBolesti = new SpremiBolestNit("BOLEST", pom2);
            service.execute(nitBolesti);
        //    spremiNovuBolest(new Bolest(naziv, odabraniSimptomi, isVirus.isSelected(), id));
        }

    /*    try(FileWriter fw = new FileWriter("dat/bolesti.txt", true);
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
        logger.info("Dodana nova bolest");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ListViewSimptoma.setItems(listaSimptoma);
        ListViewSimptoma.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
