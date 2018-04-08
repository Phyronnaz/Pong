package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import static com.sun.javafx.util.Utils.clamp;
import static java.lang.Double.min;

interface BallCallBack
{
    void ballHit(boolean left, double height);
}

public class Ball extends Circle
{
    private Timeline timeline = new Timeline();
    private int width;
    private int height;

    private double speed_x;
    private double speed_y;

    private double pos_x;
    private double pos_y;

    private BallCallBack ballCallBack;

    public Ball(int width, int height, double pos_x, double pos_y, double speed_x, double speed_y)
    {
        super(Parameters.ballRadius);

        this.width = width;
        this.height = height;

        this.pos_x = pos_x;
        this.pos_y = pos_y;

        this.speed_x = speed_x;
        this.speed_y = speed_y;

        setTranslateX(pos_x);
        setTranslateY(pos_y);

        timeline.setOnFinished(this::next);
    }

    public void play()
    {
        timeline.play();
    }

    public void setBallCallBack(BallCallBack ballCallBack)
    {
        this.ballCallBack = ballCallBack;
    }

    private void next(ActionEvent e)
    {
        final double border_size = Parameters.ballRadius + 2 * Parameters.racket_width;


        if (pos_x <= border_size + 1)
        {
            ballCallBack.ballHit(true, getTranslateY());
        }
        else if (pos_x >= width - border_size - 1)
        {
            ballCallBack.ballHit(false, getTranslateY());
        }

        double dt_x = (border_size - pos_x) / speed_x;
        if (dt_x <= 0)
        {
            dt_x = (width - border_size - pos_x) / speed_x;
        }

        double dt_y = (Parameters.ballRadius - pos_y) / speed_y;
        if (dt_y <= 0)
        {
            dt_y = (height - Parameters.ballRadius - pos_y) / speed_y;
        }

        final double dt = min(dt_x, dt_y);
        if (dt < 0)
        {
            System.out.print("Error!");
        }

        pos_x += speed_x * dt;
        pos_y += speed_y * dt;

        if (dt_x < dt_y)
        {
            speed_x = -speed_x;
        }
        else if (dt_y < dt_x)
        {
            speed_y = -speed_y;
        }
        else
        {
            speed_x = -speed_x;
            speed_y = -speed_y;
        }

        pos_x = clamp(0, pos_x, width);
        pos_y = clamp(0, pos_y, height);

//        System.out.print(pos_x);
//        System.out.print("; ");
//        System.out.print(pos_y);
//        System.out.print("; ");
//        System.out.print(dt);
//        System.out.print("\n");

        KeyValue keyvalue_x = new KeyValue(translateXProperty(), pos_x);
        KeyValue keyvalue_y = new KeyValue(translateYProperty(), pos_y);

        Duration time = timeline.getCurrentTime().add(Duration.millis(dt));

        KeyFrame keyframe = new KeyFrame(time, keyvalue_x, keyvalue_y);

        timeline.getKeyFrames().add(keyframe);
        timeline.playFrom(timeline.getCurrentTime());
    }
}
