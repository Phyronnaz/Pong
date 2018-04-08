package pong;

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
        int width = 1000;
        int height = 500;

        Ball ball = new Ball(width, height, 200, 200, 2, 2);
        ball.setBallCallBack(this);

        left_racket = new AIBracket(width, true, ball);
        right_racket = new AIBracket(width, false, ball);

        //Creating a Group object
        Group root = new Group(ball, left_racket, right_racket);

        //Creating a scene object
        Scene scene = new Scene(root, width, height);

        //Setting title to the Stage
        stage.setTitle("Pong");

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
        boolean caught;
        if (left)
        {
            caught = left_racket.isInside(height);
        }
        else
        {
            caught = right_racket.isInside(height);
        }
        if(caught)
            return;

        System.out.print("\n");
        if(left) {
            System.out.println("You lost !");
        }
        else {
            System.out.println("You won!");
        }
        System.exit(0);
    }
}
