package hr.java.covidportal.niti;

import hr.java.covidportal.model.Simptom;
import main.java.sample.BazaPodataka;
import main.java.sample.DodavanjeNovogSimptomaController;

import java.io.IOException;
import java.sql.SQLException;


public class SpremiSimptomNit extends BazaPodataka implements Runnable {
    private String threadName;
    private Thread t;

    private String naziv;
    private String vrijednost;
    private Long id;


    public SpremiSimptomNit(String threadName, String naziv, String enumm, Long lastID) {
        this.threadName = threadName;
        this.naziv = naziv;
        this.vrijednost = enumm;
        this.id = lastID;


        t = new Thread();
        System.out.println("Napravljena nit: " + threadName);
        t.start();
    }

    @Override
    public synchronized void run() {
        try {
            dohvatiSimptomNit();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void dohvatiSimptomNit() throws InterruptedException, SQLException, IOException {
        while(aktivnaVezaSBazomPodataka == true){
            try {
                System.out.println("Netko je već unutra!");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        aktivnaVezaSBazomPodataka = true;

        System.out.println("Ulazi nova nit: " + threadName);
        spremiNoviSimptom(new Simptom(naziv, vrijednost, id));

        aktivnaVezaSBazomPodataka = false;
        System.out.println("Završena nit: " + threadName);
        notifyAll();
    }
}
