package main.java.sample;

import hr.java.covidportal.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static hr.java.covidportal.main.Glavna.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UnosIzDatoteka {
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

    public void unos() {
        logger.info("Pokrenuta aplikacija");
        unosZupanija(zupanijeIzDat, zupanijeDat);
        unosSimptoma(simptomiIzDat, simptomiDat);
        unosBolesti(bolestiIzDat, bolestiDat, simptomiIzDat);
        unosVirusa(virusiIzDat, virusiDat, simptomiIzDat);
        unosOsoba(osobeIzDat, osobeDat, zupanijeIzDat, bolestiIzDat, virusiIzDat);
    }
}
