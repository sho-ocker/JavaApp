package main.java.sample;

import hr.java.covidportal.model.Osoba;
import hr.java.covidportal.model.Zupanija;
import hr.java.covidportal.niti.NajviseZarazenihNit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PretragaZupanijaController extends BazaPodataka implements Initializable{

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

        observableListZupanija = FXCollections.observableArrayList(listaZupanija);
        tablicaZupanija.setItems(observableListZupanija);

    /*    tablicaZupanija.setRowFactory(tv -> {
            TableRow<Zupanija> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    Zupanija zupanija = row.getItem();
                    clicked(zupanija);
                }
            });
            return row;
        });         */

        ContextMenu cm = new ContextMenu();
        MenuItem mi1 = new MenuItem("Ukloni");
        cm.getItems().add(mi1);

        mi1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Zupanija zup = tablicaZupanija.getSelectionModel().getSelectedItem();
                if (zup != null) {
                    observableListZupanija.remove(zup);
                    listaZupanija.remove(zup);
                }


                try {
                    Connection veza = connectToDatabase();

                    PreparedStatement st3 = veza.prepareStatement("DELETE FROM KONTAKTIRANE_OSOBE\n" +
                            "\n" +
                            "WHERE OSOBA_ID IN\n" +
                            "\n" +
                            "(SELECT ID FROM OSOBA WHERE ZUPANIJA_ID = ?)\n" +
                            "\n" +
                            "OR KONTAKTIRANA_OSOBA_ID IN\n" +
                            "\n" +
                            "(SELECT ID FROM OSOBA WHERE ZUPANIJA_ID = ?)");

                    st3.setString(1, zup.getId().toString());
                    st3.setString(2, zup.getId().toString());
                    st3.executeUpdate();

                    PreparedStatement st2 = veza.prepareStatement("DELETE FROM OSOBA WHERE ZUPANIJA_ID = ?");
                    st2.setString(1, zup.getId().toString());
                    st2.executeUpdate();

                    PreparedStatement st1 = veza.prepareStatement("DELETE FROM ZUPANIJA WHERE NAZIV = ?");
                    st1.setString(1, zup.getNaziv());
                    st1.executeUpdate();

                    closeDatabaseConnection(veza);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        tablicaZupanija.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if (t.getButton() == MouseButton.SECONDARY) {
                    cm.show(tablicaZupanija, t.getScreenX(), t.getScreenY());
                }
            }
        });


        tablicaZupanija.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if(t.getButton().equals(MouseButton.PRIMARY)){
                    if(t.getClickCount() == 2) {
                        Zupanija zup = tablicaZupanija.getSelectionModel().getSelectedItem();
                        List<Osoba> ispis = new ArrayList<>();
                        try{
                            Connection veza = connectToDatabase();
                            Statement stmt = veza.createStatement();

                            ResultSet rs = stmt.executeQuery("SELECT OSOBA.* FROM OSOBA WHERE ZUPANIJA_ID = '" + zup.getId() + "'");

                            while (rs.next()) {
                                int id = rs.getInt("id");
                                String ime = rs.getString("ime");
                                String prezime = rs.getString("prezime");
                                Date date = (Date) rs.getDate("datum_rodjenja");
                                Instant instant = Instant.ofEpochMilli(date.getTime());
                                LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
                                int zupanija_id = rs.getInt("zupanija_id");
                                int bolest_id = rs.getInt("bolest_id");


                                ispis.add(new Osoba.Builder(ime, prezime)
                                        .setId((long) id)
                                        .build());
                            }

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Popis osoba");
                            alert.setHeaderText("Osobe koje se nalaze u odabranoj županiji:");
                            alert.setContentText(ispis.toString());
                            // alert.setContentText("ID: " + id + "\n" + "IME: " + ime + "\n" + "PREZIME: " + prezime + "\n");
                            alert.showAndWait();

                            closeDatabaseConnection(veza);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

     /*   NajviseZarazenihNit nit = new NajviseZarazenihNit();
        Thread t = new Thread(nit);
        t.start();  */
    }

    public void search() {
        logger.info("Pretraživanje županija");
        String naziv = nazivZupanije.getText();

        List<Zupanija> filtriranaListaZupanija = listaZupanija.stream()
                .filter(z -> z.getNaziv().toLowerCase().contains(naziv))
                .collect(Collectors.toList());

        observableListZupanija.clear();
        observableListZupanija.addAll(FXCollections.observableArrayList(filtriranaListaZupanija));
    }

    public void clicked(Zupanija zupanija){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ZupanijaShow.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);
            Stage stg = new Stage();        //
            stg.setScene(scene);            //  .setCenterPane()
            stg.show();                     //

            PrikaziZupanijuController controller = loader.getController();
            controller.prikaziZupaniju(zupanija);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
