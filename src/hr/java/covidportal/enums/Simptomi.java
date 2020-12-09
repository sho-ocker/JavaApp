package hr.java.covidportal.enums;

public enum Simptomi {

    RIJETKO("RIJETKO"),
    SREDNJE("SREDNJE"),
    CESTO("ČESTO");

    private String vrijednost;

    Simptomi(String vrijednost) {
        this.vrijednost = vrijednost;
    }

    public String getVrijednost() {
        return vrijednost;
    }
}
