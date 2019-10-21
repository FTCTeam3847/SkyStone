package org.firstinspires.ftc.teamcode.gamepad;

import java.util.function.Supplier;

/**
 * Keeps track of a toggle value in response to button presses on a
 * gamepad. It's expected that an OpMode will call {@link #get()}
 * once per `loop()` to obtain the current value of the toggle.
 *
 * The tricky part is that loop() runs many times per second, and so the
 * button state is checked many times per second. It's difficult for a user
 * to tap and release a button in the time it takes for one loop() to
 * complete. Because of this, a naive implementation that flips the toggle
 * value whenever the button state is pressed actually causes the toggle to
 * flip many times per physical button-press. The result to the end user is
 * a seemingly random toggle value after tapping the button.
 *
 * This timeline depicts 27 iterations through an OpMode's `loop()` calling
 * `get()`, and so the button is checked 27 times and is wither unpressed
 * (U) or pressed (P). The user has only pressed the button 5 times, and so
 * the toggle should only flip 5 times.
 *
 * Assuming the starting toggle value is false (F), then the final toggle
 * value after 5 button presses should be true (T).
 *
 * Iterations through loop() - each column is one loop()
 *        Presses:        ↓       ↓         ↓         ↓       ↓
 *   Button State:  F F F T T F F T T T F F T T F F F T T F F T T T F F F ...
 *   Toggle Value:  F F F T T T T F F F F F T T T T T F F F F T T T T T T ...
 */
public class ToggleButton {
    private Supplier<Boolean> readButtonState;
    boolean buttonIsToggled = false;
    boolean wasSwitched = false;

    /**
     * Advanced Question: Why do we use the Supplier type here? Why not
     * pass a Gamepad type instead?
     */
    public ToggleButton(Supplier<Boolean> readButtonState) {
        this.readButtonState = readButtonState;
    }

    /**
     * TODO: fix `get()` by implementing real logic.
     *
     * Things to think about:
     *   How do you know when to flip the toggle? What are the conditions?
     *   What stateful variable do you need to track?
     *   Where does a Java class store its stateful variables?
     */
    public Boolean get() {
        // Read the state of the button from the gamepad.
        boolean buttonStateNow = readButtonState.get();

        if(buttonStateNow && !wasSwitched)
        {
            buttonIsToggled = !buttonIsToggled;
        }
        wasSwitched = !wasSwitched;
        return buttonIsToggled;
        // We must return a value to satisfy the compiler so the tests can execute.
        // When you write the code, remember to modify this return statement.
    }
}
