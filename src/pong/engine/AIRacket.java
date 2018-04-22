package pong.engine;

import javafx.beans.property.DoubleProperty;

import static com.sun.javafx.util.Utils.clamp;
import static java.lang.Math.abs;

public class AIRacket extends Racket
{
    private final DoubleProperty ballY;

    public AIRacket(Engine engine, Ball ball, RacketSide side, double speed)
    {
        super(engine, side, speed);

        this.ballY = ball.positionYProperty();
    }

    @Override
    protected double nextHeight()
    {
        double sign = (ballY.getValue() + getRacketHeight() / 2 - getRacketY()) / abs(ballY.getValue() + getRacketHeight() / 2 - getRacketY());
        return getRacketY() + sign * getSpeed() * clamp(abs((ballY.getValue() + getRacketHeight() / 2 - getRacketY()) / getSpeed()), 0, 0.1);
    }
}
