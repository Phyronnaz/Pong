package pong.world;
import pong.Vector2D;

public class Segment implements StaticCollision
{
    private final Vector2D start;
    private final Vector2D end;

    public Segment(Vector2D start, Vector2D end)
    {
        this.start = start;
        this.end = end;
    }

    @Override
    public CollisionPoint getIntersection(Vector2D position, Vector2D speed, double radius)
    {
        final Vector2D n = getNormal(speed.mul(-1));
        final Vector2D startToEnd = end.sub(start);
        final Vector2D u = startToEnd.getNormal();

        // Collision with the line
        final Vector2D pointOnCircle = position.add(n.mul(-radius));
        final double speedDotN = Vector2D.dotProduct(speed, n);

        if(-speedDotN <= 10e-7)
            return intersectWithPoints(position, speed, radius);

        final double dt = Vector2D.dotProduct(start.sub(pointOnCircle), n) / speedDotN;

        if(dt <= 0)
            return intersectWithPoints(position, speed, radius);

        final Vector2D startToIntersection = speed.mul(dt).add(pointOnCircle).sub(start);
        final double s2iDots2e = Vector2D.dotProduct(startToIntersection, startToEnd);

        if(0 > s2iDots2e || s2iDots2e > Vector2D.dotProduct(startToEnd, startToEnd))
            return intersectWithPoints(position, speed, radius);

        final Vector2D newSpeed = u.mul(Vector2D.dotProduct(speed, u)).add(n.mul(-Vector2D.dotProduct(speed,n)));

        return new CollisionPoint(dt, newSpeed);
    }

    private CollisionPoint intersectWithPoints(Vector2D position, Vector2D speed, double radius) {
        double dt = Double.POSITIVE_INFINITY;
        Vector2D newSpeed = new Vector2D(0,0);

        for(Vector2D point : new Vector2D[]{start, end}) {
            final Vector2D pointToPos = position.sub(point);
            final double a = Vector2D.dotProduct(speed, speed);
            final double b = 2*Vector2D.dotProduct(speed, pointToPos);
            final double c = Vector2D.dotProduct(pointToPos, pointToPos) - radius * radius;

            final double delta = b*b-4*a*c;

            if(delta < 10e-7)
                continue;

            final double dt1 = (-b-Math.sqrt(delta))/(2*a);
            if(dt1 < 10e-7)
                continue;

            if(dt1 < dt) {
                dt = dt1;
                final Vector2D finalPosition = speed.mul(dt).add(position);
                final Vector2D u = finalPosition.sub(point).getNormal(); final Vector2D v = u.getOrthogonal();
                newSpeed = u.mul(-Vector2D.dotProduct(u,speed)).add(v.mul(Vector2D.dotProduct(v, speed)));
            }
        }
        return new CollisionPoint(dt, newSpeed);
    }


    public Vector2D getStart()
    {
        return start;
    }

    public Vector2D getEnd()
    {
        return end;
    }

    public Vector2D getNormal(Vector2D colinearVector)
    {
        final double dx = end.x - start.x;
        final double dy = end.y - start.y;
        Vector2D N1 = new Vector2D(-dy, dx).getNormal();
        Vector2D N2 = new Vector2D(dy, -dx).getNormal();

        return Vector2D.dotProduct(N1, colinearVector) > 0 ? N1 : N2;
    }
}
