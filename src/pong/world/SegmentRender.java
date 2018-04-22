package pong.world;

import javafx.scene.shape.Line;
import pong.engine.Engine;
import pong.Vector2D;

public class SegmentRender extends Line
{
    public SegmentRender(Engine engine, Segment segment)
    {
        super();

        Vector2D screenStart = engine.getScreenPosition(segment.getStart());
        Vector2D screenEnd = engine.getScreenPosition(segment.getEnd());

        setStartX(screenStart.x);
        setStartY(screenStart.y);

        setEndX(screenEnd.x);
        setEndY(screenEnd.y);
    }
}
