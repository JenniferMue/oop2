package ch.fhnw.oop2.DepatureApp;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Created by Steven on 27.05.16.
 */
public class DepartureStartUI extends Pane {

    private DepatureAppUI AppUI;
    final Line line = new Line(100, 400, 950, 400);
    final PathTransition transition;
    final Duration duration = Duration.seconds(2.);

    private ImageView imageView;

    public DepartureStartUI(DepatureAppUI AppUI) {
        this.AppUI = AppUI;
        this.imageView = new ImageView(new Image("zugx.png"));
        imageView.setFitWidth(80);
        imageView.setFitHeight(50);
        imageView.setX(125);
        imageView.setY(87.75);
        this.transition = new PathTransition(duration, line, imageView);
        this.getChildren().addAll(imageView);
    }

    public void animationPlay()
    {
        this.transition.play();
        Timeline timeline = new Timeline(new KeyFrame(
                duration,
                te -> {
                    getChildren().clear();
                    getChildren().add(AppUI);
                }
        ));
        timeline.play();
    }

}
