package hr.java.covidportal.iznimke;

/**
 *  Služi za provjeru da li je unutar polja kontaktiranih osoba, unesena ista osoba kao i prijašnjih unosa
 */

public class DuplikatKontaktiraneOsobe extends Exception{

    /**
     * Konstruktor s porukom kao parametrom
     *
     * @param message -> poruka koju šaljemo
     */

    public DuplikatKontaktiraneOsobe(String message) {
        super(message);
    }

    /**
     * Konstruktor s iznimkom kao parametrom
     *
     * @param cause -> iznimka koju šaljemo
     */

    public DuplikatKontaktiraneOsobe(Throwable cause) {
        super(cause);
    }

    /**
     * Konstruktor s porukom i iznimkom kao parametrima
     *
     * @param message -> poruka koju šaljemo
     * @param cause -> iznimka koju šaljemo
     */

    public DuplikatKontaktiraneOsobe(String message, Throwable cause) {
        super(message, cause);
    }
}
