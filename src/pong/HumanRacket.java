package pong;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class HumanRacket extends Racket
{
    double currentHeight;

    public HumanRacket(Engine engine, Ball ball, RacketSide side, double speed)
    {
        super(engine, ball, side, speed);
        engine.addOnKeyPressed(this::actionOnKeyPressed);

        this.currentHeight = initialRacketHeight;
    }

    @Override
    protected double nextHeight()
    {
        return currentHeight;
    }

    private void actionOnKeyPressed(KeyEvent event)
    {
        if (side == RacketSide.LEFT)
        {
            switch (event.getCode())
            {
                case LEFT:
                    currentHeight += 50;
                    break;
                case RIGHT:
                    currentHeight -= 50;
                    break;
            }
        }
        else
        {
            switch (event.getCode())
            {
                case UP:
                    currentHeight += 50;
                    break;
                case DOWN:
                    currentHeight -= 50;
                    break;
            }
        }
    }

    @Override
    public void racketReset()
    {
        currentHeight = initialRacketHeight;
    }
}

