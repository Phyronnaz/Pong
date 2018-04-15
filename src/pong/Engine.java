package pong;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

    private final Group group;

    private final Rectangle leftRacket;
    private final Rectangle rightRacket;

    private final DoubleProperty leftRacketYProperty;
    private final DoubleProperty rightRacketYProperty;

    private final Vector<ScriptObject> scriptObjects = new Vector<>();

    private final IntegerProperty leftScore;
    private final IntegerProperty rightScore;

    private final Text scoresText;

    private final Vector<EventHandler<? super KeyEvent>> onKeyPressedBinds = new Vector<>();
    private final Vector<EventHandler<? super KeyEvent>> onKeyReleasedBinds = new Vector<>();

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

        this.leftRacket = new Rectangle(racketWidth, racketHeight);
        this.leftRacket.setTranslateX(racketDistanceToWall);
        addRenderObject(leftRacket);

        this.rightRacket = new Rectangle(racketWidth, racketHeight);
        this.rightRacket.setTranslateX(windowWidth - racketDistanceToWall - racketWidth);
        addRenderObject(rightRacket);

        this.leftRacketYProperty = new SimpleDoubleProperty();
        this.leftRacketYProperty.addListener(
                (observable, oldValue, newValue) ->
                {
                    leftRacket.setTranslateY(getScreenPosition(new Vector2D(0, newValue.doubleValue())).y);
                }
        );
        this.rightRacketYProperty = new SimpleDoubleProperty();
        this.rightRacketYProperty.addListener(
                (observable, oldValue, newValue) ->
                {
                    rightRacket.setTranslateY(getScreenPosition(new Vector2D(0, newValue.doubleValue())).y);
                }
        );

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

        this.scoresText = new Text(windowWidth / 2, 25, "");
        this.scoresText.setScaleX(5);
        this.scoresText.setScaleY(5);
        this.scoresText.setScaleZ(5);
        addRenderObject(scoresText);
        updateScores();
    }

    public void updateScores()
    {
        scoresText.setText(leftScore.getValue() + " | " + rightScore.getValue());
    }

    public DoubleProperty getRacketYProperty(RacketSide side)
    {
        if (side == RacketSide.LEFT)
        {
            return leftRacketYProperty;
        }
        else
        {
            return rightRacketYProperty;
        }
    }

    public void addRenderObject(Node object)
    {
        group.getChildren().add(object);
    }

    public void addScriptObject(ScriptObject object)
    {
        scriptObjects.add(object);
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

        for (ScriptObject object : scriptObjects)
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
        if (position.x < ballRadius + 1 && !(leftRacketYProperty.getValue() - racketHeight <= position.y && position.y <= leftRacketYProperty.getValue()))
        {
            System.out.print("Right scores\n");
            rightScore.setValue(rightScore.getValue() + 1);
            reset();
        }
        else if (getWorldWidth() - ballRadius - 1 < position.x && !(rightRacketYProperty.getValue() - racketHeight <= position.y && position.y <= rightRacketYProperty.getValue()))
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
        for (ScriptObject scriptObject : scriptObjects)
        {
            scriptObject.reset();
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
