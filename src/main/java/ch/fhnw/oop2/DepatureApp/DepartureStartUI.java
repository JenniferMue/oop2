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
    final Line line = new Line(100, 400, 750, 400);
    final PathTransition transition;
    final Duration duration = Duration.seconds(1.5);

    private ImageView imageView;
    private ImageView imageView2;
    private ImageView imageView3;

    public DepartureStartUI(DepatureAppUI AppUI) {
        this.AppUI = AppUI;
        this.imageView2 = new ImageView(new Image("station.png"));
        imageView2.setFitWidth(160);
        imageView2.setFitHeight(100);
        imageView2.setX(825);
        imageView2.setY(320.75);
        this.imageView3 = new ImageView(new Image("fhnw.png"));
        imageView3.setFitWidth(160);
        imageView3.setFitHeight(100);
        imageView3.setX(125);
        imageView3.setY(120.75);
        this.imageView = new ImageView(new Image("zugx.png"));
        imageView.setFitWidth(80);
        imageView.setFitHeight(50);
        imageView.setX(125);
        imageView.setY(87.75);


        this.transition = new PathTransition(duration, line, imageView);
        this.getChildren().addAll(imageView, imageView2, imageView3);
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
