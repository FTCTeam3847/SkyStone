package org.firstinspires.ftc.teamcode.gamepad;

import org.firstinspires.ftc.teamcode.Sensor;

import java.util.function.Supplier;


/**
 * Provides a single True value in response to button presses on a
 * gamepad. It's expected that an OpMode will call {@link #getCurrent()}
 * once per `loop()` to check if the button has been pressed.
 * <p>
 * The tricky part is that getCurrent() runs many times per second, and so
 * the button state is checked many times per second. It's difficult for a
 * user to tap and release a button in the time it takes for one
 * loop() to complete. Because of this, a naive implementation that
 * returns true whenever the button is pressed will actually return true
 * many times per physical button-press. The result to the end user is
 * whatever action is predicated on the button and should only execute once
 * will actually execute many times.
 * <p>
 * This timeline depicts 27 iterations through an OpMode's `loop()` calling
 * `getCurrent()`, and so the button is checked 27 times and is either
 * unpressed (F) or pressed (T). The user has only pressed the button 5
 * times, and so the `true` should only be returned 5 times.
 * <p>
 * Iterations through loop() - each column is one loop() calling getCurrent()
 * Presses:                  ↓       ↓         ↓         ↓       ↓
 * Real Button State:  F F F T T F F T T T F F T T F F F T T F F T T T F F F ...
 * PushButton Value:   F F F T F F F T F F F F T F F F F T F F F T F F F F F ...
 */
public class PushButton implements Sensor<Boolean> {
}
