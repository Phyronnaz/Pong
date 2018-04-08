package sample;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        int width = 2000;
        int height = 1000;

        Ball ball = new Ball(width, height, 100, 100, 1, 10, 50);

        //Creating a Group object
        Group root = new Group(ball);

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
}
