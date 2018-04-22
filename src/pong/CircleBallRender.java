package pong;

import javafx.scene.Node;
import javafx.scene.shape.Circle;

public class CircleBallRender extends GameObject
{
    private final Circle circle;

    public CircleBallRender(Engine engine, Ball ball)
    {
        super(engine);

        this.circle = new Circle(engine.getScreenLength(ball.getRadius()));

        this.circle.translateXProperty().bind(engine.getScreenPositionXBinding(ball.positionXProperty()));
        this.circle.translateYProperty().bind(engine.getScreenPositionYBinding(ball.positionYProperty()));
    }

    @Override
    public Node getRender()
    {
        return circle;
    }
}
