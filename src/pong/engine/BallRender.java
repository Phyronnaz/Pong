package pong.engine;

import javafx.beans.property.DoubleProperty;
import pong.Vector2D;

public interface BallRender
{
    void setOnFinished(Runnable event);
    void setNewPosition(Vector2D newPosition, double dt);
    void play();
    void reset();
    DoubleProperty heightProperty();
}
