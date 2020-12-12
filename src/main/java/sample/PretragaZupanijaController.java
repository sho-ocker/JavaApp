package main.java.sample;

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

public class PretragaZupanijaController extends UnosIzDatoteka implements Initializable{

    private static ObservableList<Zupanija> observableListZupanija;

    @FXML
    private TextField nazivZupanije;

    @FXML
    private TableView<Zupanija> tablicaZupanija;

    @FXML
    private TableColumn<Zupanija, String> stupacNaziv;

    @FXML
    private TableColumn<Zupanija, String> stupacBrojStanovnika;

    @FXML
    private TableColumn<Zupanija, String> stupacBrojZarazenih;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        stupacNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        stupacBrojStanovnika.setCellValueFactory(new PropertyValueFactory<>("brojStanovnika"));
        stupacBrojZarazenih.setCellValueFactory(new PropertyValueFactory<>("brojZarazenih"));

        observableListZupanija = FXCollections.observableArrayList(zupanijeIzDat);
        tablicaZupanija.setItems(observableListZupanija);
    }

    public void search() {
        logger.info("Pretraživanje županija");
        String naziv = nazivZupanije.getText();

        List<Zupanija> filtriranaListaZupanija = zupanijeIzDat.stream()
                .filter(z -> z.getNaziv().toLowerCase().contains(naziv))
                .collect(Collectors.toList());

        observableListZupanija.clear();
        observableListZupanija.addAll(FXCollections.observableArrayList(filtriranaListaZupanija));
    }

}
