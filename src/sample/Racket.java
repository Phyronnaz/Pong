package sample;

import javafx.scene.shape.Rectangle;

public class Racket extends Rectangle
{
    public Racket(int width, boolean left)
    {
        super(Parameters.racket_width, Parameters.racket_height);

        if (left)
        {
            setTranslateX(Parameters.racket_width);
        }
        else
        {
            setTranslateX(width - 2 * Parameters.racket_width);
        }
    }

    public boolean isInside(double height)
    {
        return getTranslateY() < height && height < getTranslateY() + Parameters.racket_height;
    }
}
