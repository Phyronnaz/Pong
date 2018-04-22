package pong;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Ball implements GameObject
{
    private final Engine engine;

    private final double radius;
    private final Vector2D initialSpeed;
    private final Vector2D initialPosition;
    private final Timeline timeline;

    public double getSpeedX()
    {
        return speedX.get();
    }

    public DoubleProperty speedXProperty()
    {
        return speedX;
    }

    public double getSpeedY()
    {
        return speedY.get();
    }

    public DoubleProperty speedYProperty()
    {
        return speedY;
    }

    public double getPositionX()
    {
        return positionX.get();
    }

    public DoubleProperty positionXProperty()
    {
        return positionX;
    }

    public double getPositionY()
    {
        return positionY.get();
    }

    public DoubleProperty positionYProperty()
    {
        return positionY;
    }

    private DoubleProperty speedX = new SimpleDoubleProperty();
    private DoubleProperty speedY = new SimpleDoubleProperty();
    private DoubleProperty positionX = new SimpleDoubleProperty();
    private DoubleProperty positionY = new SimpleDoubleProperty();

    public Ball(Engine engine, Vector2D speed, Vector2D position)
    {
        this.engine = engine;

        this.radius = engine.getBallRadius();
        this.initialSpeed = speed;
        this.initialPosition = new Vector2D(engine.getWorldWidth() / 2, engine.getWorldHeight() / 2);

        this.speedX.setValue(speed.x);
        this.speedY.setValue(speed.y);

        this.positionX.setValue(position.x);
        this.positionY.setValue(position.y);

        this.timeline = new Timeline();
        this.timeline.setOnFinished(this::next);

        engine.addScriptObject(this);
    }

    public double getRadius()
    {
        return radius;
    }

    @Override
    public void start()
    {
        timeline.play();
    }

    @Override
    public void nextLevel()
    {
        timeline.stop();
        timeline.getKeyFrames().clear();

        Vector2D newPosition = initialPosition.randomTranslate(-50, 50, -50, 50);
        Vector2D newSpeed = initialSpeed.randomRotate();

        positionXProperty().setValue(newPosition.x);
        positionYProperty().setValue(newPosition.y);

        speedXProperty().setValue(newSpeed.x);
        speedYProperty().setValue(newSpeed.y);
    }

    private void next(ActionEvent e)
    {
        Vector2D position = new Vector2D(getPositionX(), getPositionY());
        Vector2D speed = new Vector2D(getSpeedX(), getSpeedY());

        CollisionPoint collisionPoint = engine.getWorld().getClosestIntersection(position, speed, radius);
        assert collisionPoint != null;

        final double dt = collisionPoint.getDt();

        position = position.add(speed.mul(dt));
        speed = collisionPoint.getNewSpeed();

        KeyValue keyValuePositionX = new KeyValue(positionXProperty(), position.x);
        KeyValue keyValuePositionY = new KeyValue(positionYProperty(), position.y);

        KeyValue keyValueSpeedX = new KeyValue(speedXProperty(), speed.x);
        KeyValue keyValueSpeedY = new KeyValue(speedYProperty(), speed.y);

        Duration time = timeline.getCurrentTime().add(Duration.millis(dt));

        KeyFrame keyFrame = new KeyFrame(time, keyValuePositionX, keyValuePositionY, keyValueSpeedX, keyValueSpeedY);
        timeline.getKeyFrames().add(keyFrame);
        timeline.playFrom(timeline.getCurrentTime());
    }
}
