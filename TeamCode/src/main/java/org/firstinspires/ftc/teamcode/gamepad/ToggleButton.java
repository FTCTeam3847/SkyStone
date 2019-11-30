package org.firstinspires.ftc.teamcode.gamepad;

import org.firstinspires.ftc.teamcode.Sensor;

import java.util.function.Supplier;

/**
 * Keeps track of a toggle value in response to button presses on a
 * gamepad. It's expected that an OpMode will call {@link #getCurrent()}
 * once per `getLast()` to obtain the current value of the toggle.
 * <p>
 * The tricky part is that getLast() runs many times per second, and so the
 * button state is checked many times per second. It's difficult for a user
 * to tap and release a button in the time it takes for one getLast() to
 * complete. Because of this, a naive implementation that flips the toggle
 * value whenever the button state is pressed actually causes the toggle to
 * flip many times per physical button-press. The result to the end user is
 * a seemingly random toggle value after tapping the button.
 * <p>
 * This timeline depicts 27 iterations through an OpMode's `getLast()` calling
 * `getLast()`, and so the button is checked 27 times and is wither unpressed
 * (F) or pressed (T). The user has only pressed the button 5 times, and so
 * the toggle should only flip 5 times.
 * <p>
 * Assuming the starting toggle value is false (F), then the final toggle
 * value after 5 button presses should be true (T).
 * <p>
 * Iterations through getLast() - each column is one getLast()
 * Presses:        ↓       ↓         ↓         ↓       ↓
 * Button State:  F F F T T F F T T T F F T T F F F T T F F T T T F F F ...
 * Toggle Value:  F F F T T T T F F F F F T T T T T F F F F T T T T T T ...
 */
public class ToggleButton implements Sensor<Boolean> {
    private Supplier<Boolean> readButtonState;
    boolean buttonIsToggled = false;
    boolean previousState = false;

    /**
     * Advanced Question: Why do we use the Supplier type here? Why not
     * pass a Gamepad type instead?
     */
    public ToggleButton(Supplier<Boolean> readButtonState) {
        this.readButtonState = readButtonState;
    }

    /**
     * TODO: fix `getLast()` by implementing real logic.
     * <p>
     * Things to think about:
     * How do you know when to flip the toggle? What are the conditions?
     * What stateful variables do you need to track?
     * Where does a Java class store its stateful variables?
     */
    public Boolean getCurrent() {
        // Read the state of the button from the gamepad.
        boolean buttonStateNow = readButtonState.get();

        if (buttonStateNow && !previousState) {
            buttonIsToggled = !buttonIsToggled;
        }

        previousState = buttonStateNow;

        return buttonIsToggled;
        // We must return a value to satisfy the compiler so the tests can execute.
        // When you write the code, remember to modify this return statement.
    }
}
