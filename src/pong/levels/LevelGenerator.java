package pong.levels;

import pong.core.*;
import pong.render.SegmentRender;

import java.util.Vector;

public class LevelGenerator {

    public static void createSegment(World world, Vector renderObjects, Engine engine, double x1, double y1, double x2, double y2)
    {
        Segment seg = new Segment(new Vector2D(x1, y1), new Vector2D(x2, y2));
        world.addStaticCollision(seg);
        renderObjects.add(new SegmentRender(engine, seg));
    }

    public static void createRectangle(World world, Vector renderObject, Engine engine, double x1, double y1, double x2, double y2)
    {
        createSegment(world, renderObject, engine, x1, y1, x1, y2);
        createSegment(world, renderObject, engine, x1, y2, x2, y2);
        createSegment(world, renderObject, engine, x2, y2, x2, y1);
        createSegment(world, renderObject, engine, x2, y1, x1, y1);
    }

    public static void createDiamond(World world, Vector renderObject, Engine engine, double x1, double y1, double x2, double y2)
    {
        createSegment(world, renderObject, engine, x1, (y1 + y2) / 2, (x1 + x2) / 2, y2);
        createSegment(world, renderObject, engine, (x1 + x2) / 2, y2, x2, (y1 + y2) / 2);
        createSegment(world, renderObject, engine, x2, (y1 + y2) / 2, (x1 + x2) / 2, y1);
        createSegment(world, renderObject, engine, (x1 + x2) / 2, y1, x1, (y1 + y2) / 2);
    }

}
