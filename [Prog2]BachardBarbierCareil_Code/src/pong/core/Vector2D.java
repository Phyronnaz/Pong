package pong.core;

import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * 2D double vector class
 */
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

    /**
     * Dot product between A and B
     *
     * @param A A
     * @param B B
     * @return dotProduct of A and B
     */
    public static double dotProduct(Vector2D A, Vector2D B)
    {
        return A.x * B.x + A.y * B.y;
    }

    /**
     * Add other to this
     *
     * @param other Vector to add
     * @return this + other
     */
    public Vector2D add(Vector2D other)
    {
        return new Vector2D(x + other.x, y + other.y);
    }

    /**
     * Subtract other from this
     *
     * @param other Vector to subtract
     * @return this - other
     */
    public Vector2D sub(Vector2D other)
    {
        return new Vector2D(x - other.x, y - other.y);
    }

    /**
     * Multiply this by other
     *
     * @param other Factor
     * @return this * other
     */
    public Vector2D mul(double other)
    {
        return new Vector2D(x * other, y * other);
    }

    /**
     * Rotate this by angle
     *
     * @param angle angle to rotate by
     * @return this rotated by angle
     */
    public Vector2D rotate(double angle)
    {
        return new Vector2D(x * cos(angle) - y * sin(angle), x * sin(angle) + y * cos(angle));
    }

    /**
     * @return Length of this vector
     */
    public double length()
    {
        return sqrt(x * x + y * y);
    }

    /**
     * @return This vector normalized
     */
    public Vector2D getNormal()
    {
        double size = length();
        return new Vector2D(x / size, y / size);
    }

    /**
     * @return Orthogonal of this vector
     */
    public Vector2D getOrthogonal()
    {
        return new Vector2D(-y, x);
    }

    /**
     * Flip this vector around a vertical line at x = lineX
     *
     * @param lineX line x position
     * @return flipped vector
     */
    public Vector2D flipAroundVerticalLine(double lineX)
    {
        return new Vector2D(lineX - (x - lineX), y);
    }

    /**
     * Rotate this randomly using a gaussian
     *
     * @param standard_deviation gaussian std
     * @return this rotated randomly
     */
    public Vector2D randomDirection(double standard_deviation)
    {
        return rotate(rand.nextGaussian() * standard_deviation);
    }
}