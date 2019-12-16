package org.firstinspires.ftc.teamcode.gamepad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Here I've written code to test a class called PushButton.
 * The PushButton class has just enough code to allow the test
 * to compile and run, but it lacks real functionality so the
 * test fails.
 *
 * Your challenge:
 *   1. Run the test below and inspect the test output.
 *
 *   2. Open the PushButton class and read its comments to
 *      learn what it's supposed to do.
 *
 *   3. Modify PushButton and re-run the test until you make
 *      the test pass.
 *
 *   4. You may not change any code in this test. No cheating!
 */
class PushButtonTest {

    class FakeGamePad {
        boolean a = false;
    }

    /**
     * A good test should demonstrated how the tested code is
     * used. In this case, the code we're testing is PushButton,
     * which would be used in an iterative OpMode. So here we have
     * a fake OpMode that looks a bit like the real thing.
     *
     * An OpMode might use gamepad1.a to increment a `counter`
     * variable, or some other action only once per button press.
     */
    class FakeDerpyOp {
        FakeGamePad gamepad1 = new FakeGamePad();

        // This is really how you would use PushButton in an OpMode.
        // Note the use of the lambda function `() -> gamepad1.a` to
        // read from the controller. Can you explain why a lambda here
        // is better than passing gamepad1 or gamepad1.a to the
        // PushButton constructor?
        PushButton pushButtonA = new PushButton(() -> gamepad1.a);
        int counter = 0;

        public void loop() {
            if (pushButtonA.getCurrent()) counter++;
        }
    }

    /**
     * In this test, we simulate an iterative OpMode. We control the
     * gamepad as though we are the user. We invoke the OpMode's getCurrent()
     * many times just as a real OpMode's loop() would call getCurrent()
     * iteratively.
     */
    @Test
    void getCurrent() {
        final FakeDerpyOp derpyOp = new FakeDerpyOp();

        // user hasn't pushed the A button yet - counter should
        // remain 0 through multiple loop()s calling getCurrent().
        derpyOp.loop();
        assertEquals(0, derpyOp.counter);
        derpyOp.loop();
        assertEquals(0, derpyOp.counter);
        derpyOp.loop();
        assertEquals(0, derpyOp.counter);

        // pushing the button for the first time - counter should increment and remain at 1
        derpyOp.gamepad1.a = true;
        derpyOp.loop();
        assertEquals(1, derpyOp.counter); // <---- the test should fail here - that is, until you write PushButton's logic. Off you go. Best of luck. You got this.
        derpyOp.loop();
        assertEquals(1, derpyOp.counter);
        derpyOp.loop();
        assertEquals(1, derpyOp.counter);

        // release the button - counter should remain at 1
        derpyOp.gamepad1.a = false;
        derpyOp.loop();
        assertEquals(1, derpyOp.counter);
        derpyOp.loop();
        assertEquals(1, derpyOp.counter);
        derpyOp.loop();
        assertEquals(1, derpyOp.counter);

        // pushing the button a second time - counter should increment and remain at 2
        derpyOp.gamepad1.a = true;
        derpyOp.loop();
        assertEquals(2, derpyOp.counter);
        derpyOp.loop();
        assertEquals(2, derpyOp.counter);
        derpyOp.loop();
        assertEquals(2, derpyOp.counter);

        // release the button again - counter should remain at 2
        derpyOp.gamepad1.a = false;
        derpyOp.loop();
        assertEquals(2, derpyOp.counter);
        derpyOp.loop();
        assertEquals(2, derpyOp.counter);
        derpyOp.loop();
        assertEquals(2, derpyOp.counter);

        // pushing the button for the third time - counter should increment and remain at 3
        derpyOp.gamepad1.a = true;
        derpyOp.loop();
        assertEquals(3, derpyOp.counter);
        derpyOp.loop();
        assertEquals(3, derpyOp.counter);
        derpyOp.loop();
        assertEquals(3, derpyOp.counter);

        // release the button again - counter should remain at 3
        derpyOp.gamepad1.a = false;
        derpyOp.loop();
        assertEquals(3, derpyOp.counter);
        derpyOp.loop();
        assertEquals(3, derpyOp.counter);
        derpyOp.loop();
        assertEquals(3, derpyOp.counter);
    }
}