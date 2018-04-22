package pong.core;

import javafx.scene.Node;

public abstract class GameObject
{
    public GameObject(Engine engine)
    {
        engine.addGameObject(this);
    }

    public void start()
    {
    }

    public void reset()
    {
    }

    public void nextLevel()
    {
    }

    public void stop()
    {
    }

    public Node getRender()
    {
        return null;
    }
}
