package hr.java.covidportal.sort;

import hr.java.covidportal.model.Virus;

import java.util.Comparator;

public class SortVirus implements Comparator<Virus> {

    @Override
    public int compare(Virus prvi, Virus drugi) {
        if(prvi.getNaziv().compareTo(drugi.getNaziv()) < 0) {
            return 1;
        }
        else if (prvi.getNaziv().compareTo(drugi.getNaziv()) > 0) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
