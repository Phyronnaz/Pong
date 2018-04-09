package pong;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import static com.sun.javafx.util.Utils.clamp;
import static java.lang.Double.min;
import static java.lang.Math.atan2;
import static java.lang.Math.toDegrees;

interface BallCallBack
{
    void ballHit(boolean left, double height);
}

public class Ball extends ImageView
{
    private Timeline timeline = new Timeline();
    private int width;
    private int height;

    private double speed_x;
    private double speed_y;

    private double pos_x;
    private double pos_y;

    private boolean stop = false;

    private double offset_x = 0;
    private double offset_y = 0;

    private BallCallBack ballCallBack;

    public double getPosY()
    {
        return getTranslateY() - offset_y;
    }

    public Ball(int width, int height, double pos_x, double pos_y, double speed_x, double speed_y)
    {
//        super(Parameters.ballRadius);

        setImage(new Image(this.getClass().getResource("car.png").toExternalForm()));

        setScaleX(0.1);
        setScaleY(0.1);

        offset_x = -getImage().getWidth() / 2;
        offset_y = -getImage().getHeight() / 2;


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

    public void stop()
    {
        stop = true;
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
            ballCallBack.ballHit(true, getPosY());
        }
        else if (pos_x >= width - border_size - 1)
        {
            ballCallBack.ballHit(false, getPosY());
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

        if (!stop)
        {
            KeyValue keyvalue_x = new KeyValue(translateXProperty(), pos_x + offset_x);
            KeyValue keyvalue_y = new KeyValue(translateYProperty(), pos_y + offset_y);

            setRotate(toDegrees(atan2(speed_y, speed_x)) + 90);

            KeyValue keyvalue_rotation = new KeyValue(rotateProperty(), toDegrees(atan2(speed_x, speed_y)));

            Duration time = timeline.getCurrentTime().add(Duration.millis(dt));

            KeyFrame keyframe = new KeyFrame(time, keyvalue_x, keyvalue_y);

            timeline.getKeyFrames().add(keyframe);
            timeline.playFrom(timeline.getCurrentTime());
        }
    }
}
