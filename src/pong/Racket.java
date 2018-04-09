package pong;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;



public class Racket extends Rectangle
{

    private int racketScore = 0;
    private TextFlow racketTextFlow;

    public void setRacketText(int score) {
        this.racketText.setText(String.valueOf(score));
    }

    private Text racketText;

    public Racket(int width, boolean left)
    {
        super(Parameters.racket_width, Parameters.racket_height);

        this.racketText = new Text("0");


        if (left)
        {
            setTranslateX(Parameters.racket_width);
        }
        else
        {
            setTranslateX(width - 2 * Parameters.racket_width);
        }
    }

    public void initTextFlow(double x, double y)
    {
        this.racketTextFlow = new TextFlow(this.racketText);
        this.racketTextFlow.setLayoutX(x);
        this.racketTextFlow.setLayoutY(y);
    }

    public TextFlow getTextFlow()
    {
        return this.racketTextFlow;
    }

    public Text getRacketText()
    {
        return this.racketText;
    }

    public int getRacketScore()
    {
        return racketScore;
    }

    public void incScore()
    {
        racketScore += 1;
    }

    public boolean isInside(double height)
    {
        return getTranslateY() < height && height < getTranslateY() + Parameters.racket_height;
    }
}
