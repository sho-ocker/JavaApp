package hr.java.covidportal.model;


import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *  Služi kako bi klasi osoba mogli dodijeliti određenu bolest u njen atribut
 */

public class Bolest extends ImenovaniEntitet implements Serializable {
    private List<Simptom> simptomi;
    private Boolean virus;

    /**
     * Konstruktor s nazivom i poljem simptoma kao parametrima
     *
     * @param naziv -> string koji predstavlja ime bolesti
     * @param simptomi -> polje simptoma koji određuju simptome bolesti
     */

    public Bolest(String naziv, List<Simptom> simptomi, Boolean virus, Long id) {
        super(naziv, id);
        this.simptomi = simptomi;
        this.virus = virus;
    }

    public Bolest(String naziv, Boolean virus){
        super(naziv);
        this.virus = virus;
    }



    public Bolest() {
        super();
    }

    public Bolest(String naziv, Long id) {
        super(naziv, id);
    }

    public Bolest(String naziv, List<Simptom> simptomi, Long id) {
    }

    public Bolest(String naziv, List<Simptom> simptomi, boolean virus) {
        super(naziv);
        this.simptomi = simptomi;
        this.virus = virus;
    }

    /**
     * getter simptoma
     *
     * @return -> vraća polje simptoma
     */

    public List<Simptom> getSimptomi() {
        return simptomi;
    }

    public Boolean getVirus() {
        return virus;
    }



    /**
     * setter simptoma
     *
     * @param simptomi -> postavlja polje simptoma u atribut simptomi
     */

    public void setSimptomi(List<Simptom> simptomi) {
        this.simptomi = simptomi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bolest)) return false;
        if (!super.equals(o)) return false;
        Bolest bolest = (Bolest) o;
        return Objects.equals(getNaziv(), bolest.getNaziv()) &&
                Objects.equals(getSimptomi(), bolest.getSimptomi());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNaziv(), getSimptomi());
    }

    @Override
    public String toString() {
      /*  return "Bolest{" +
                "id=" + getId() +
                ", naziv='" + getNaziv() + '\'' +
                ", simptomi=" + simptomi +
                '}';    */
        return getNaziv();
    }
}
