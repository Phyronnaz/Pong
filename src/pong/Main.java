package pong;

import javafx.application.Application;
import javafx.stage.Stage;
import pong.engine.*;
import pong.world.Level;
import pong.world.World;

public class Main extends Application
{
    @Override
    public void start(Stage stage)
    {
        int ScreenWidth = 1000;
        int ScreenHeight = 500;
        Engine engine = new Engine(stage, ScreenWidth, ScreenHeight, 20, 200, 10, 19);
        World world = new World(engine);

        double gameWidth = engine.getWorldWidth();
        double gameHeight = engine.getWorldHeight();

        Ball ball = new Ball(engine, world, new Vector2D(0.5, 0.5), new Vector2D(gameWidth / 2, gameHeight / 2));
        new CircleBallRender(engine, ball);

        Level.createLevel(world, engine, 2);

        //new AIRacket(engine, ball, RacketSide.LEFT, 0.5);
        new HumanRacket(engine, ball, RacketSide.LEFT, 0.5);
        new AIRacket(engine, ball, RacketSide.RIGHT, 0.5);

        engine.start();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
