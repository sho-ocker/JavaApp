package main.java.sample;

import hr.java.covidportal.model.Bolest;
import hr.java.covidportal.model.Simptom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PretragaBolestiController extends UnosIzDatoteka implements Initializable{

    private static ObservableList<Bolest> observableListBolesti;

    @FXML
    private TextField nazivBolesti;

    @FXML
    private TableView<Bolest> tablicaBolesti;

    @FXML
    private TableColumn<Bolest, String> stupacNaziv;

    @FXML
    private TableColumn<Bolest, List<Simptom>> stupacSimptomi;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        stupacNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        stupacSimptomi.setCellValueFactory(new PropertyValueFactory<>("simptomi"));

        observableListBolesti = FXCollections.observableArrayList(bolestiIzDat);
        tablicaBolesti.setItems(observableListBolesti);
    }


    public void search() {
        logger.info("Pretraživanje bolesti");
        String naziv = nazivBolesti.getText();

        List<Bolest> filtriranaListaBolesti = bolestiIzDat.stream()
                .filter(z -> z.getNaziv().toLowerCase().contains(naziv))
                .collect(Collectors.toList());

        observableListBolesti.clear();
        observableListBolesti.addAll(FXCollections.observableArrayList(filtriranaListaBolesti));
    }
}
