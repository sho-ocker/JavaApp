package hr.java.covidportal.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *  Služi kako bi klasi osoba mogli dodijeliti određenu županiju u njen atribut
 */

public class Zupanija extends ImenovaniEntitet implements Serializable {
    private String naziv;
    private Integer brojStanovnika;
    private Integer brojZarazenih;
    private Long id;

    /**
     * Konstruktor s nazivom i brojem stanovnika kao parametrima
     * @param naziv -> string koji predstavlja ime županije
     * @param brojStanovnika -> broj koji predstavlja broj stanovnika županije
     */

    public Zupanija(String naziv, Integer brojStanovnika, Integer brojZarazenih, Long id) {
        super(naziv, id);
        this.brojStanovnika = brojStanovnika;
        this.brojZarazenih = brojZarazenih;
    }

    public Zupanija() {

    }

    public Zupanija(String naziv,Integer brojStanovnika, Integer brojZarazenih) {
        super(naziv);
        this.brojStanovnika = brojStanovnika;
        this.brojZarazenih = brojZarazenih;
    }

    /**
     * getter stanovnika
     *
     * @return -> vraća broj stanovnika županije
     */

    public Integer getBrojStanovnika() {
        return brojStanovnika;
    }

    /**
     * setter stanovnika
     *
     * @param brojStanovnika -> postavlja atribut na vrijednost parametra
     */

    public void setBrojStanovnika(Integer brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public Integer getBrojZarazenih() {
        return brojZarazenih;
    }

    public void setBrojZarazenih(Integer brojZarazenih) {
        this.brojZarazenih = brojZarazenih;
    }

    public double getPostotakZarazenosti(){
        return ((double)this.brojZarazenih/this.brojStanovnika)*100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Zupanija)) return false;
        if (!super.equals(o)) return false;
        Zupanija zupanija = (Zupanija) o;
        return Objects.equals(getNaziv(), zupanija.getNaziv()) &&
                Objects.equals(getBrojStanovnika(), zupanija.getBrojStanovnika());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNaziv(), getBrojStanovnika());
    }

    @Override
    public String toString() {
      /*  return "Zupanija{" +
                "id=" + getId() +
                ", naziv='" + getNaziv() + '\'' +
                ", brojStanovnika=" + brojStanovnika +
                ", brojZarazenih=" + brojZarazenih +
                '}';    */
        return getNaziv();
    }
}
