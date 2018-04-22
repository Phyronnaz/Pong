package pong;

import javafx.scene.Node;

public abstract class GameObject
{
    public GameObject(Engine engine)
    {
        engine.addGameObject(this);
    }

    void start()
    {
    }

    void reset()
    {
    }

    void nextLevel()
    {
    }

    Node getRender()
    {
        return null;
    }
}
