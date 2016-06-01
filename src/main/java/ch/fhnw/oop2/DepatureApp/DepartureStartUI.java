package ch.fhnw.oop2.DepatureApp;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 * Createwd by Steven on 27.05.16.
 */


public class DepartureStartUI extends BorderPane {

    private DepatureAppUI AppUI;
    final Line rail = new Line(10, 400, 1000, 400);
    final Line trainLine = new Line(200, 430, 760, 430);

    final PathTransition transition;
    final PathTransition transitionRail;
    final Duration duration = Duration.seconds(2.5);

    private ImageView imageView;
    private ImageView imageView2;
    private ImageView background;



    public DepartureStartUI(DepatureAppUI AppUI) {
        this.AppUI = AppUI;

        this.imageView2 = new ImageView(new Image("station.png"));
        imageView2.setFitWidth(320);
        imageView2.setFitHeight(200);
        imageView2.setX(625);
        imageView2.setY(200.75);

        this.background = new ImageView(new Image("background.png"));
        background.setFitWidth(1000);
        background.setFitHeight(500);


        this.imageView = new ImageView(new Image("zugx.png"));
        imageView.setFitWidth(456);
        imageView.setFitHeight(300);


        rail.setFill(Color.BLACK);
        rail.setTranslateY(45);


        this.transition = new PathTransition(duration, trainLine, imageView);
        this.transitionRail = new PathTransition(duration, rail);


        this.setCenter(new Pane(background, rail, imageView2,  imageView));
    }

    public void animationPlay()
    {
        this.transition.play();
        Timeline timeline = new Timeline(new KeyFrame(
                duration,
                te -> {
                    getChildren().clear();
                    setCenter(AppUI);
                }
        ));
        timeline.play();
    }

}
