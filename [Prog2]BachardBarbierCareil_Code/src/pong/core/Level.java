package pong.core;

import javafx.scene.Node;

import java.util.Vector;

/**
 * Simple level interface
 */
public interface Level
{
    /**
     * @return the world of this level
     */
    World getWorld();

    /**
     * @return the render objects of this level
     */
    Vector<Node> getRenderObjects();

    /**
     * @return the initial position (can be random)
     */
    Vector2D getInitialPosition();

    /**
     * @return the initial speed (can be random)
     */
    Vector2D getInitialSpeed();
}