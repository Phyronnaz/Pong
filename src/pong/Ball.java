package pong;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import static java.lang.Math.random;

public class Ball extends GameObject
{
    private final Engine engine;

    private final double radius;
    private final LevelManager levelManager;
    private final Timeline timeline;
    private boolean first = true;

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

    private final DoubleProperty speedX = new SimpleDoubleProperty();
    private final DoubleProperty speedY = new SimpleDoubleProperty();
    private final DoubleProperty positionX = new SimpleDoubleProperty();
    private final DoubleProperty positionY = new SimpleDoubleProperty();

    public Ball(Engine engine, LevelManager levelManager)
    {
        super(engine);

        this.engine = engine;

        this.radius = engine.getBallRadius();
        this.levelManager = levelManager;

        Vector2D speed = levelManager.getInitialSpeed();
        Vector2D position = levelManager.getInitialPosition();

        this.speedX.setValue(speed.x);
        this.speedY.setValue(speed.y);

        this.positionX.setValue(position.x);
        this.positionY.setValue(position.y);

        this.timeline = new Timeline();
        this.timeline.setOnFinished(this::next);

        engine.setBall(this);
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
    public void reset()
    {
        timeline.stop();
        timeline.getKeyFrames().clear();

        Vector2D newPosition = levelManager.getInitialPosition();
        Vector2D newSpeed = levelManager.getInitialSpeed();

        if (random() > 0.5)
        {
            newPosition = newPosition.flipAroundVerticalLine(engine.getWorldWidth() / 2);
            newSpeed = newSpeed.flipAroundVerticalLine(0);
        }

        positionXProperty().setValue(newPosition.x);
        positionYProperty().setValue(newPosition.y);

        speedXProperty().setValue(newSpeed.x);
        speedYProperty().setValue(newSpeed.y);

        first = true;

        timeline.play();
    }

    @Override
    public void stop()
    {
        timeline.stop();
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

        double newDt;
        if (first)
        {
            first = false;
            newDt = dt * 10;
        }
        else
        {
            newDt = dt;
        }

        Duration time = timeline.getCurrentTime().add(Duration.millis(newDt));

        KeyFrame keyFrame = new KeyFrame(time, keyValuePositionX, keyValuePositionY, keyValueSpeedX, keyValueSpeedY);
        timeline.getKeyFrames().add(keyFrame);
        timeline.playFrom(timeline.getCurrentTime());
    }
}
