package pong;

public class AIRacket extends Racket
{
    public AIRacket(Engine engine, Ball ball, RacketSide side, double speed)
    {
        super(engine, ball, side, speed);
    }

    @Override
    protected double nextHeight()
    {
        return ballHeightProperty.getValue() + racketHeight / 2;
    }
}
