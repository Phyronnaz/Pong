package pong;

import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import java.util.function.Function;

public interface BallRender
{
    void setOnFinished(Runnable event);
    void setNewPosition(Vector2D newPosition, double dt);
    void play();
    void reset();
    DoubleProperty heightProperty();
}
