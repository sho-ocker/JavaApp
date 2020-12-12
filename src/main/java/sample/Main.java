package main.java.sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main extends Application {

    protected static Stage mainStage;

    protected static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        Parent root = FXMLLoader.load( getClass().getClassLoader().getResource("pocetniEkran.fxml"));

        mainStage.setTitle("Desktop Aplikacija");
        mainStage.setScene(new Scene(root, 600, 400));
        mainStage.show();
        mainStage.setResizable(false);

        mainStage.setOnCloseRequest(t -> {
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

    public static void main(String[] args) {
        new UnosIzDatoteka().unos();
        launch(args);
    }
}
