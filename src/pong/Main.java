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
        Engine engine = new Engine(stage, width, height, 20, 200, 10, 19);
        World world = new World(engine);

        Ball ball = new Ball(engine, world, new Vector2D(1, 1), new Vector2D(width / 2, height / 2));
        new CircleBallRender(engine, ball);

        Level.createLevel(world, engine, 1);

        new AIRacket(engine, ball, RacketSide.LEFT, 0.5);
        new AIRacket(engine, ball, RacketSide.RIGHT, 2);

        engine.start();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
