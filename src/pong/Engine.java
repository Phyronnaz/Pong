package pong;

public class Engine
{
    private final double windowWidth;
    private final double windowHeight;
    private final double racketWidth;
    private final double racketDistanceToWall;

    public Engine(double windowWidth, double windowHeight, double racketWidth, double racketDistanceToWall)
    {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.racketWidth = racketWidth;
        this.racketDistanceToWall = racketDistanceToWall;
    }

    public double getWorldWidth()
    {
        return windowWidth - 2 * (racketWidth + racketDistanceToWall);
    }

    public double getWorldHeight()
    {
        return windowHeight;
    }

    public boolean checkIfWon(Vector2D position)
    {
        return false;
    }

    public Vector2D getScreenPosition(Vector2D position)
    {
        return new Vector2D( racketWidth + racketDistanceToWall + position.x, windowHeight - position.y);
    }

    public double getScreenLength(double length)
    {
        return length;
    }
}
