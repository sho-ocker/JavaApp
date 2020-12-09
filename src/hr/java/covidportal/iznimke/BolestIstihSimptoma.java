package hr.java.covidportal.iznimke;

/**
 *  Služi za provjeru da li je već unesena bolest istih simptoma
 */

public class BolestIstihSimptoma extends RuntimeException{

    /**
     * Konstruktor s porukom kao parametrom
     *
     * @param message -> poruka koju šaljemo
     */

    public BolestIstihSimptoma(String message) {
        super(message);
    }

    /**
     * Konstruktor s iznimkom kao parametrom
     *
     * @param cause -> iznimka koju šaljemo
     */

    public BolestIstihSimptoma(Throwable cause) {
        super(cause);
    }

    /**
     * Konstruktor s porukom i iznimkom kao parametrima
     *
     * @param message -> poruka koju šaljemo
     * @param cause -> iznimka koju šaljemo
     */

    public BolestIstihSimptoma(String message, Throwable cause) {
        super(message, cause);
    }
}
