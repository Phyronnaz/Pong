package pong;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage stage)
    {
        int width = 1000;
        int height = 500;
        Engine engine = new Engine(stage, width, height, 20, 200, 10, 49);
        World world = new World(engine);

        Ball ball = new Ball(engine, world, new Vector2D(-0.5, -0.5), new Vector2D(500, 250));
        new CircleBallRender(engine, ball);

        Segment segment0 = new Segment(new Vector2D(200, 200), new Vector2D(200, 400));
        world.addStaticCollision(segment0);
        new SegmentRender(engine, segment0);

        Segment segment1 = new Segment(new Vector2D(400, 200), new Vector2D(400, 400));
        world.addStaticCollision(segment1);
        new SegmentRender(engine, segment1);

        new AIRacket(engine, ball, RacketSide.LEFT, 0.5);
        new HumanRacket(engine, ball, RacketSide.RIGHT, 2);

        engine.start();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
