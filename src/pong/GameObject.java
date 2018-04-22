package pong;

import javafx.scene.Node;

public interface GameObject
{
    void start();
    void nextLevel();
    Node getRender();
}
