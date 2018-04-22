package pong.engine;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.util.Duration;

import static java.lang.Math.abs;

public abstract class Racket extends GameObject
{
    private final Timeline timeline;
    private final DoubleProperty racketY;
    private final RacketSide side;
    private final double racketHeight;
    private final double initialRacketY;
    private final double speed;

    public Racket(Engine engine, RacketSide side, double speed)
    {
        super(engine);

        this.side = side;
        this.racketY = engine.getRacketYProperty(side);
        this.racketHeight = engine.getRacketHeight();
        this.initialRacketY = engine.getWorldHeight() / 2 + racketHeight / 2;
        this.speed = speed;

        timeline = new Timeline();
        timeline.setOnFinished(this::next);
    }

    protected double nextHeight()
    {
        return initialRacketY;
    }

    private void next(ActionEvent e)
    {
        double targetHeight = nextHeight();
        if (Double.isNaN(targetHeight))
        {
            targetHeight = getRacketY();
        }
        final double dt = abs((racketY.getValue() - targetHeight) / speed) + 1;

        Duration time = timeline.getCurrentTime().add(Duration.millis(dt));

        KeyValue heightKeyvalue = new KeyValue(racketY, targetHeight);
        KeyFrame keyframe = new KeyFrame(time, heightKeyvalue);

        timeline.getKeyFrames().add(keyframe);
        timeline.playFrom(timeline.getCurrentTime());
    }

    @Override
    public void start()
    {
        racketY.setValue(initialRacketY);
        timeline.play();
    }

    @Override
    public void reset()
    {
        timeline.getKeyFrames().clear();
        racketY.setValue(initialRacketY);
    }

    public RacketSide getSide()
    {
        return side;
    }

    public double getRacketHeight()
    {
        return racketHeight;
    }

    public double getInitialRacketY()
    {
        return initialRacketY;
    }

    public double getSpeed()
    {
        return speed;
    }

    public double getRacketY()
    {
        return racketY.get();
    }
}
