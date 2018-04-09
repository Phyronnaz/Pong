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

public class Main extends Application implements BallCallBack
{
    private Racket leftRacket;
    private Racket rightRacket;
    private Ball ball;
    private ImageView explosion;

    private int width = 1000;
    private int height = 500;

    private int xOffSet = 100;
    private int yOffSet = 50;

    private Group root;

    private void initGame()
    {
        ball = new Ball(width, height, 200, 200, 0.5, 0.5);
        ball.setBallCallBack(this);

        leftRacket = new HumanRacket(width, true);
        leftRacket.initTextFlow(xOffSet, yOffSet);

        rightRacket = new HumanRacket(width, false);
        rightRacket.initTextFlow(width - xOffSet, yOffSet);

        explosion = new ImageView();
        explosion.setImage(new Image(this.getClass().getResource("explosion.gif").toExternalForm()));
        explosion.setOpacity(0);

    }

    private void startGame()
    {
        ball.play();
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        initGame();

        //Creating a Group object
        Group root = new Group(ball, leftRacket, rightRacket, leftRacket.getTextFlow(), rightRacket.getTextFlow());

        //Creating a scene object
        Scene scene = new Scene(root, width, height);

        //Setting title to the Stage
        stage.setTitle("Pong");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();

        startGame();
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
            caught = leftRacket.isInside(height);
        }
        else
        {
            caught = rightRacket.isInside(height);
        }
        if (!caught)
        {
            System.out.print("\n");
            if (left)
            {
                leftRacket.incScore();
                leftRacket.setRacketText(leftRacket.getRacketScore());
                System.out.println("You lost !");
            }
            else
            {
                rightRacket.incScore();
                rightRacket.setRacketText(leftRacket.getRacketScore());
                System.out.println("You won!");
            }

            //System.out.print(leftRacket.getRacketScore());
            //System.out.print(" - ");
            //System.out.print(rightRacket.getRacketScore());
            //System.out.print("\n");

            // System.exit(0);

            // ball.stop();

            /*
            {
                explosion.setOpacity(1);
                explosion.setTranslateX(ball.getTranslateX() - explosion.getImage().getWidth() / 2);
                explosion.setTranslateY(ball.getTranslateY() - explosion.getImage().getHeight() / 2);

                Timeline timeline = new Timeline();

                KeyValue on = new KeyValue(explosion.opacityProperty(), 1);
                KeyValue off = new KeyValue(explosion.opacityProperty(), 0);

                Duration timeOn = timeline.getCurrentTime().add(Duration.millis(1500));
                Duration timeOff = timeline.getCurrentTime().add(Duration.millis(1501));

                KeyFrame keyframeOn = new KeyFrame(timeOn, on);
                KeyFrame keyframeOff = new KeyFrame(timeOff, off);

                timeline.getKeyFrames().add(keyframeOn);
                timeline.getKeyFrames().add(keyframeOff);

                timeline.play();
            }

            {
                Timeline timeline = new Timeline();

                KeyValue on = new KeyValue(ball.opacityProperty(), 1);
                KeyValue off = new KeyValue(ball.opacityProperty(), 0);

                Duration timeOn = timeline.getCurrentTime().add(Duration.millis(750));
                Duration timeOff = timeline.getCurrentTime().add(Duration.millis(751));

                KeyFrame keyframeOn = new KeyFrame(timeOn, on);
                KeyFrame keyframeOff = new KeyFrame(timeOff, off);

                timeline.getKeyFrames().add(keyframeOn);
                timeline.getKeyFrames().add(keyframeOff);

                timeline.play();
            }
            */
        }
    }
}
