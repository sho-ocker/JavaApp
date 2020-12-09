package main.java.sample;

import hr.java.covidportal.model.Bolest;
import hr.java.covidportal.model.Osoba;
import hr.java.covidportal.model.Zupanija;
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

public class PretragaOsobaController extends Main implements Initializable {

    private static ObservableList<Osoba> observableListOsoba;

    @FXML
    private TextField imeOsobe;

    @FXML
    private TextField prezimeOsobe;

    @FXML
    private TableView<Osoba> tablicaOsoba;

    @FXML
    private TableColumn<Osoba, String> stupacIme;

    @FXML
    private TableColumn<Osoba, String> stupacPrezime;

    @FXML
    private TableColumn<Osoba, Integer> stupacStarost;

    @FXML
    private TableColumn<Osoba, Zupanija> stupacZupanija;

    @FXML
    private TableColumn<Osoba, Bolest> stupacBolest;

    @FXML
    private TableColumn<Osoba, List<Osoba>> stupacKontakti;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        stupacIme.setCellValueFactory(new PropertyValueFactory<>("ime"));
        stupacPrezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));
        stupacStarost.setCellValueFactory(new PropertyValueFactory<>("starost"));
        stupacBolest.setCellValueFactory(new PropertyValueFactory<>("zarazenBolescu"));
        stupacZupanija.setCellValueFactory(new PropertyValueFactory<>("zupanija"));
        stupacKontakti.setCellValueFactory(new PropertyValueFactory<>("kontaktiraneOsobe"));

        observableListOsoba = FXCollections.observableArrayList(osobeIzDat);
        tablicaOsoba.setItems(observableListOsoba);
    }

    public void search() {
        logger.info("Pretraživanje osoba");
        String ime = imeOsobe.getText();
        String prezime = prezimeOsobe.getText();

        List<Osoba> filtriranaListaOsoba = osobeIzDat.stream()
                .filter(z -> z.getIme().toLowerCase().contains(ime) && z.getPrezime().toLowerCase().contains(prezime))
                .collect(Collectors.toList());

        observableListOsoba.clear();
        observableListOsoba.addAll(FXCollections.observableArrayList(filtriranaListaOsoba));
    }
}
