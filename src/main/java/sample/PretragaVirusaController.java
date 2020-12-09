package main.java.sample;

import hr.java.covidportal.model.Simptom;
import hr.java.covidportal.model.Virus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PretragaVirusaController extends Main implements Initializable {

    private static ObservableList<Virus> observableListVirusa;

    @FXML
    private TextField nazivVirusa;

    @FXML
    private TableView<Virus> tablicaVirusa;

    @FXML
    private TableColumn<Virus, String> stupacNaziv;

    @FXML
    private TableColumn<Virus, List<Simptom>> stupacSimptomi;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        stupacNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        stupacSimptomi.setCellValueFactory(new PropertyValueFactory<>("simptomi"));

        observableListVirusa = FXCollections.observableArrayList(virusiIzDat);
        tablicaVirusa.setItems(observableListVirusa);
    }

    public void search() {
        logger.info("Pretraživanje virusa");
        String naziv = nazivVirusa.getText();

        List<Virus> filtriranaListaVirusa = virusiIzDat.stream()
                .filter(z -> z.getNaziv().toLowerCase().contains(naziv))
                .collect(Collectors.toList());

        observableListVirusa.clear();
        observableListVirusa.addAll(FXCollections.observableArrayList(filtriranaListaVirusa));
    }
}
