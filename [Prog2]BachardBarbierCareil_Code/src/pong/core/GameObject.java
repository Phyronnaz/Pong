package pong.core;

import javafx.scene.Node;

/**
 * GameObject class
 */
public abstract class GameObject
{
    public GameObject(Engine engine)
    {
        engine.addGameObject(this);
    }

    /**
     * Called at the game start
     */
    public void start()
    {
    }

    /**
     * Called at the game end
     */
    public void stop()
    {
    }

    /**
     * Called when someone scores
     */
    public void reset()
    {
    }

    /**
     * Called when the level is switched
     */
    public void nextLevel()
    {
    }

    /**
     * If we need to render something
     *
     * @return Render object, can be null
     */
    public Node getRender()
    {
        return null;
    }
}
