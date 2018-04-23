package pong.core;

import java.util.Vector;

/**
 * Handles collisions
 */
public class World
{
    private final Vector<StaticCollision> staticCollisions = new Vector<>();
    private final Borders borders;

    /**
     * Constructor
     *
     * @param width  width of the world
     * @param height height of the world
     */
    public World(double width, double height)
    {
        this.borders = new Borders(width, height);
    }

    /**
     * Add a static collision object
     *
     * @param staticCollision Object to add
     */
    public void addStaticCollision(StaticCollision staticCollision)
    {
        staticCollisions.add(staticCollision);
    }

    /**
     * Get the closest intersection
     *
     * @param position Ball position
     * @param speed    Ball speed
     * @param radius   Ball radius
     * @return Closest CollisionPoint
     */
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
