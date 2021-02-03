package hr.java.covidportal.niti;

import hr.java.covidportal.model.Zupanija;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.java.sample.BazaPodataka;
import main.java.sample.Main;

import java.util.Comparator;

public class NajviseZarazenihNit extends BazaPodataka implements Runnable{

    @Override
    public void run() {
        while(true){
            Zupanija najveciPostotak = listaZupanija.stream()
                    .max(Comparator.comparing(Zupanija::getPostotakZarazenosti))
                    .get();

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), e -> {
                        Main.mainStage.setTitle("Najveći postotak ima županija: " + najveciPostotak.getNaziv() + " od " +
                                najveciPostotak.getPostotakZarazenosti() + "%");
                    })
            );
            timeline.play();

            System.out.println("Najveći postotak ima županija: " + najveciPostotak.getNaziv() + " od " +
                    najveciPostotak.getPostotakZarazenosti() + "%");

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
