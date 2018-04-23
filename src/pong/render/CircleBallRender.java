package pong.render;

import javafx.scene.Node;
import javafx.scene.shape.Circle;
import pong.core.Ball;
import pong.core.Engine;
import pong.core.GameObject;

/**
 * Circle render for the ball
 */
public class CircleBallRender extends GameObject
{
    private final Circle circle;

    public CircleBallRender(Engine engine, Ball ball)
    {
        super(engine);

        this.circle = new Circle(ball.getRadius());

        this.circle.translateXProperty().bind(engine.getScreenPositionXBinding(ball.positionXProperty()));
        this.circle.translateYProperty().bind(engine.getScreenPositionYBinding(ball.positionYProperty()));
    }

    @Override
    public Node getRender()
    {
        return circle;
    }
}
