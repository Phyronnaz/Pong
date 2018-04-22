package pong.world;

import pong.Vector2D;

public interface StaticCollision
{
    CollisionPoint getIntersection(Vector2D position, Vector2D speed, double radius);
}
