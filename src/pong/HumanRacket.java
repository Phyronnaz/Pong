package pong;

import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class HumanRacket extends Racket
{

    boolean isLeft;

    public HumanRacket(int width, boolean left)
    {
        super(width, left);

        System.out.print(left);

        isLeft = left;

        ChangeListener<Scene> listener = (obs, old, newValue) -> newValue.setOnKeyPressed(this::actionOnKeyPressed);

        sceneProperty().addListener(listener);
    }

    private void actionOnKeyPressed(KeyEvent event)
    {
        System.out.print("\n");
        System.out.print(this.isLeft);
        if (this.isLeft)
        {
            switch (event.getCode())
            {
                case LEFT:
                    setTranslateY(getTranslateY() - Parameters.inputDelta);
                    break;
                case RIGHT:
                    setTranslateY(getTranslateY() + Parameters.inputDelta);
                    break;
            }
        }
        else {
            switch (event.getCode()) {
                case UP:
                setTranslateY(getTranslateY() - Parameters.inputDelta);
                break;
            case DOWN:
                setTranslateY(getTranslateY() + Parameters.inputDelta);
                break;
        }
    }
}
}

