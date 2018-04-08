package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class AIBracket extends Racket
{
    private Ball ball;
    private Timeline timeline = new Timeline();

    public AIBracket(int width, boolean left, Ball ball)
    {
        super(width, left);

        this.ball = ball;
        timeline.setOnFinished(this::next);
        timeline.play();
    }

    private void next(ActionEvent e)
    {
        double new_height = ball.getTranslateY() - Parameters.racket_height / 2;
        KeyValue height_keyvalue = new KeyValue(translateYProperty(), new_height);

        Duration time = timeline.getCurrentTime().add(Duration.millis(Parameters.aiTickRate));

        KeyFrame keyframe = new KeyFrame(time, height_keyvalue);

        timeline.getKeyFrames().add(keyframe);
        timeline.playFrom(timeline.getCurrentTime());
    }
}
