package pong;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class HumanRacket extends Racket
{
    private double currentY;
    private final Timeline timeline;
    private boolean isDownPressed = false;
    private boolean isUpPressed = false;

    public HumanRacket(Engine engine, RacketSide side, double speed)
    {
        super(engine, side, speed);
        engine.addOnKeyPressed(this::actionOnKeyPressed);
        engine.addOnKeyReleased(this::actionOnKeyReleased);

        this.currentY = getInitialRacketY();
        this.timeline = new Timeline();

        timeline.setOnFinished(this::onFinished);
        timeline.play();
    }

    private void onFinished(ActionEvent e)
    {
        if (isDownPressed)
        {
            currentY -= 5 * getSpeed();
        }
        if (isUpPressed)
        {
            currentY += 5 * getSpeed();
        }

        Duration time = timeline.getCurrentTime().add(Duration.millis(5));

        KeyFrame keyframe = new KeyFrame(time, (KeyValue) null);

        timeline.getKeyFrames().add(keyframe);
        timeline.playFrom(timeline.getCurrentTime());
    }

    @Override
    protected double nextHeight()
    {
        return currentY;
    }

    private void actionOnKeyPressed(KeyEvent event)
    {
        if (getSide() == RacketSide.LEFT)
        {
            switch (event.getCode())
            {
                case LEFT:
                    isDownPressed = true;
                    break;
                case RIGHT:
                    isUpPressed = true;
                    break;
            }
        }
        else
        {
            switch (event.getCode())
            {
                case UP:
                    isUpPressed = true;
                    break;
                case DOWN:
                    isDownPressed = true;
                    break;
            }
        }
    }

    private void actionOnKeyReleased(KeyEvent event)
    {
        if (getSide() == RacketSide.LEFT)
        {
            switch (event.getCode())
            {
                case LEFT:
                    isDownPressed = false;
                    break;
                case RIGHT:
                    isUpPressed = false;
                    break;
            }
        }
        else
        {
            switch (event.getCode())
            {
                case UP:
                    isUpPressed = false;
                    break;
                case DOWN:
                    isDownPressed = false;
                    break;
            }
        }
    }

    @Override
    public void reset()
    {
        currentY = getInitialRacketY();
        super.reset();
    }
}

