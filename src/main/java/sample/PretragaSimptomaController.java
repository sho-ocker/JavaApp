package main.java.sample;

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

public class PretragaSimptomaController extends Main implements Initializable {

    private static ObservableList<Simptom> observableListSimptoma;

    @FXML
    private TextField nazivSimptoma;

    @FXML
    private TableView<Simptom> tablicaSimptoma;

    @FXML
    private TableColumn<Simptom, String> stupacNaziv;

    @FXML
    private TableColumn<Simptom, String> stupacVrijednost;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        stupacNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        stupacVrijednost.setCellValueFactory(new PropertyValueFactory<>("vrijednost"));

        observableListSimptoma = FXCollections.observableArrayList(simptomiIzDat);
        tablicaSimptoma.setItems(observableListSimptoma);
    }

    public void search() {
        logger.info("Pretraživanje simptoma");
        String naziv = nazivSimptoma.getText();

        List<Simptom> filtriranaListaSimptoma = simptomiIzDat.stream()
                .filter(z -> z.getNaziv().toLowerCase().contains(naziv))
                .collect(Collectors.toList());

        observableListSimptoma.clear();
        observableListSimptoma.addAll(FXCollections.observableArrayList(filtriranaListaSimptoma));
    }
}
