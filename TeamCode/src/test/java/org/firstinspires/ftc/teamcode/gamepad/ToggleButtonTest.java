package org.firstinspires.ftc.teamcode.gamepad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Assignment 1: Test-driven development - or TDD - is a coding
 * technique where you first write a test that fails, and then
 * you write or change the application's code to make the test
 * pass.
 *
 * Here I've written code to test a class called ToggleButton.
 * The ToggleButton class has just enough code to allow the test
 * to compile and run, but it lacks real functionality so the
 * test fails.
 *
 * Your challenge:
 *   1. Run the test below and inspect the test output.
 *
 *   2. Open the ToggleButton class and read its comments to
 *      learn what it's supposed to do.
 *
 *   3. Modify ToggleButton and re-run the test until you make
 *      the test pass.
 *
 *   4. You may not change any code in this test. No cheating!
 */
class ToggleButtonTest {

    class FakeGamePad {
        boolean a = false;
    }

    /**
     * A good test should demonstrated how the tested code is
     * used. In this case, the code we're testing is ToggleButton,
     * which would be used in an iterative OpMode. So here we have
     * a fake OpMode that looks a bit like the real thing.
     *
     * SkottOp uses gamepad1.a to set a `testMode` variable to decide
     * which drive-train code to use.
     */
    class FakeDerpyOp {
        FakeGamePad gamepad1 = new FakeGamePad();

        // This is really how you would use ToggleButton in an OpMode.
        // Note the use of the lambda function `() -> gamepad1.a` to
        // read from the controller. Can you explain why a lambda here
        // is better than passing gamepad1 or gamepad1.a to the
        // ToggleButton constructor?
        ToggleButton toggleButtonA = new ToggleButton(() -> gamepad1.a);
        boolean testMode = false;

        public void loop() {
            testMode = toggleButtonA.getCurrent();
        }
    }

    /**
     * In this test, we simulate an iterative OpMode. We control the
     * gamepad as though we are the user. We invoke the OpMode's loop()
     * many times just as a real OpMode's loop() is called iteratively.
     */
    @Test
    void get() {
        final FakeDerpyOp derpyOp = new FakeDerpyOp();

        // user hasn't pushed the A button yet - testMode should
        // remain false through multiple getLast() iterations.
        derpyOp.loop();
        assertFalse(derpyOp.testMode);
        derpyOp.loop();
        assertFalse(derpyOp.testMode);
        derpyOp.loop();
        assertFalse(derpyOp.testMode);

        // pushing the button for the first time - testMode should flip to true
        derpyOp.gamepad1.a = true;
        derpyOp.loop();
        assertTrue(derpyOp.testMode); // <---- the test should fail here - that is, until you write ToggleButton's logic. Off you go. Best of luck. You got this.
        derpyOp.loop();
        assertTrue(derpyOp.testMode);
        derpyOp.loop();
        assertTrue(derpyOp.testMode);

        // release the button - testMode should remain true
        derpyOp.gamepad1.a = false;
        derpyOp.loop();
        assertTrue(derpyOp.testMode);
        derpyOp.loop();
        assertTrue(derpyOp.testMode);
        derpyOp.loop();
        assertTrue(derpyOp.testMode);

        // pushing the button a second time - testMode should flip to false
        derpyOp.gamepad1.a = true;
        derpyOp.loop();
        assertFalse(derpyOp.testMode);
        derpyOp.loop();
        assertFalse(derpyOp.testMode);
        derpyOp.loop();
        assertFalse(derpyOp.testMode);

        // release the button again - testMode should remain set to false
        derpyOp.gamepad1.a = false;
        derpyOp.loop();
        assertFalse(derpyOp.testMode);
        derpyOp.loop();
        assertFalse(derpyOp.testMode);
        derpyOp.loop();
        assertFalse(derpyOp.testMode);

        // pushing the button for the third time - toggle should flip again to true
        derpyOp.gamepad1.a = true;
        derpyOp.loop();
        assertTrue(derpyOp.testMode);
        derpyOp.loop();
        assertTrue(derpyOp.testMode);
        derpyOp.loop();
        assertTrue(derpyOp.testMode);

        // release the button again - toggle should remain be set to true
        derpyOp.gamepad1.a = false;
        derpyOp.loop();
        assertTrue(derpyOp.testMode);
        derpyOp.loop();
        assertTrue(derpyOp.testMode);
        derpyOp.loop();
        assertTrue(derpyOp.testMode);
    }
}
