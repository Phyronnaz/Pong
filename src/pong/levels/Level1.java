package pong.levels;

import javafx.scene.Node;
import pong.core.*;
import pong.render.SegmentRender;

import java.util.Vector;

public class Level1 implements Level
{
    private final World world;
    private final Vector<Node> renderObjects = new Vector<>();
    private final Vector2D initialPosition;
    private final Vector2D initialSpeed;

    public Level1(Engine engine)
    {
        final double width = engine.getWorldWidth();
        final double height = engine.getWorldHeight();

        final double halfWidth = width / 2;
        final double halfHeight = height / 2;

        this.world = new World(width, height);

        double offSet = 100;

        Segment segment2_0 = new Segment(new Vector2D(halfWidth - 50, halfHeight - offSet), new Vector2D(halfWidth, halfHeight - 50 - offSet));
        Segment segment2_1 = new Segment(new Vector2D(halfWidth, halfHeight - 50 - offSet), new Vector2D(halfWidth + 50, halfHeight - offSet));
        Segment segment2_2 = new Segment(new Vector2D(halfWidth + 50, halfHeight - offSet), new Vector2D(halfWidth, halfHeight + 50 - offSet));
        Segment segment2_3 = new Segment(new Vector2D(halfWidth, halfHeight + 50 - offSet), new Vector2D(halfWidth - 50, halfHeight - offSet));

        world.addStaticCollision(segment2_0);
        world.addStaticCollision(segment2_1);
        world.addStaticCollision(segment2_2);
        world.addStaticCollision(segment2_3);

        renderObjects.add(new SegmentRender(engine, segment2_0));
        renderObjects.add(new SegmentRender(engine, segment2_1));
        renderObjects.add(new SegmentRender(engine, segment2_2));
        renderObjects.add(new SegmentRender(engine, segment2_3));

        Segment segment2_4 = new Segment(new Vector2D(halfWidth - 50, halfHeight + offSet), new Vector2D(halfWidth, halfHeight - 50 + offSet));
        Segment segment2_5 = new Segment(new Vector2D(halfWidth, halfHeight - 50 + offSet), new Vector2D(halfWidth + 50, halfHeight + offSet));
        Segment segment2_6 = new Segment(new Vector2D(halfWidth + 50, halfHeight + offSet), new Vector2D(halfWidth, halfHeight + 50 + offSet));
        Segment segment2_7 = new Segment(new Vector2D(halfWidth, halfHeight + 50 + offSet), new Vector2D(halfWidth - 50, halfHeight + offSet));

        world.addStaticCollision(segment2_4);
        world.addStaticCollision(segment2_5);
        world.addStaticCollision(segment2_6);
        world.addStaticCollision(segment2_7);

        renderObjects.add(new SegmentRender(engine, segment2_4));
        renderObjects.add(new SegmentRender(engine, segment2_5));
        renderObjects.add(new SegmentRender(engine, segment2_6));
        renderObjects.add(new SegmentRender(engine, segment2_7));

        initialPosition = new Vector2D(halfWidth, halfHeight);
        initialSpeed = new Vector2D(1, 1);
    }

    @Override
    public World getWorld()
    {
        return world;
    }

    @Override
    public Vector<Node> getRenderObjects()
    {
        return renderObjects;
    }

    @Override
    public Vector2D getInitialPosition()
    {
        return initialPosition;
    }

    @Override
    public Vector2D getInitialSpeed()
    {
        return initialSpeed;
    }
}
