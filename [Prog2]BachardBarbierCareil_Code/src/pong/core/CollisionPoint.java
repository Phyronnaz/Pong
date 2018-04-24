package pong.core;

/**
 * Possible collision point; defined by a dt and the new speed after bounce
 */
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

    /**
     * @return dt
     */
    public double getDt()
    {
        return dt;
    }

    /**
     * @return new speed after bounce
     */
    public Vector2D getNewSpeed()
    {
        return newSpeed;
    }
}
