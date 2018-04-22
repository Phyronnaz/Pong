package pong.core;

import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class Vector2D
{
    private static Random rand = new Random();
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

    public Vector2D rotate(double angle) {
        return new Vector2D(x*cos(angle) - y*sin(angle), x*sin(angle)+y*cos(angle));
    }

    public double length()
    {
        return sqrt(x * x + y * y);
    }

    public Vector2D getNormal()
    {
        double size = length();
        return new Vector2D(x / size, y / size);
    }

    public Vector2D getOrthogonal()
    {
        return new Vector2D(-y, x);
    }

    public Vector2D flipAroundVerticalLine(double lineX)
    {
        return new Vector2D(lineX - (x - lineX), y);
    }

    public Vector2D randomDirection(double standard_deviation) {
        return rotate(rand.nextGaussian()*standard_deviation);
    }
}