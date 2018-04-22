package pong;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableNumberValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.naming.Binding;
import java.util.Vector;

public class Engine
{
    private final Stage stage;

    public double getWindowWidth()
    {
        return windowWidth;
    }

    public double getWindowHeight()
    {
        return windowHeight;
    }

    private final double windowWidth;
    private final double windowHeight;

    public double getRacketWidth()
    {
        return racketWidth;
    }

    private final double racketWidth;
    private final double racketHeight;

    public double getRacketDistanceToWall()
    {
        return racketDistanceToWall;
    }

    private final double racketDistanceToWall;
    private final double ballRadius;

    private final Group group;

    private final DoubleProperty leftRacketY;
    private final DoubleProperty rightRacketY;

    private final Vector<GameObject> gameObjects = new Vector<>();

    private final IntegerProperty leftScore;
    private final IntegerProperty rightScore;

    private final Text scoresText;

    private final Vector<EventHandler<? super KeyEvent>> onKeyPressedBinds = new Vector<>();
    private final Vector<EventHandler<? super KeyEvent>> onKeyReleasedBinds = new Vector<>();

    public World getWorld()
    {
        return world;
    }

    public void setWorld(World world)
    {
        this.world = world;
    }

    private World world;

    public Ball getBall()
    {
        return ball;
    }

    public void setBall(Ball ball)
    {
        this.ball = ball;
    }

    private Ball ball;

    public Engine(Stage stage, double windowWidth, double windowHeight, double racketWidth, double racketHeight, double racketDistanceToWall, double ballRadius)
    {
        this.stage = stage;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.racketWidth = racketWidth;
        this.racketHeight = racketHeight;
        this.racketDistanceToWall = racketDistanceToWall;
        this.ballRadius = ballRadius;

        this.group = new Group();

        this.leftRacketY = new SimpleDoubleProperty();
        this.rightRacketY = new SimpleDoubleProperty();

        this.leftScore = new SimpleIntegerProperty();
        this.leftScore.addListener(
                (observable, oldValue, newValue) ->
                {
                    updateScores();
                }
        );
        this.rightScore = new SimpleIntegerProperty();
        this.rightScore.addListener(
                (observable, oldValue, newValue) ->
                {
                    updateScores();
                }
        );

        this.scoresText = new Text((windowWidth / 2) - 18, 35, ""); // 18 is the empirical text width
        this.scoresText.setScaleX(5);
        this.scoresText.setScaleY(5);
        this.scoresText.setScaleZ(5);
        this.scoresText.textProperty().bind(Bindings.createStringBinding())
        addRenderObject(scoresText);
        updateScores();

        this.world = null;
    }

    public void updateScores()
    {
        scoresText.setText(leftScore.getValue() + "   -   " + rightScore.getValue());
    }

    public DoubleProperty getRacketYProperty(RacketSide side)
    {
        if (side == RacketSide.LEFT)
        {
            return leftRacketY;
        }
        else
        {
            return rightRacketY;
        }
    }

    public void addRenderObject(Node object)
    {
        group.getChildren().add(object);
    }

    public void addScriptObject(GameObject object)
    {
        gameObjects.add(object);
    }

    public void start()
    {
        Scene scene = new Scene(group, windowWidth, windowHeight);
        scene.setOnKeyPressed(this::actionOnKeyPressed);
        scene.setOnKeyReleased(this::actionOnKeyReleased);

        //Setting title to the Stage
        stage.setTitle("Pong");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();

        for (GameObject object : gameObjects)
        {
            object.start();
        }
    }

    public void addOnKeyPressed(EventHandler<? super KeyEvent> value)
    {
        onKeyPressedBinds.add(value);
    }

    public void addOnKeyReleased(EventHandler<? super KeyEvent> value)
    {
        onKeyReleasedBinds.add(value);
    }

    private void actionOnKeyPressed(KeyEvent event)
    {
        for (EventHandler<? super KeyEvent> eventHandler : onKeyPressedBinds)
        {
            eventHandler.handle(event);
        }
    }

    private void actionOnKeyReleased(KeyEvent event)
    {
        for (EventHandler<? super KeyEvent> eventHandler : onKeyReleasedBinds)
        {
            eventHandler.handle(event);
        }
    }

    public double getWorldWidth()
    {
        return windowWidth - 2 * (racketWidth + racketDistanceToWall);
    }

    public double getWorldHeight()
    {
        return windowHeight;
    }

    public void checkIfWon(Vector2D position)
    {
        if (position.x < ballRadius + 1 && !(leftRacketY.getValue() - racketHeight <= position.y && position.y <= leftRacketY.getValue()))
        {
            System.out.print("Right scores\n");
            rightScore.setValue(rightScore.getValue() + 1);
            reset();
        }
        else if (getWorldWidth() - ballRadius - 1 < position.x && !(rightRacketY.getValue() - racketHeight <= position.y && position.y <= rightRacketY.getValue()))
        {
            System.out.print("Left scores\n");
            leftScore.setValue(leftScore.getValue() + 1);
            reset();
        }
    }

    public Vector2D getScreenPosition(Vector2D position)
    {
        return new Vector2D(racketWidth + racketDistanceToWall + position.x, windowHeight - position.y);
    }

    public NumberBinding getScreenPositionXBinding(ObservableNumberValue x)
    {
        return Bindings.add(racketWidth + racketDistanceToWall, x);
    }

    public NumberBinding getScreenPositionYBinding(ObservableNumberValue y)
    {
        return Bindings.subtract(windowHeight, y);
    }

    public Vector2D getLocalPosition(Vector2D screenPosition)
    {
        return new Vector2D(screenPosition.x - racketWidth + racketDistanceToWall, windowHeight - screenPosition.y);
    }

    public double getScreenLength(double length)
    {
        return length;
    }

    public double getRacketHeight()
    {
        return racketHeight;
    }

    public void reset()
    {
        for (GameObject gameObject : gameObjects)
        {
            gameObject.nextLevel();
        }
        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
        }
    }

    public double getBallRadius()
    {
        return ballRadius;
    }
}
