package pong.levels;

import javafx.scene.Node;
import pong.core.Engine;
import pong.core.Level;
import pong.core.Vector2D;
import pong.core.World;

import java.util.Vector;

/**
 * Level1
 */
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

        double size = 50;
        double offSet = 100;


        LevelGenerator.createDiamond(world, renderObjects, engine, halfWidth - size, halfHeight - size - offSet, halfWidth + size, halfHeight + size - offSet);
        LevelGenerator.createDiamond(world, renderObjects, engine, halfWidth - size, halfHeight - size + offSet, halfWidth + size, halfHeight + size + offSet);


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
        return initialSpeed.rotate(Math.PI / 16);
    }
}
