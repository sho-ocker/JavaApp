package hr.java.covidportal.niti;

import hr.java.covidportal.model.Bolest;
import hr.java.covidportal.model.Simptom;
import hr.java.covidportal.model.Virus;
import main.java.sample.BazaPodataka;

import java.io.IOException;
import java.sql.SQLException;


public class SpremiBolestNit extends BazaPodataka implements Runnable {
    private String threadName;
    private Thread t;
    private Bolest bolest;


    public SpremiBolestNit(String threadName, Virus bolest) {
        this.bolest = bolest;
        this.threadName = threadName;
        t = new Thread();
        System.out.println("Napravljena nit: " + threadName);
        t.start();
    }

    public SpremiBolestNit(String threadName, Bolest bolest) {
        this.bolest = bolest;
        this.threadName = threadName;
        t = new Thread();
        System.out.println("Napravljena nit: " + threadName);
        t.start();
    }

    @Override
    public synchronized void run() {
        dohvatiBolestNit();
    }

    public synchronized void dohvatiBolestNit(){
        while(aktivnaVezaSBazomPodataka == true){
            try {
                System.out.println("netko je vec unutra!");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        aktivnaVezaSBazomPodataka = true;

        try {
            System.out.println("ulazi nova nit: " + threadName);
            spremiNovuBolest(bolest);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        aktivnaVezaSBazomPodataka = false;
        System.out.println("završena nit: " + threadName);
        notifyAll();
    }
}
