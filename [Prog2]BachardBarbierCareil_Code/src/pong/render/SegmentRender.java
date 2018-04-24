package pong.render;

import javafx.scene.shape.Line;
import pong.levels.Segment;
import pong.core.Engine;
import pong.core.Vector2D;

/**
 * Basic black segment render
 */
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
