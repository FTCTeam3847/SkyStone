package org.firstinspires.ftc.teamcode.gamepad;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class OptionsButton<T> {
    private final PushButton pushButton;
    private final T[] options;
    private int index = 0;

    public OptionsButton(Supplier<Boolean> btnRead, T... options) {
        this.pushButton = new PushButton(btnRead);
        this.options = options;
    }

    public void apply(Consumer<T> consumer) {
        if (null == options) return;

        if (pushButton.getCurrent()) {
            index = (index + 1) % options.length;
            consumer.accept(options[index]);
        }
    }
}
