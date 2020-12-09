package hr.java.covidportal.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Služi kako bi program mogao vršiti osnovne funkcionalnosti
 */

public class Osoba implements Serializable {
    private final String ime;
    private final String prezime;
    private final Integer starost;
    private final Zupanija zupanija;
    private Bolest zarazenBolescu;
    private final List<Osoba> kontaktiraneOsobe;
    private final Long id;

    /**
     * Konstruktor osobe sa svim atributima
     */

    public Osoba(Builder builder){
        this.ime = builder.ime;
        this.prezime = builder.prezime;
        this.starost = builder.starost;
        this.zupanija = builder.zupanija;
        this.zarazenBolescu = builder.zarazenBolescu;
        this.kontaktiraneOsobe = builder.kontaktiraneOsobe;
        this.id = builder.id;
    }

    /**
     * getteri za atribute osobe
     *
     * @return -> vraćaju traženi atribut
     */

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }
    public Integer getStarost(){
        return starost;
    }
    public Zupanija getZupanija() {
        return zupanija;
    }
    public Bolest getZarazenBolescu() {
        return zarazenBolescu;
    }
    public List<Osoba> getKontaktiraneOsobe() {
        return kontaktiraneOsobe;
    }
    public Long getId() {
        return id;
    }

    public void setZarazenVirusom(Virus virus){
        this.zarazenBolescu = virus;
    }
    public void setZarazenBolescu(Bolest zarazenBolescu) {
        this.zarazenBolescu = zarazenBolescu;
    }

    @Override
    public String toString() {
      /*  return "Osoba{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", starost=" + starost +
                ", " + zupanija +
                ", zarazenBolescu=" + zarazenBolescu +
                ", kontaktiraneOsobe=" + kontaktiraneOsobe +
                '}';    */
        return getIme() + " " + getPrezime();
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Služi kao poboljšanja verzija konstruktora osobe(ne moraju svi atributi biti dodani)
     */

    public static class Builder {
        private String ime;
        private String prezime;
        private Integer starost;
        private Zupanija zupanija;
        private Bolest zarazenBolescu;
        private List<Osoba> kontaktiraneOsobe;
        private Long id;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Builder)) return false;
            Builder builder = (Builder) o;
            return Objects.equals(ime, builder.ime) &&
                    Objects.equals(prezime, builder.prezime) &&
                    Objects.equals(starost, builder.starost) &&
                    Objects.equals(zupanija, builder.zupanija) &&
                    Objects.equals(zarazenBolescu, builder.zarazenBolescu) &&
                    Objects.equals(kontaktiraneOsobe, builder.kontaktiraneOsobe);
        }

        @Override
        public int hashCode() {
            return Objects.hash(ime, prezime, starost, zupanija, zarazenBolescu, kontaktiraneOsobe);
        }


        public Builder(String ime, String prezime) {
            this.ime = ime;
            this.prezime = prezime;
        }

        /**
         * setteri za atribute koje sadrži klasa osoba
         */

        public Builder setStarost(Integer starost) {
            this.starost = starost;
            return this;
        }

        public Builder setZupanija(Zupanija zupanija) {
            this.zupanija = zupanija;
            return this;
        }

        public Builder setZarazenBolescu(Bolest zarazenBolescu) {
            this.zarazenBolescu = zarazenBolescu;
            return this;
        }

        public Builder setKontaktiraneOsobe(List<Osoba> kontaktiraneOsobe) {
            this.kontaktiraneOsobe = kontaktiraneOsobe;
            return this;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Služi za konstruiranje osobe i dodavanje svih njenih atributa, te širenje virusa ako osoba u polju
         * kontaktiranih osoba sadrži određene osobe
         *
         * @return -> vraća osobu sa svim atributima(konstruktor)
         */

        public Osoba build() {
            Osoba osoba = new Osoba(this);
            if (zarazenBolescu instanceof Virus virus) {
                if (kontaktiraneOsobe != null)
                    for (int i = 0; i < kontaktiraneOsobe.size(); i++)
                        if (kontaktiraneOsobe.toArray()[i] != null)
                            kontaktiraneOsobe.get(i).setZarazenBolescu(virus);
            }
            return osoba;
        }
    }


  /* public Osoba(String ime, String prezime, Integer starost, Zupanija zupanija, Bolest zarazenBolescu,
                 Osoba[] kontaktiraneOsobe) {
        this.ime = ime;
        this.prezime = prezime;
        this.starost = starost;
        this.zupanija = zupanija;
        this.zarazenBolescu = zarazenBolescu;
        this.kontaktiraneOsobe = kontaktiraneOsobe;

        if(zarazenBolescu instanceof Virus ){
            if(kontaktiraneOsobe != null)
               for (int i=0; i<kontaktiraneOsobe.length; i++)
                   if(kontaktiraneOsobe[i] != null)
                        kontaktiraneOsobe[i].setZarazenBolescu(zarazenBolescu);
        }


    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setStarost(Integer starost) {
        this.starost = starost;
    }

    public void setZupanija(Zupanija zupanija) {
        this.zupanija = zupanija;
    }

    public void setZarazenBolescu(Bolest zarazenBolescu) {
        this.zarazenBolescu = zarazenBolescu;
    }

    public void setKontaktiraneOsobe(Osoba[] kontaktiraneOsobe) {
        this.kontaktiraneOsobe = kontaktiraneOsobe;
    }*/
}
