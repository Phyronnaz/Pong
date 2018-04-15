package pong;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Circle;

import javafx.event.ActionEvent;
import javafx.util.Duration;

public class CircleBallRender extends Circle implements BallRender
{
    private final Engine engine;
    private final Timeline timeline;
    private Runnable onFinished;
    private final DoubleProperty heigthProperty;

    public CircleBallRender(Engine engine, Ball ball)
    {
        super(engine.getScreenLength(ball.getRadius()));

        engine.addRenderObject(this);

        this.engine = engine;
        this.heigthProperty = new SimpleDoubleProperty();

        translateYProperty().addListener(
                (observable, oldValue, newValue) ->
                {
                    heigthProperty.setValue(engine.getLocalPosition(new Vector2D(0, newValue.doubleValue())).y);
                }
        );

        timeline = new Timeline();
        timeline.setOnFinished(this::next);

        ball.setBallRender(this);
    }

    @Override
    public void setOnFinished(Runnable event)
    {
        onFinished = event;
    }

    @Override
    public void setNewPosition(Vector2D newPosition, double dt)
    {
        assert dt >= 0;

        final Vector2D position = engine.getScreenPosition(newPosition);

        KeyValue keyValueX = new KeyValue(translateXProperty(), position.x);
        KeyValue keyValueY = new KeyValue(translateYProperty(), position.y);

        Duration time = timeline.getCurrentTime().add(Duration.millis(dt));

        KeyFrame keyFrame = new KeyFrame(time, keyValueX, keyValueY);
        timeline.getKeyFrames().add(keyFrame);
        timeline.playFrom(timeline.getCurrentTime());
    }

    @Override
    public void play()
    {
        timeline.play();
    }

    @Override
    public void reset()
    {
        timeline.stop();
        timeline.getKeyFrames().clear();
    }

    @Override
    public DoubleProperty heightProperty()
    {
        return heigthProperty;
    }

    private void next(ActionEvent e)
    {
        onFinished.run();
    }
}
