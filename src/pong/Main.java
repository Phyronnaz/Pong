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

        LevelManager levelManager = new LevelManager(engine, new Level0(engine), new Level1(engine));

        Ball ball = new Ball(engine, levelManager);

        new CircleBallRender(engine, ball);

        Racket racketLeft = new AIRacket(engine, ball, RacketSide.LEFT, 0.5);
        Racket racketRight = new HumanRacket(engine, RacketSide.RIGHT, 2);

        new SimpleRacketRender(engine, racketLeft);
        new SimpleRacketRender(engine, racketRight);

        engine.start();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
