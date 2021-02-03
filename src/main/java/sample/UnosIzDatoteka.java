package main.java.sample;

import hr.java.covidportal.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static hr.java.covidportal.main.Glavna.*;

import java.io.File;

public class UnosIzDatoteka {
    protected static ObservableList<Zupanija> zupanijeIzDat = FXCollections.observableArrayList();
    protected static ObservableList<Simptom> simptomiIzDat = FXCollections.observableArrayList();
    protected static ObservableList<Bolest> bolestiIzDat = FXCollections.observableArrayList();
    protected static ObservableList<Virus> virusiIzDat = FXCollections.observableArrayList();
    protected static ObservableList<Osoba> osobeIzDat = FXCollections.observableArrayList();

    protected static File zupanijeDat = new File("dat/zupanije.txt");
    protected static File simptomiDat = new File("dat/simptomi.txt");
    protected static File bolestiDat = new File("dat/bolesti.txt");
    protected static File virusiDat = new File("dat/virusi.txt");
    protected static File osobeDat = new File("dat/osobe.txt");

    protected static final Logger logger = LoggerFactory.getLogger(Main.class);

    public void unos() {
        logger.info("Pokrenuta aplikacija");
        unosZupanija(zupanijeIzDat, zupanijeDat);
        unosSimptoma(simptomiIzDat, simptomiDat);
        unosBolesti(bolestiIzDat, bolestiDat, simptomiIzDat);
        unosVirusa(virusiIzDat, virusiDat, simptomiIzDat);
        unosOsoba(osobeIzDat, osobeDat, zupanijeIzDat, bolestiIzDat, virusiIzDat);
    }
}
