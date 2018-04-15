package pong;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import static java.lang.Math.abs;

public class Racket implements ScriptObject
{
    protected final DoubleProperty ballHeightProperty;
    protected final DoubleProperty racketHeightProperty;
    protected final double racketHeight;
    protected final double initialRacketHeight;
    protected final double speed;

    private Timeline timeline;

    public Racket(Engine engine, Ball ball, RacketSide side, double speed)
    {
        this.ballHeightProperty = ball.heightProperty();
        this.racketHeightProperty = engine.getRacketYProperty(side);
        this.racketHeight = engine.getRacketHeight();
        this.initialRacketHeight = engine.getWorldHeight() / 2 + racketHeight / 2;
        this.speed = speed;

        timeline = new Timeline();
        timeline.setOnFinished(this::next);

        engine.addScriptObject(this);
    }

    protected double nextHeight()
    {
        return initialRacketHeight;
    }

    private void next(ActionEvent e)
    {
        final double targetHeight = nextHeight();
        final double dt = abs((racketHeightProperty.getValue() - targetHeight) / speed);

        Duration time = timeline.getCurrentTime().add(Duration.millis(dt));

        KeyValue heightKeyvalue = new KeyValue(racketHeightProperty, targetHeight);
        KeyFrame keyframe = new KeyFrame(time, heightKeyvalue);

        timeline.getKeyFrames().add(keyframe);
        timeline.playFrom(timeline.getCurrentTime());
    }

    @Override
    public void start()
    {
        racketHeightProperty.setValue(initialRacketHeight);
        timeline.play();
    }

    @Override
    public void reset()
    {
        timeline.getKeyFrames().clear();
        racketHeightProperty.setValue(initialRacketHeight);
    }
}
