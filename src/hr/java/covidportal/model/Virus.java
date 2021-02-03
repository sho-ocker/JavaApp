package hr.java.covidportal.model;

import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.List;

/**
 *  Služi kako bi mogli generirati virus (koji nasljeđuje bolest) i dodati ga klasi osoba
 */

public class Virus extends Bolest implements Zarazno, Serializable {

    /**
     * Konstruktor s nazivom i poljem simptoma kao parametrima
     *
     * @param naziv -> string koji predstavlja ime bolesti
     * @param simptomi -> polje simptoma koji određuju simptome bolesti
     */

    public Virus(String naziv, List<Simptom> simptomi, Long id) {
        super(naziv, simptomi, id);
    }

    public Virus() {
        super();
    }

    public Virus(String naziv, Long id) {
        super(naziv, id);
    }

    public Virus(String naziv, List<Simptom> simptomi, Boolean virus, Long id) {
        super(naziv, simptomi, virus, id);
    }

    /**
     * Funkcija za širenje virusa
     *
     * @param osoba -> atribut osoba koja će biti zaražena istim virusom kao i parametar
     */

    public void prelazakZarazeNaOsobu(Osoba osoba){
        osoba.setZarazenVirusom(this);
    }
}
