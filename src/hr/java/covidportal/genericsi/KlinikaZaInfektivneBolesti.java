package hr.java.covidportal.genericsi;

import hr.java.covidportal.model.Osoba;
import hr.java.covidportal.model.Virus;
import hr.java.covidportal.sort.SortVirus;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KlinikaZaInfektivneBolesti<T extends Virus, S extends Osoba> {
    private List<T> ListaVirusa;
    private List<S> ListaOsoba;

    public KlinikaZaInfektivneBolesti(List<T> listaVirusa, List<S> listaOsoba) {
        ListaVirusa = listaVirusa;
        ListaOsoba = listaOsoba;
    }

    private int br = 0;
    public void SortVirus(){
         ListaVirusa.stream()
                .sorted(Comparator.comparing(Virus::getNaziv).reversed())
                .forEach(virus -> System.out.println(++br + ". " + virus.getNaziv()));
        return;
    }

    public void SortVirusBezLambde(){
        Collections.sort(ListaVirusa, new SortVirus());
        int br=0;
        for(Virus vir : ListaVirusa)
            System.out.println(++br + ". " + vir.getNaziv());
        return;
    }

    public List<T> getListaVirusa() {
        return ListaVirusa;
    }

    public void setListaVirusa(List<T> listaVirusa) {
        ListaVirusa = listaVirusa;
    }

    public List<S> getListaOsoba() {
        return ListaOsoba;
    }

    public void setListaOsoba(List<S> listaOsoba) {
        ListaOsoba = listaOsoba;
    }
}
