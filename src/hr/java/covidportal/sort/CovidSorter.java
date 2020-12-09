package hr.java.covidportal.sort;

import hr.java.covidportal.model.Zupanija;

import java.util.Comparator;

public class CovidSorter implements Comparator<Zupanija> {
    @Override
    public int compare(Zupanija prva, Zupanija druga) {
        if(prva.getPostotakZarazenosti() < druga.getPostotakZarazenosti()) {
            return 1;
        }
        else if (prva.getPostotakZarazenosti() > druga.getPostotakZarazenosti()) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
