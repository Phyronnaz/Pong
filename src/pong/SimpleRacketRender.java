package pong;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class SimpleRacketRender implements GameObject
{
    private final Rectangle rectangle;

    public SimpleRacketRender(Engine engine, RacketSide side)
    {
        this.rectangle = new Rectangle(engine.getRacketWidth(), engine.getRacketHeight());

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
    public void start()
    {

    }

    @Override
    public void nextLevel()
    {

    }

    @Override
    public Node getRender()
    {
        return rectangle;
    }
}
