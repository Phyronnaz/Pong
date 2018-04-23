package pong.core;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import static java.lang.Math.abs;

/**
 * Abstract racket class
 */
public abstract class Racket extends GameObject
{
    private final Timeline timeline;
    private final DoubleProperty racketY;
    private final RacketSide side;
    private final double racketHeight;
    private final double initialRacketY;
    private final double speed;

    /**
     * Constructor
     *
     * @param engine Main engine
     * @param side   Racket side
     * @param speed  Speed of the racket
     */
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

    /**
     * Should be overridden by child classes
     *
     * @return the target Y position
     */
    protected double nextY()
    {
        return initialRacketY;
    }

    /**
     * Timeline callback
     */
    private void next(ActionEvent e)
    {
        double targetHeight = nextY();
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

    @Override
    public void stop()
    {
        timeline.stop();
    }

    /**
     * @return the racket side
     */
    public RacketSide getSide()
    {
        return side;
    }

    /**
     * @return the racket height
     */
    public double getRacketHeight()
    {
        return racketHeight;
    }

    /**
     * @return the racket initial Y position
     */
    public double getInitialRacketY()
    {
        return initialRacketY;
    }

    /**
     * @return the racket speed
     */
    public double getSpeed()
    {
        return speed;
    }

    /**
     * @return the racket Y position
     */
    public double getRacketY()
    {
        return racketY.get();
    }
}
