package main.java.sample;


import hr.java.covidportal.model.Zupanija;
import hr.java.covidportal.niti.SpremiZupanijuNit;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;


public class DodavanjeNoveZupanijeController extends BazaPodataka{

    @FXML
    private TextField nazivZupanije;

    @FXML
    private TextField brojStanovnika;

    @FXML
    private TextField brojZarazenih;


    public void dodajZupaniju() throws SQLException, IOException, InterruptedException {
        String naziv = nazivZupanije.getText();
        String brStanovnika = brojStanovnika.getText();
        String brZarazenih = brojZarazenih.getText();

        Long lastID = (long) listaZupanija.size() + 1;

        if(naziv.isEmpty() || brStanovnika.isEmpty() || brZarazenih.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška u spremanju");
            alert.setContentText("Sva polja moraju biti popunjena, pokušajte ponovno!");
            alert.showAndWait();
            return;
        }


        if(!brStanovnika.matches("\\d+") || !brZarazenih.matches("\\d+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška u tipu podataka");
            alert.setContentText("Tražni podatak mora biti broj!");
            alert.showAndWait();
            return;
        }


        listaZupanija.add(new Zupanija(naziv, Integer.parseInt(brStanovnika), Integer.parseInt(brZarazenih), lastID));
        SpremiZupanijuNit nitZupanija = new SpremiZupanijuNit
                ("ZUPANIJE", naziv, Integer.parseInt(brStanovnika), Integer.parseInt(brZarazenih), lastID);

        service.execute(nitZupanija);

     //   spremiNovuZupaniju(new Zupanija(naziv, Integer.parseInt(brStanovnika), Integer.parseInt(brZarazenih), lastID));



    /*    try(FileWriter fw = new FileWriter("dat/zupanije.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("\n" + lastID);
            out.println(naziv);
            out.println(brStanovnika);
            out.print(brZarazenih);
        } catch (IOException e) {
            System.err.println(e);
        }   */


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spremanje podatka");
        alert.setContentText("Nova županija je uspješno spremljena!");
        alert.showAndWait();
        logger.info("Dodana nova županija");
    }

}
