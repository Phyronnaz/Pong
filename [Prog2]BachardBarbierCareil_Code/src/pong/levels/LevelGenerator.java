package pong.levels;

import javafx.scene.Node;
import pong.core.*;
import pong.render.SegmentRender;

import java.util.Vector;

/**
 * Level helper
 */
public class LevelGenerator
{
    /**
     * Create a segment
     *
     * @param world         World
     * @param renderObjects Render objects
     * @param engine        Engine
     * @param x1            Start x
     * @param y1            Start y
     * @param x2            End x
     * @param y2            End y
     */
    public static void createSegment(World world, Vector<Node> renderObjects, Engine engine, double x1, double y1, double x2, double y2)
    {
        Segment seg = new Segment(new Vector2D(x1, y1), new Vector2D(x2, y2));
        world.addStaticCollision(seg);
        renderObjects.add(new SegmentRender(engine, seg));
    }

    /**
     * Create a rectangle
     *
     * @param world        World
     * @param renderObject Render objects
     * @param engine       Engine
     * @param x1           Start x
     * @param y1           Start y
     * @param x2           End x
     * @param y2           End y
     */
    public static void createRectangle(World world, Vector<Node> renderObject, Engine engine, double x1, double y1, double x2, double y2)
    {
        createSegment(world, renderObject, engine, x1, y1, x1, y2);
        createSegment(world, renderObject, engine, x1, y2, x2, y2);
        createSegment(world, renderObject, engine, x2, y2, x2, y1);
        createSegment(world, renderObject, engine, x2, y1, x1, y1);
    }

    /**
     * Create a diamond
     *
     * @param world        World
     * @param renderObject Render object
     * @param engine       Engine
     * @param x1           Start x
     * @param y1           Start y
     * @param x2           End x
     * @param y2           End y
     */
    public static void createDiamond(World world, Vector<Node> renderObject, Engine engine, double x1, double y1, double x2, double y2)
    {
        createSegment(world, renderObject, engine, x1, (y1 + y2) / 2, (x1 + x2) / 2, y2);
        createSegment(world, renderObject, engine, (x1 + x2) / 2, y2, x2, (y1 + y2) / 2);
        createSegment(world, renderObject, engine, x2, (y1 + y2) / 2, (x1 + x2) / 2, y1);
        createSegment(world, renderObject, engine, (x1 + x2) / 2, y1, x1, (y1 + y2) / 2);
    }
}
