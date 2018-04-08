package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import static java.lang.Double.min;

public class Ball extends Circle
{
    private Timeline timeline = new Timeline();
    private int width;
    private int height;

    private double speed_x;
    private double speed_y;

    private double pos_x;
    private double pos_y;

    private double radius;

    public Ball(int width, int height, double pos_x, double pos_y, double speed_x, double speed_y, double radius)
    {
        super(radius);

        this.width = width;
        this.height = height;

        this.pos_x = pos_x;
        this.pos_y = pos_y;

        this.speed_x = speed_x;
        this.speed_y = speed_y;

        this.radius = radius;

        setTranslateX(pos_x);
        setTranslateY(pos_y);

        timeline.setOnFinished(this::next);
    }

    public void play()
    {
        timeline.play();
    }

    private void next(ActionEvent e)
    {
        double dt;
        double new_speed_x;
        double new_speed_y;
        {
            double dt_x = (radius - pos_x) / speed_x;
            if (dt_x <= 0)
            {
                dt_x = (width - radius - pos_x) / speed_x;
            }

            double dt_y = (radius - pos_y) / speed_y;
            if (dt_y <= 0)
            {
                dt_y = (height - radius - pos_y) / speed_y;
            }

            if (dt_x < dt_y)
            {
                dt = dt_x;
                new_speed_x = -speed_x;
                new_speed_y = speed_y;
            }
            else
            {
                dt = dt_y;
                new_speed_x = speed_x;
                new_speed_y = -speed_y;
            }

        }

        System.out.print(dt);
        System.out.print("; Speed: (");
        System.out.print(speed_x);
        System.out.print(", ");
        System.out.print(speed_y);
        System.out.print("); Position: (");
        System.out.print(pos_x);
        System.out.print(", ");
        System.out.print(pos_y);
        System.out.print(")\n");
        pos_x += speed_x * dt;
        pos_y += speed_y * dt;

        speed_x = new_speed_x;
        speed_y = new_speed_y;

        // Add another action
        timeline.getKeyFrames().add(
                new KeyFrame(timeline.getCurrentTime().add(Duration.millis(dt)),
                        new KeyValue(translateXProperty(), pos_x),
                        new KeyValue(translateYProperty(), pos_y)));
        // Restart the timeline
        timeline.playFrom(timeline.getCurrentTime());
    }
}
