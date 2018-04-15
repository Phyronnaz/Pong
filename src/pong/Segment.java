package pong;

import javafx.util.Pair;

import java.util.Vector;

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

        double t = Double.POSITIVE_INFINITY;
        double u;

        boolean parallel = false;

        if (S2.y != 0)
        {
            final double k = S2.x / S2.y;
            if (S1.x - k * S1.y != 0)
            {
                t = ((P2.x - P1.x) - k * (P2.y - P1.y)) / (S1.x - k * S1.y);
            }
            else
            {
                parallel = true;
            }
        }
        else
        {
            if (S1.y == 0)
            {
                parallel = true;
            }
            else
            {
                t = (P2.y - P1.y) / S1.y;
            }
        }

        if (S2.x != 0)
        {
            u = (P1.x - P2.x + t * S1.x) / S2.x;
        }
        else
        {
            assert S2.y != 0;
            u = (P1.y - P2.y + t * S1.y) / S2.y;
        }
        final double length = Vector2D.distance(start, end);

        if (!parallel && 0 <= u && 0 <= t && t <= length)
        {
            final Vector2D newSpeed = speed.sub(n.mul(2 * Vector2D.dotProduct(speed, n)));
            return new CollisionPoint(u, newSpeed);
        }
        else
        {
            // Y = AX + B <-> AX - Y + B = 0
            final double A = speed.y / speed.x;
            final double B = -position.x * A + position.y;

            assert abs(position.y - (A * position.x + B)) < 0.00001;

            Vector<Pair<Double, Vector2D>> dtsAndDirections = new Vector<>();

            for (Vector2D X : new Vector2D[]{start, end})
            {
                final double X1 = X.x;
                final double Y1 = X.y;
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

                    dtsAndDirections.add(new Pair<>(dtA, new Vector2D(X2A - X1, Y2A - Y1).getNormal()));
                    dtsAndDirections.add(new Pair<>(dtB, new Vector2D(X2B - X1, Y2B - Y1).getNormal()));
                }
            }
            double minDt = Double.POSITIVE_INFINITY;
            Vector2D minDirection = new Vector2D(0, 0);
            for (Pair<Double, Vector2D> pair : dtsAndDirections)
            {
                final double dt = pair.getKey();
                final Vector2D direction = pair.getValue();

                if (0 < dt && dt < minDt)
                {
                    minDt = dt;
                    minDirection = direction;
                }
            }
            final Vector2D newSpeed = speed.sub(minDirection.mul(2 * Vector2D.dotProduct(speed, minDirection)));
            return minDt < Double.POSITIVE_INFINITY ? new CollisionPoint(minDt, newSpeed) : null;
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
