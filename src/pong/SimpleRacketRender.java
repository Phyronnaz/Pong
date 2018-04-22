package pong;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class SimpleRacketRender extends GameObject
{
    private final Rectangle rectangle;

    public SimpleRacketRender(Engine engine, Racket racket)
    {
        super(engine);

        this.rectangle = new Rectangle(engine.getRacketWidth(), engine.getRacketHeight());

        RacketSide side = racket.getSide();

        double translateX;
        switch (side)
        {
            case LEFT:
                translateX = engine.getRacketDistanceToWall();
                break;
            default:
                translateX = engine.getWindowWidth() - engine.getRacketDistanceToWall() - engine.getRacketWidth();
                break;
        }
        rectangle.setTranslateX(translateX);

        rectangle.translateYProperty().bind(engine.getScreenPositionYBinding(engine.getRacketYProperty(side)));
    }

    @Override
    public Node getRender()
    {
        return rectangle;
    }
}
