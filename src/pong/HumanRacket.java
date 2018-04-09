package pong;

import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class HumanRacket extends Racket
{
    public HumanRacket(int width, boolean left)
    {
        super(width, left);

        ChangeListener<Scene> listener = (obs, old, newValue) -> newValue.setOnKeyPressed(this::actionOnKeyPressed);

        sceneProperty().addListener(listener);
    }

    private void actionOnKeyPressed(KeyEvent event)
    {
        switch (event.getCode())
        {
            case UP:
                setTranslateY(getTranslateY() - Parameters.inputDelta);
                break;
            case DOWN:
                setTranslateY(getTranslateY() + Parameters.inputDelta);
                break;
        }
    }
}
