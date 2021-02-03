package main.java.sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main extends Application {

    public static Stage mainStage;

    protected static final Logger logger = LoggerFactory.getLogger(Main.class);


    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("pocetniEkran.fxml"));

        //mainStage.setTitle("COVID-19 TRACKER");
        mainStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/css/icon.png")));

        Scene scene = new Scene(root, 600, 400);

        mainStage.setScene(scene);
        mainStage.show();
        mainStage.setResizable(false);


        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

        exec.scheduleAtFixedRate(() -> {
            exec.execute(new Thread());
            Platform.runLater(new Runnable(){
                @Override public void run(){
                    DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    Calendar cali = Calendar.getInstance();
                    cali.getTime();
                    String time = timeFormat.format(cali.getTimeInMillis());
                    Main.mainStage.setTitle("COVID-19 TRACKER" + "                                " +
                            "                                                            " + time);
                }
            });
        }, 0, 1, TimeUnit.SECONDS);



        mainStage.setOnCloseRequest(k -> {
            logger.info("Ugašena aplikacija");
            Platform.exit();
            System.exit(0);
        });
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage newStage) {
        mainStage = newStage;
    }

    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
        //   new UnosIzDatoteka().unos()
        BazaPodataka.unos();
        launch(args);
    }
}
