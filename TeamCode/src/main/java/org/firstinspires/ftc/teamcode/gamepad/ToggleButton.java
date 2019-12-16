package org.firstinspires.ftc.teamcode.gamepad;

import org.firstinspires.ftc.teamcode.Sensor;

import java.util.function.Supplier;

/**
 * Keeps track of a toggle value in response to button presses on a
 * gamepad. It's expected that an OpMode will call {@link #getCurrent()}
 * once per `loop()` to obtain the current value of the toggle.
 * <p>
 * The tricky part is that getCurrent() runs many times per second, and so
 * the button state is checked many times per second. It's difficult for a
 * user to tap and release a button in the time it takes for one
 * getCurrent() to complete. Because of this, a naive implementation that
 * flips the toggle value whenever the button state is pressed actually
 * causes the toggle to flip many times per physical button-press. The
 * result to the end user is a seemingly random toggle value after tapping
 * the button.
 * <p>
 * This timeline depicts 27 iterations through an OpMode's `loop()` calling
 * `getCurrent()`, and so the button is checked 27 times and is either
 * unpressed (F) or pressed (T). The user has only pressed the button 5
 * times, and so the toggle should only flip 5 times.
 * <p>
 * Assuming the starting toggle value is false (F), then the final toggle
 * value after 5 button presses should be true (T).
 * <p>
 * Iterations through getCurrent() - each column is one getCurrent()
 * Presses:             ↓       ↓         ↓         ↓       ↓
 * Button State:  F F F T T F F T T T F F T T F F F T T F F T T T F F F ...
 * Toggle Value:  F F F T T T T F F F F F T T T T T F F F F T T T T T T ...
 */
public class ToggleButton implements Sensor<Boolean> {
    private Supplier<Boolean> readButtonState;
    private boolean previousButtonState = false;
    private boolean toggleValue = false;

    /**
     * Advanced Question: Why do we use the Supplier type here? Why not
     * pass a Gamepad type instead?
     */
    public ToggleButton(Supplier<Boolean> readButtonState) {
        this.readButtonState = readButtonState;
    }

    /**
     * Things to think about:
     * How do you know when to flip the toggle? What are the conditions?
     * What stateful variables do you need to track?
     * Where does a Java class store its stateful variables?
     */
    public Boolean getCurrent() {
        // Read the state of the button from the gamepad.
        boolean buttonStateNow = readButtonState.get();

        if (buttonStateNow && !previousButtonState) {
            toggleValue = !toggleValue;
        }

        previousButtonState = buttonStateNow;

        return toggleValue;
    }
}
