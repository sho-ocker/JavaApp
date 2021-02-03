package hr.java.covidportal.niti;

import hr.java.covidportal.model.Zupanija;
import main.java.sample.BazaPodataka;

import java.io.IOException;
import java.sql.SQLException;


public class SpremiZupanijuNit extends BazaPodataka implements Runnable {
    private String threadName;
    private Thread t;

    private String naziv;
    private Integer brojStanovnika;
    private Integer brojZarazenih;
    private Long id;


    public SpremiZupanijuNit(String threadName, String naziv, Integer brStanovnika, Integer brZarazenih, Long id) {
        this.threadName = threadName;
        this.naziv = naziv;
        this.brojStanovnika = brStanovnika;
        this.brojZarazenih = brZarazenih;
        this.id = id;

        t = new Thread();
        System.out.println("Napravljena nit: " + threadName);
        t.start();
    }

    @Override
    public synchronized void run() {
        try {
            dohvatiZupanijuNit();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void dohvatiZupanijuNit() throws InterruptedException, SQLException, IOException {
        while(aktivnaVezaSBazomPodataka == true){
            System.out.println("netko je vec unutra!");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        aktivnaVezaSBazomPodataka = true;


        System.out.println("ulazi nova nit: " + threadName);
        spremiNovuZupaniju(new Zupanija(naziv, brojStanovnika, brojZarazenih, id));


        System.out.println("završena nit: " + threadName);
        aktivnaVezaSBazomPodataka = false;
        notifyAll();
    }
}
