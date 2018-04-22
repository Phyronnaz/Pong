package pong;

import javafx.scene.Node;

import java.util.Vector;

public interface Level
{
    World getWorld();

    Vector<Node> getRenderObjects();
}