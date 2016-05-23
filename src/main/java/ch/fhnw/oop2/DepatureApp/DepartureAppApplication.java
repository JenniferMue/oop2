package ch.fhnw.oop2.DepatureApp;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Jennifer Müller on 24.04.2016.
 */
public class DepartureAppApplication extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        DepatureAppPM pm = new DepatureAppPM();

        Parent rootPanel = new DepatureAppUI(pm);

        Scene scene = new Scene(rootPanel);

        String stylesheet = getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


