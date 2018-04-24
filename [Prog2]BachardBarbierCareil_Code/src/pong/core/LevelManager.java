package pong.core;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.Vector;

/**
 * Manages levels
 */
public class LevelManager extends GameObject
{
    private final Engine engine;
    private final Vector<Level> levels = new Vector<>();
    private int currentLevel = 0;

    /**
     * Constructor
     *
     * @param engine Main engine
     * @param levels List of levels
     */
    public LevelManager(Engine engine, Level... levels)
    {
        super(engine);

        this.engine = engine;
        this.levels.addAll(Arrays.asList(levels));
    }

    @Override
    public void start()
    {
        engine.setWorld(levels.get(0).getWorld());
    }

    @Override
    public void nextLevel()
    {
        currentLevel++;
        try
        {
            engine.setWorld(levels.get(currentLevel).getWorld());
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            engine.setWorld(new World(1e9, 1e9));
            engine.stop();
        }
    }

    @Override
    public Node getRender()
    {
        try
        {
            return new Group(levels.get(currentLevel).getRenderObjects());
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            Text text = new Text(engine.getWindowWidth() / 2, engine.getWindowHeight() / 2, "VICTORY");
            text.setScaleX(5);
            text.setScaleY(5);
            text.setScaleZ(5);
            return text;
        }
    }

    /**
     * @return the initial position
     */
    public Vector2D getInitialPosition()
    {
        try
        {
            return levels.get(currentLevel).getInitialPosition();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return new Vector2D(engine.getWorldWidth() / 2, engine.getWorldHeight() / 2);
        }
    }

    /**
     * @return the initial speed
     */
    public Vector2D getInitialSpeed()
    {
        try
        {
            return levels.get(currentLevel).getInitialSpeed();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return new Vector2D(0, 0);
        }
    }
}
