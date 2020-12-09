package main.java.sample;

import hr.java.covidportal.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static hr.java.covidportal.main.Glavna.*;


public class Main extends Application {

    protected static Stage mainStage;

    protected static List<Zupanija> zupanijeIzDat = new ArrayList<>();
    protected static List<Simptom> simptomiIzDat = new ArrayList<>();
    protected static List<Bolest> bolestiIzDat = new ArrayList<>();
    protected static List<Virus> virusiIzDat = new ArrayList<>();
    protected static List<Osoba> osobeIzDat = new ArrayList<>();

    protected static File zupanijeDat = new File("dat/zupanije.txt");
    protected static File simptomiDat = new File("dat/simptomi.txt");
    protected static File bolestiDat = new File("dat/bolesti.txt");
    protected static File virusiDat = new File("dat/virusi.txt");
    protected static File osobeDat = new File("dat/osobe.txt");

    protected static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Override
    public void start(Stage primaryStage) throws Exception{
        logger.info("Pokrenuta aplikacija");
        mainStage = primaryStage;
        Parent root = FXMLLoader.load( getClass().getClassLoader().getResource("pocetniEkran.fxml"));

        mainStage.setTitle("Desktop Aplikacija");
        mainStage.setScene(new Scene(root, 600, 400));
        mainStage.show();
        mainStage.setResizable(false);

        mainStage.setOnCloseRequest(t -> {
            logger.info("Ugašena aplikacija");
            Platform.exit();
            System.exit(0);
        });
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage newStage) {
        mainStage = newStage;
    }

    public static void main(String[] args) {
        unosZupanija(zupanijeIzDat, zupanijeDat);
        unosSimptoma(simptomiIzDat, simptomiDat);
        unosBolesti(bolestiIzDat, bolestiDat, simptomiIzDat);
        unosVirusa(virusiIzDat, virusiDat, simptomiIzDat);
        unosOsoba(osobeIzDat, osobeDat, zupanijeIzDat, bolestiIzDat, virusiIzDat);
        launch(args);
    }
}
