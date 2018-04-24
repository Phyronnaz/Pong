package pong.core;

public interface StaticCollision
{
    CollisionPoint getIntersection(Vector2D position, Vector2D speed, double radius);
}
