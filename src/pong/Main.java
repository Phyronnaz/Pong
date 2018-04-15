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
        Engine engine = new Engine(width, height, 0, 0);
        World world = new World(engine);

        Ball ball = new Ball(engine, world, 49, new Vector2D(-1, 1), new Vector2D(100, 100));
        CircleBallRender ballRender = new CircleBallRender(engine, ball);

        Segment segment0 = new Segment(new Vector2D(700, 300), new Vector2D(700, 500));
        Segment segment1 = new Segment(new Vector2D(200, 100), new Vector2D(200, 250));
        Segment segment2 = new Segment(new Vector2D(300, 100), new Vector2D(300, 300));
        Segment segment3 = new Segment(new Vector2D(400, 300), new Vector2D(400, 400));
        Segment segment4 = new Segment(new Vector2D(500, 100), new Vector2D(500, 200));

        world.addStaticCollision(segment0);
        world.addStaticCollision(segment1);
        world.addStaticCollision(segment2);
        world.addStaticCollision(segment3);
        world.addStaticCollision(segment4);

        SegmentRender segmentRender0 = new SegmentRender(engine, segment0);
        SegmentRender segmentRender1 = new SegmentRender(engine, segment1);
        SegmentRender segmentRender2 = new SegmentRender(engine, segment2);
        SegmentRender segmentRender3 = new SegmentRender(engine, segment3);
        SegmentRender segmentRender4 = new SegmentRender(engine, segment4);

        //Creating a Group object
        Group root = new Group(ballRender, segmentRender0, segmentRender1, segmentRender2, segmentRender3, segmentRender4);

        //Creating a scene object
        Scene scene = new Scene(root, width, height);

        //Setting title to the Stage
        stage.setTitle("Pong");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();

        ball.start();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
