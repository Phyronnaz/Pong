package pong;

import static java.lang.Math.*;

public class Vector2D
{
    public double x;
    public double y;

    public Vector2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public static double dotProduct(Vector2D A, Vector2D B)
    {
        return A.x * B.x + A.y * B.y;
    }

    public static double distance(Vector2D A, Vector2D B)
    {
        return A.sub(B).length();
    }

    public static double squaredDistance(Vector2D A, Vector2D B)
    {
        return A.sub(B).squaredLength();
    }

    public Vector2D add(Vector2D other)
    {
        return new Vector2D(x + other.x, y + other.y);
    }

    public Vector2D sub(Vector2D other)
    {
        return new Vector2D(x - other.x, y - other.y);
    }

    public Vector2D mul(double other)
    {
        return new Vector2D(x * other, y * other);
    }

    public double length()
    {
        return sqrt(x * x + y * y);
    }

    public double squaredLength()
    {
        return x * y + y * y;
    }

    public Vector2D getNormal()
    {
        double size = length();
        return new Vector2D(x / size, y / size);
    }

    public double maxComponent()
    {
        return max(x, y);
    }

    public double minComponent()
    {
        return min(x, y);
    }

    /**
     * ax + by + c = 0
     * @param a
     * @param b
     * @param c
     * @return
     */
    public double distanceToLine(double a, double b, double c)
    {
        return abs(a * x + b * y + c) / sqrt(a * a + b * b);
    }
}
