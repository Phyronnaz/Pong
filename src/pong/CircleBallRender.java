package pong;

import javafx.scene.shape.Circle;

public class CircleBallRender extends Circle
{
    public CircleBallRender(Engine engine, Ball ball)
    {
        super(engine.getScreenLength(ball.getRadius()));

        engine.addRenderObject(this);

        translateXProperty().bind(engine.getScreenPositionXBinding(ball.positionXProperty()));
        translateYProperty().bind(engine.getScreenPositionYBinding(ball.positionYProperty()));
    }
}
