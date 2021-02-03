package hr.java.covidportal.enums;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;

public enum Simptomi {

    Produktivni("Produktivni"),
    Intenzivno("Intenzivno"),
    Visoka("Visoka"),
    Jaka("Jaka");


    private String vrijednost;

    Simptomi(String vrijednost) {
        this.vrijednost = vrijednost;
    }

    public String getVrijednost() {
        return vrijednost;
    }

}
