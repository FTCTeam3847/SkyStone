package org.firstinspires.ftc.teamcode.gamepad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Assignment 0: Run the JUnit tests in HelloWorldTest below,
 * and inspect the output. Fix the failing test to make it
 * succeed.
 *
 * To run the tests, right click on the class name ...
 *                       ↓↓
 *    public class HelloWorldTest {
 *
 * ... then select the menu option "Run 'HelloWorldTest".
 *
 *
 * The output from the failing test should read:
 *
 *     org.opentest4j.AssertionFailedError:
 *     Expected :Hello World!
 *     Actual   :hello world?
 *
 *
 * "Assertions" in JUnit are statements that can succeed or
 * fail. These tests use `assertEquals` to check if two values
 * are equal to one another. Assertions typically compare an
 * "expected" value to an "actual" value:
 *
 *   assertEquals(expected, actual)
 *   -or-
 *   assertEquals(2, 1+1)
 */
public class HelloWorldTest {
    // A JUnit test is simply a public void method without
    // parameters that is annotated with @Test.
    @Test
    public void succeedingHelloTest() {
        String expected = "Hello World!";
        String actual = "Hello" + " " + "World" + "!";
        assertEquals(expected, actual);
    }

    // A test fails when any assertion within test fails.
    @Test
    public void failingHelloTest() {
        String expected = "Hello World!";
        String actual = "Hello" + " " + "World" + "!";
        assertEquals(expected, actual);
    }
}
