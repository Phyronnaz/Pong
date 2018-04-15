package pong;

import static com.sun.javafx.util.Utils.clamp;

public class Ball implements EngineObject
{
    private final World world;
    private final Engine engine;

    private final double radius;
    private final Vector2D initialSpeed;
    private final Vector2D initialPosition;

    private BallRender ballRender;

    private Vector2D speed;
    private Vector2D position;

    public Ball(Engine engine, World world, double radius, Vector2D speed, Vector2D position)
    {
        this.world = world;
        this.engine = engine;

        this.radius = radius;
        this.initialSpeed = speed;
        this.initialPosition = position;

        this.speed = speed;
        this.position = position;
    }

    public void setBallRender(BallRender ballRender)
    {
        this.ballRender = ballRender;
    }

    public double getRadius()
    {
        return radius;
    }

    @Override
    public void start()
    {
        ballRender.setOnFinished(this::next);
        ballRender.setNewPosition(this.position, 0);
        ballRender.play();
    }

    @Override
    public void reset()
    {
        position = initialPosition;
        speed = initialSpeed;
        ballRender.setNewPosition(this.position, 0);
        ballRender.play();
    }

    private void next()
    {
        if (engine.checkIfWon(position))
        {
            ballRender.die();
        }
        else
        {
            CollisionPoint collisionPoint = world.getClosestIntersection(position, speed, radius);
            assert collisionPoint != null;

            final double dt = collisionPoint.getDt();

            System.out.print(dt);
            System.out.print("\n");
            System.out.print(speed.x);
            System.out.print("; ");
            System.out.print(speed.y);
            System.out.print("\n");

            position = position.add(speed.mul(dt));
            speed = collisionPoint.getNewSpeed();

            ballRender.setNewPosition(position, dt);
        }
    }
}
