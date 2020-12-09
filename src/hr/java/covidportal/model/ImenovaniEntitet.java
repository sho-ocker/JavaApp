package hr.java.covidportal.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *  Služi kako bi ujedinili sve klase koje sadrže atribut naziv
 */

public abstract class ImenovaniEntitet implements Serializable{
    private String naziv;
    private Long id;

    /**
     * Konstruktor koji sadrži parametar naziv
     *
     * @param naziv -> string koji predstavlja ime
     */

    public ImenovaniEntitet(String naziv, Long id) {
        this.naziv = naziv;
        this.id = id;
    }

    public ImenovaniEntitet() {
    }

    /**
     * setter naziva
     *
     * @param naziv -> postavlja parametar naziv u atribut naziv
     */

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    /**
     * getter naziva
     *
     * @return -> vraća naziv entiteta
     */

    public String getNaziv() {
        return naziv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImenovaniEntitet)) return false;
        ImenovaniEntitet that = (ImenovaniEntitet) o;
        return Objects.equals(getNaziv(), that.getNaziv());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNaziv());
    }

    @Override
    public String toString() {
      /*  return "ImenovaniEntitet{" +
                "id=" + id +
                ", naziv='" + naziv + '\'' +
                '}';    */
        return getNaziv();
    }
}
