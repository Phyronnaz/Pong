package pong.core;

public class CollisionPoint
{
    private final double dt;
    private final Vector2D newSpeed;

    public CollisionPoint(double dt, Vector2D newSpeed)
    {
        assert dt >= 0;

        this.dt = dt;
        this.newSpeed = newSpeed;
    }

    public double getDt()
    {
        return dt;
    }

    public Vector2D getNewSpeed()
    {
        return newSpeed;
    }
}
