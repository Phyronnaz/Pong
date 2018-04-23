package pong.levels;

import javafx.scene.Node;
import pong.core.Engine;
import pong.core.Level;
import pong.core.Vector2D;
import pong.core.World;

import java.util.Vector;

import static java.lang.Math.PI;

/**
 * Level0
 */
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

        LevelGenerator.createSegment(world, renderObjects, engine, halfWidth, (height * 0) / 12, halfWidth, (height * 2) / 12);
        LevelGenerator.createSegment(world, renderObjects, engine, halfWidth, (height * 5) / 12, halfWidth, (height * 7) / 12);
        LevelGenerator.createSegment(world, renderObjects, engine, halfWidth, (height * 10) / 12, halfWidth, (height * 12) / 12);

        initialPosition = new Vector2D(halfWidth - 100, halfHeight);
        initialSpeed = new Vector2D(1, 0.5);
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
        return initialSpeed.randomDirection(PI / 16);
    }
}
