package ch.fhnw.oop2.DepatureApp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Creaeted by Jennifer Müller on 24.04.2016.
 */
public class DepartureAppApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        DepatureAppPM pm = new DepatureAppPM();

        DepartureAppUI rootPanel = new DepartureAppUI(pm);

        DepartureStartUI startUI = new DepartureStartUI(rootPanel);
        startUI.setPrefSize(1000, 500);

        Scene scene = new Scene(startUI);

        String stylesheet = getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
        startUI.animationPlay();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
