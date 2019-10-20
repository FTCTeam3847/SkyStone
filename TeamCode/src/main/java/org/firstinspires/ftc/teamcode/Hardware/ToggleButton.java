package org.firstinspires.ftc.teamcode.Hardware;

import java.util.function.Supplier;

public class ToggleButton {
    private Supplier<Boolean> readButton = () -> false;
    private boolean lastButtonValue = readButton.get();
    private boolean currentValue = lastButtonValue;

    public void init(Supplier<Boolean> readButton) {
        this.readButton = readButton;
    }

    public boolean get() {
        boolean currentButtonValue = readButton.get();
        if (currentButtonValue != lastButtonValue) {
            lastButtonValue = currentButtonValue;
            currentValue = !currentValue;
        }
        return currentValue;
    }
}
