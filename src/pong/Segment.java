package pong;

import static java.lang.Math.*;

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

        final Vector2D P1 = start;
        final Vector2D S1 = end.sub(start).getNormal();
        final Vector2D P2 = position.add(n.mul(-radius));
        final Vector2D S2 = speed;

        // We want P1 + t * S1 = P2 + u * S2

        final double k = S2.x / S2.y;
        final double t = ((P2.x - P1.x) - k * (P2.y - P1.y)) / (S1.x - k * S1.y);
        final double u = (P1.x - P2.x + t * S1.x) / S2.x;

        final Vector2D test = P1.add(S1.mul(t)).sub(P2.add(S2.mul(u)));

        final double length = Vector2D.distance(start, end);
        final Vector2D newSpeed = speed.sub(n.mul(2 * Vector2D.dotProduct(speed, n)));

        if (u < 0)
        {
            return null;
        }
        else if (0 <= t && t <= length)
        {
            return new CollisionPoint(u, newSpeed);
        }
        else if (-2 * radius <= t && t <= length + 2 * radius)
        {
            // Y = AX + B <-> AX - Y + B = 0
            final double A = speed.y / speed.x;
            final double B = -position.x * A + position.y;

            assert abs(position.y - (A * position.x + B)) < 0.00001;

            boolean startIsClosest = start.distanceToLine(A, -1, B) < end.distanceToLine(A, -1, B);

            final double X1 = startIsClosest ? start.x : end.x;
            final double Y1 = startIsClosest ? start.y : end.y;
            final double R = radius;

            // We want (X1 - X2) ** 2 + (Y1 - Y2) ** 2 = R ** 2
            // Y2 = A * X2 + B

            final double delta = -B * B + (1 + A * A) * R * R - (-A * X1 + Y1) * (-A * X1 + Y1) + B * (-2 * A * X1 + 2 * Y1);
            if (0 <= delta)
            {
                final double X2A = (-A * B + X1 + A * Y1 - sqrt(delta)) / (1 + A * A);
                final double Y2A = A * X2A + B;

                final double dtA = (X2A - position.x) / speed.x;
                assert abs(dtA - (Y2A - position.y) / speed.y) < 0.00001;

                final double X2B = (-A * B + X1 + A * Y1 + sqrt(delta)) / (1 + A * A);
                final double Y2B = A * X2B + B;

                final double dtB = (X2B - position.x) / speed.x;
                assert abs(dtB - (Y2B - position.y) / speed.y) < 0.00001;

                return new CollisionPoint(min(dtA, dtB), newSpeed);
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
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
