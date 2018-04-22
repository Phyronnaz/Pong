package pong.engine;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableNumberValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pong.Vector2D;

import javax.naming.Binding;
import java.util.Vector;

public class Engine
{
    private final Stage stage;

    private final double windowWidth;
    private final double windowHeight;

    private final double racketWidth;
    private final double racketHeight;

    private final double racketDistanceToWall;
    private final double ballRadius;

    private final DoubleProperty leftRacketY;
    private final DoubleProperty rightRacketY;

    private final Vector<GameObject> gameObjects = new Vector<>();

    private final IntegerProperty leftScore;
    private final IntegerProperty rightScore;

    private final Text scoresText;

    private final Vector<EventHandler<? super KeyEvent>> onKeyPressedBinds = new Vector<>();
    private final Vector<EventHandler<? super KeyEvent>> onKeyReleasedBinds = new Vector<>();

    private World world;

    public Engine(Stage stage, double windowWidth, double windowHeight, double racketWidth, double racketHeight, double racketDistanceToWall, double ballRadius)
    {
        this.stage = stage;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.racketWidth = racketWidth;
        this.racketHeight = racketHeight;
        this.racketDistanceToWall = racketDistanceToWall;
        this.ballRadius = ballRadius;

        this.leftRacketY = new SimpleDoubleProperty();
        this.rightRacketY = new SimpleDoubleProperty();

        this.leftScore = new SimpleIntegerProperty();
        this.rightScore = new SimpleIntegerProperty();

        ChangeListener<Number> listener = (obs, o, n) ->
        {
            if (n.intValue() == 5)
            {
                nextLevel();
            }
        };
        this.leftScore.addListener(listener);
        this.rightScore.addListener(listener);

        this.scoresText = new Text((windowWidth / 2) - 18, 35, ""); // 18 is the empirical text width
        this.scoresText.setScaleX(5);
        this.scoresText.setScaleY(5);
        this.scoresText.setScaleZ(5);
        this.scoresText.textProperty().bind(Bindings.concat(leftScore, "   -   ", rightScore));

        this.world = null;
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

    public void addGameObject(GameObject object)
    {
        gameObjects.add(object);
    }

    public void addOnKeyPressed(EventHandler<? super KeyEvent> value)
    {
        onKeyPressedBinds.add(value);
    }

    public void addOnKeyReleased(EventHandler<? super KeyEvent> value)
    {
        onKeyReleasedBinds.add(value);
    }

    public void start()
    {
        //Setting title to the Stage
        stage.setTitle("Pong");

        //Adding scene to the stage
        stage.setScene(createScene());

        //Displaying the contents of the stage
        stage.show();

        for (GameObject object : gameObjects)
        {
            object.start();
        }
    }

    private void nextLevel()
    {
        for (GameObject gameObject : gameObjects)
        {
            gameObject.nextLevel();
        }
        leftScore.setValue(0);
        rightScore.setValue(0);
    }

    private void reset()
    {

        for (GameObject gameObject : gameObjects)
        {
            gameObject.reset();
        }
        stage.setScene(createScene());
    }

    private Scene createScene()
    {
        Group group = new Group(scoresText);

        for (GameObject gameObject : gameObjects)
        {
            Node render = gameObject.getRender();
            if (render != null)
            {
                group.getChildren().add(render);
            }
        }

        Scene scene = new Scene(group, windowWidth, windowHeight);
        scene.setOnKeyPressed((KeyEvent event) ->
        {
            for (EventHandler<? super KeyEvent> eventHandler : onKeyPressedBinds)
            {
                eventHandler.handle(event);
            }
        });
        scene.setOnKeyReleased((KeyEvent event) ->
        {
            for (EventHandler<? super KeyEvent> eventHandler : onKeyReleasedBinds)
            {
                eventHandler.handle(event);
            }
        });

        return scene;
    }

    private void checkIfWon(double x, double y)
    {
        if (x < ballRadius + 1 && !(leftRacketY.getValue() - racketHeight <= y && y <= leftRacketY.getValue()))
        {
            System.out.print("Right scores\n");
            rightScore.setValue(rightScore.getValue() + 1);
            reset();
        }
        else if (getWorldWidth() - ballRadius - 1 < x && !(rightRacketY.getValue() - racketHeight <= y && y <= rightRacketY.getValue()))
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

    public double getScreenLength(double length)
    {
        return length;
    }

    public double getBallRadius()
    {
        return ballRadius;
    }

    public void setBall(Ball ball)
    {
        ChangeListener<Number> listener = (obs, o, n) -> checkIfWon(ball.getPositionX(), ball.getPositionY());

        ball.positionXProperty().addListener(listener);
        ball.positionYProperty().addListener(listener);
    }

    public World getWorld()
    {
        return world;
    }

    public void setWorld(World world)
    {
        this.world = world;
    }

    public double getRacketDistanceToWall()
    {
        return racketDistanceToWall;
    }

    public double getRacketWidth()
    {
        return racketWidth;
    }

    public double getRacketHeight()
    {
        return racketHeight;
    }

    public double getWindowWidth()
    {
        return windowWidth;
    }

    public double getWindowHeight()
    {
        return windowHeight;
    }

    public double getWorldWidth()
    {
        return windowWidth - 2 * (racketWidth + racketDistanceToWall);
    }

    public double getWorldHeight()
    {
        return windowHeight;
    }
}
