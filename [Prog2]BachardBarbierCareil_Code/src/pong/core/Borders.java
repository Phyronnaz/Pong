package pong.core;

/**
 * Screen Borders collisions
 */
public class Borders implements StaticCollision
{
    private final double width;
    private final double height;

    /**
     * Constructor
     *
     * @param width  Screen width
     * @param height Screen height
     */
    public Borders(double width, double height)
    {
        this.width = width;
        this.height = height;
    }

    @Override
    public CollisionPoint getIntersection(Vector2D position, Vector2D speed, double radius)
    {
        double dtx;
        double dty;
        {
            if (speed.x < 0)
            {
                dtx = (radius - position.x) / speed.x;
            }
            else if (speed.x > 0)
            {
                dtx = (width - radius - position.x) / speed.x;
            }
            else
            {
                dtx = Double.POSITIVE_INFINITY;
            }
            assert dtx >= 0;

            if (speed.y < 0)
            {
                dty = (radius - position.y) / speed.y;
            }
            else if (speed.y > 0)
            {
                dty = (height - radius - position.y) / speed.y;
            }
            else
            {
                dty = Double.POSITIVE_INFINITY;
            }
            assert dty >= 0;
        }

        double dt;
        Vector2D newSpeed;

        if (dtx < dty)
        {
            dt = dtx;
            newSpeed = new Vector2D(-speed.x, speed.y);
        }
        else if (dty < dtx)
        {
            dt = dty;
            newSpeed = new Vector2D(speed.x, -speed.y);
        }
        else
        {
            dt = dtx;
            newSpeed = new Vector2D(-speed.x, -speed.y);
        }

        return new CollisionPoint(dt, newSpeed);
    }
}
