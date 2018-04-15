package pong;

import javafx.beans.property.DoubleProperty;

public class Ball implements ScriptObject
{
    private final World world;
    private final Engine engine;

    private final double radius;
    private final Vector2D initialSpeed;
    private final Vector2D initialPosition;

    private BallRender ballRender;

    private Vector2D speed;
    private Vector2D position;

    public Ball(Engine engine, World world, Vector2D speed, Vector2D position)
    {
        this.world = world;
        this.engine = engine;

        this.radius = engine.getBallRadius();
        this.initialSpeed = speed;
        this.initialPosition = new Vector2D(engine.getWorldWidth() / 2, engine.getWorldHeight() / 2);

        this.speed = speed;
        this.position = position;

        engine.addScriptObject(this);
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
        ballRender.reset();
        position = initialPosition;
        speed = initialSpeed;
        ballRender.setNewPosition(this.position, 0);
        ballRender.play();
    }

    public DoubleProperty heightProperty()
    {
        return ballRender.heightProperty();
    }

    private void next()
    {
        Vector2D oldPosition = position;

        CollisionPoint collisionPoint = world.getClosestIntersection(position, speed, radius);
        assert collisionPoint != null;

        final double dt = collisionPoint.getDt();

        position = position.add(speed.mul(dt));
        speed = collisionPoint.getNewSpeed();

        ballRender.setNewPosition(position, dt);

        engine.checkIfWon(oldPosition);
    }
}
