package pong;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application
{
    private int width = 1000;
    private int height = 500;

    private Group root;

    @Override
    public void start(Stage stage) throws Exception
    {
        Engine engine = new Engine(stage, width, height, 50, 200, 10, 9);
        World world = new World(engine);

        Ball ball = new Ball(engine, world, new Vector2D(1, 1), new Vector2D(500, 250));
        new CircleBallRender(engine, ball);

        Segment segment0 = new Segment(new Vector2D(200, 200), new Vector2D(400, 400));
        world.addStaticCollision(segment0);
        new SegmentRender(engine, segment0);

        Segment segment1 = new Segment(new Vector2D(500, 200), new Vector2D(400, 100));
        world.addStaticCollision(segment1);
        new SegmentRender(engine, segment1);

        new AIRacket(engine, ball, RacketSide.LEFT, 10);
        new HumanRacket(engine, ball, RacketSide.RIGHT, 0.5);

        engine.start();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
