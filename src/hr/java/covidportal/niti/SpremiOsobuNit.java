package hr.java.covidportal.niti;

import hr.java.covidportal.model.Osoba;
import main.java.sample.BazaPodataka;

import java.io.IOException;
import java.sql.SQLException;


public class SpremiOsobuNit extends BazaPodataka implements Runnable {
    private String threadName;
    private Thread t;

    private Osoba osoba;

    public SpremiOsobuNit(String threadName, Osoba osoba) {
        this.osoba = osoba;
        this.threadName = threadName;
        t = new Thread();
        System.out.println("Napravljena nit: " + threadName);
        t.start();
    }

    @Override
    public synchronized void run() {
        dohvatiOsobuNit();
    }

    public synchronized void dohvatiOsobuNit(){
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
        try {
            spremiNovuOsobu(osoba);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("završena nit: " + threadName);
        aktivnaVezaSBazomPodataka = false;
        notifyAll();
    }
}
