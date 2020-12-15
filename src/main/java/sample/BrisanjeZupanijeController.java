package main.java.sample;

import hr.java.covidportal.model.Zupanija;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class BrisanjeZupanijeController extends UnosIzDatoteka{

    @FXML
    private TextField nazivZupanije;


    public void obrisiZupaniju(){
        String naziv = nazivZupanije.getText();
        Boolean zastavica = false;

        for(Zupanija zup : zupanijeIzDat){
            if(zup.getNaziv().equals(naziv)) {
                zastavica = true;
                //zupanijaTrazena = zup;
                break;
            }
        }

        if(!zastavica){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušajte ponovno");
            alert.setContentText("Ne postoji tražena županija!");
            alert.showAndWait();
            return;
        }

        zupanijeIzDat.removeIf(zupanija -> zupanija.getNaziv().equals(naziv));

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

}
