package pong;

import javafx.scene.Node;

import java.util.Vector;

public class Level0 implements Level
{
    private final World world;
    private final Vector<Node> renderObjects = new Vector<>();
    private final Vector2D initialPosition;
    private final Vector2D initialSpeed;

    public Level0(Engine engine)
    {
        final double width = engine.getWorldWidth();
        final double height = engine.getWorldHeight();

        final double halfWidth = width / 2;
        final double halfHeight = height / 2;

        this.world = new World(width, height);

        Segment segment1_0 = new Segment(new Vector2D(halfWidth, (height * 1) / 12), new Vector2D(halfWidth, (height * 3) / 12));
        world.addStaticCollision(segment1_0);
        renderObjects.add(new SegmentRender(engine, segment1_0));

        Segment segment1_1 = new Segment(new Vector2D(halfWidth, (height * 5) / 12), new Vector2D(halfWidth, (height * 7) / 12));
        world.addStaticCollision(segment1_1);
        renderObjects.add(new SegmentRender(engine, segment1_1));

        Segment segment1_2 = new Segment(new Vector2D(halfWidth, (height * 9) / 12), new Vector2D(halfWidth, (height * 11) / 12));
        world.addStaticCollision(segment1_2);
        renderObjects.add(new SegmentRender(engine, segment1_2));

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
