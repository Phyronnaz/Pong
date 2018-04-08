package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application implements BallCallBack
{
    private Racket left_racket;
    private Racket right_racket;

    @Override
    public void start(Stage stage) throws Exception
    {
        int width = 2000;
        int height = 1000;

        Ball ball = new Ball(width, height, 200, 200, 1, 1);
        ball.setBallCallBack(this);

        left_racket = new HumanRacket(width, true);
        right_racket = new AIBracket(width, false, ball);

        //Creating a Group object
        Group root = new Group(ball, left_racket, right_racket);

        //Creating a scene object
        Scene scene = new Scene(root, width, height);

        //Setting title to the Stage
        stage.setTitle("Drawing a cylinder");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();

        ball.play();
    }


    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void ballHit(boolean left, double height)
    {
        System.out.print("Left: ");
        System.out.print(left);
        System.out.print("; Height:");
        System.out.print(height);
        System.out.print("\n");

        boolean catched;
        if (left)
        {
            catched = left_racket.isInside(height);
        }
        else
        {
            catched = right_racket.isInside(height);
        }
        System.out.print("\n");
        System.out.print(catched);
        System.out.print("\n");
    }
}
