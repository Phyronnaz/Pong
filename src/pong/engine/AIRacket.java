package pong.engine;

import static com.sun.javafx.util.Utils.clamp;
import static java.lang.Math.*;

public class AIRacket extends Racket
{
    public AIRacket(Engine engine, Ball ball, RacketSide side, double speed)
    {
        super(engine, ball, side, speed);
    }

    @Override
    protected double nextHeight()
    {
        double sign = (getBallHeight() + racketHeight / 2 - getRacketHeight()) / abs(getBallHeight() + racketHeight / 2 - getRacketHeight());
        return getRacketHeight() + sign * speed * clamp(abs((getBallHeight() + racketHeight / 2  - getRacketHeight()) / speed), 0, 0.1);
    }
}
