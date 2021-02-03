package main.java.sample;

import hr.java.covidportal.model.Osoba;
import hr.java.covidportal.model.Zupanija;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class BrisanjeZupanijeController extends BazaPodataka implements Initializable {

    ObservableList<Zupanija> pomListaZupanija = FXCollections.observableArrayList(listaZupanija);

    @FXML
    private ListView<Zupanija> listViewZupanija;


    public void obrisiZupaniju() throws IOException, SQLException, InterruptedException {
        List<Zupanija> odabraneZupanije;
        odabraneZupanije = listViewZupanija.getSelectionModel().getSelectedItems();


        Connection veza = connectToDatabase();


        for(Zupanija zup : odabraneZupanije) {
            PreparedStatement st = veza.prepareStatement("DELETE FROM ZUPANIJA WHERE NAZIV = ?");
            st.setString(1, zup.getNaziv());
            st.executeUpdate();
            listaZupanija.remove(zup);
            pomListaZupanija.remove(zup);
        }
        closeDatabaseConnection(veza);



      /*  try{
            File tempFile = new File(zupanijeDat.getAbsolutePath() + ".tmp");

            BufferedReader rd = new BufferedReader(new FileReader(zupanijeDat));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            RandomAccessFile raf = new RandomAccessFile(zupanijeDat, "rw");


            String line;
            while ((line = rd.readLine()) != null){
                raf.seek(0);
                if(line.equals(zupanijaTrazena.getId().toString())) {
                    for(int i=0; i<4; i++){
                        rd.readLine();
                    }
                }
                pw.println(line);
            }
            pw.close();
            rd.close();
            Path from = tempFile.toPath(); //convert from File to Path
            Path to = Paths.get(String.valueOf(zupanijeDat)); //convert from String to Path
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            tempFile.delete();

        } catch (IOException e){
            e.printStackTrace();
        }   */


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Brisanje podatka");
        alert.setContentText("Županija je uspješno obrisana!");
        alert.showAndWait();
        logger.info("Obrisana županija");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Osoba oso : listaOsoba){
            for(Zupanija zup : listaZupanija){
                if(oso.getZupanija().equals(zup))
                    pomListaZupanija.remove(zup);
            }
        }
        listViewZupanija.setItems(pomListaZupanija);
        listViewZupanija.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
