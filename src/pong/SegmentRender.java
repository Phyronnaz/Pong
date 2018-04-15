package pong;

import javafx.scene.shape.Line;

public class SegmentRender extends Line
{
    public SegmentRender(Engine engine, Segment segment)
    {
        super();

        engine.addRenderObject(this);

        Vector2D screenStart = engine.getScreenPosition(segment.getStart());
        Vector2D screenEnd = engine.getScreenPosition(segment.getEnd());

        setStartX(screenStart.x);
        setStartY(screenStart.y);

        setEndX(screenEnd.x);
        setEndY(screenEnd.y);
    }
}
