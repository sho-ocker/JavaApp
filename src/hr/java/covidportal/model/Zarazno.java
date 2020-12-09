package hr.java.covidportal.model;

/**
 * Sučelje pomoću kojeg širimo zarazu virusa
 */

public interface Zarazno {

    void prelazakZarazeNaOsobu(Osoba osoba);
}
