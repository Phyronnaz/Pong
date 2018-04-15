package pong;

import java.util.Vector;

public class World
{
    private Vector<StaticCollision> staticCollisions = new Vector<StaticCollision>();

    private final double width;
    private final double height;

    private final Borders borders;

    public World(Engine engine)
    {
        this.width = engine.getWorldWidth();
        this.height = engine.getWorldHeight();

        this.borders = new Borders(width, height);
    }

    public void addStaticCollision(StaticCollision staticCollision)
    {
        staticCollisions.add(staticCollision);
    }

    public CollisionPoint getClosestIntersection(Vector2D position, Vector2D speed, double radius)
    {
        CollisionPoint result = borders.getIntersection(position, speed, radius);
        for (StaticCollision staticCollision : staticCollisions)
        {
            CollisionPoint collisionPoint = staticCollision.getIntersection(position, speed, radius);
            if (collisionPoint != null)
            {
                if (result == null || result.getDt() > collisionPoint.getDt())
                {
                    result = collisionPoint;
                }
                else if (result.getDt() == collisionPoint.getDt())
                {
                    Vector2D newSpeed = speed.mul(-1);
                    result = new CollisionPoint(result.getDt(), newSpeed);
                }
            }
        }
        return result;
    }
}
