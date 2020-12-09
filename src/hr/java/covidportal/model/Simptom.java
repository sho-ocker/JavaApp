package hr.java.covidportal.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *  Služi kako bi klasi bolest mogli dodijeliti određene simptome u njen atribut
 */

public class Simptom extends ImenovaniEntitet implements Serializable {
    private String naziv;
    private String vrijednost;
    private Long id;


    /**
     * Konstruktor s nazivom i vrijednošću kao parametrima
     *
     * @param naziv -> string koji predstavlja naziv simptoma
     * @param vrijednost -> string koji predstavlja učestalost simptoma
     */

    public Simptom(String naziv, String vrijednost, Long id) {
        super(naziv, id);
        this.vrijednost = vrijednost;
    }

    /**
     * getter vrijednosti
     *
     * @return -> vraća atribut vrijednosti
     */

    public String getVrijednost() {
        return vrijednost;
    }

    /**
     * setter vrijednosti
     *
     * @param vrijednost -> postavlja atribut vrijednost na vrijednost parametra
     */

    public void setVrijednost(String vrijednost) {
        this.vrijednost = vrijednost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Simptom)) return false;
        if (!super.equals(o)) return false;
        Simptom simptom = (Simptom) o;
        return Objects.equals(getNaziv(), simptom.getNaziv()) &&
                Objects.equals(getVrijednost(), simptom.getVrijednost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNaziv(), getVrijednost());
    }

    @Override
    public String toString() {
     /*   return "Simptom{" +
                "id=" + getId() +
                ", naziv='" + getNaziv() + '\'' +
                ", vrijednost='" + vrijednost + '\'' +
                '}';    */
        return getNaziv();
    }
}