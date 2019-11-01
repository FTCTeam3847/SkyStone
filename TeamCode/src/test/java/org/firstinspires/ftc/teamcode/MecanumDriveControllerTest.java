package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Hardware.AngularPController;
import org.junit.jupiter.api.Test;

import static java.lang.Math.round;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Assignment 2: "Example based" testing is a technique where
 * you create a test with know, hard-coded inputs and assert
 * for specific known outputs. This form of testing is simple
 * to write and understand, however will only test the specific
 * conditions, or examples, that the developer thinks of.
 *
 * Here I've written a test around the MecanumDriveController.
 * As it turns out, the code has a bug! Oh noes!
 *
 * Your challenge:
 *   1. Run the test below and inspect the test output.
 *
 *   2. See if you can fix the MecanumDriveController
 *      to provide full power to the wheels during
 *      basic maneuvers.
 *
 *   3. You may not change any code in this test. No cheating!
 *
 *   4. Advanced: add your own test class that tests the power
 *      output when strafing and turning at the same time.
 */
class MecanumDriveControllerTest {
    final AngularPController FIXED_HEADING =
            new AngularPController(() -> 0.0, 2.0d, 2.0d, 0.1d);

    final double LEFT = -1.0d;
    final double RIGHT = 1.0d;
    final double UP = -1.0d;
    final double DOWN = 1.0d;
    final double ZERO = 0.0d;
    final double FORWARD = 1.0d;
    final double BACKWARD = -1.0d;

    final double ERR = 0.0001;

    double round2(double d) {
        return round(d * 100.0d) / 100.0d;
    }

    DrivePower round2(DrivePower drivePower) {
        return new DrivePower(
                round2(drivePower.rightFor),
                round2(drivePower.rightBack),
                round2(drivePower.leftFor),
                round2(drivePower.leftBack)
        );
    }

    @Test
    void driveForwardFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower forward = new DrivePower(FORWARD, FORWARD, FORWARD, FORWARD);

        assertThat(
                "Forward should power all wheels forward at full power",
                round2(driveController.update(ZERO, UP, ZERO)),
                is(equalTo(forward))
        );
    }

    @Test
    void driveBackwardFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower backward = new DrivePower(BACKWARD, BACKWARD, BACKWARD, BACKWARD);

        assertThat(
                "Backward should power all wheels backward at negative full power",
                round2(driveController.update(ZERO, DOWN, ZERO)),
                is(equalTo(backward))
        );
    }

    @Test
    void strafeLeftFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower left = new DrivePower(FORWARD, BACKWARD, BACKWARD, FORWARD);

        assertThat(
                "Left should provide full power to RF and LB, and negative full power to LF and RB.",
                round2(driveController.update(LEFT, ZERO, ZERO)),
                is(equalTo(left))
        );
    }

    @Test
    void strafeRightFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower right = new DrivePower(BACKWARD, FORWARD, FORWARD, BACKWARD);

        assertThat(
                "Left should provide full power to LF and RB, and negative full power to RF and LB.",
                round2(driveController.update(RIGHT, ZERO, ZERO)),
                is(equalTo(right))
        );
    }

    @Test
    void turnLeftFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower turnLeft = new DrivePower(FORWARD, FORWARD, BACKWARD, BACKWARD);

        assertThat(
                "Turn left should provide full power to the right wheels, and negative full power to the left wheels.",
                round2(driveController.update(ZERO, ZERO, LEFT)),
                is(equalTo(turnLeft))
        );
    }

    @Test
    void turnRightFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower turnRight = new DrivePower(BACKWARD, BACKWARD, FORWARD, FORWARD);

        assertThat(
                "Turn left should provide full power to the left wheels, and negative full power to the right wheels.",
                round2(driveController.update(ZERO, ZERO, RIGHT)),
                is(equalTo(turnRight))
        );
    }

    @Test
    void strafeRightFullAndTurnRightFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower strafeRightAndTurnRight = new DrivePower(BACKWARD, ZERO, FORWARD, ZERO);

        assertThat(
                "...",
                round2(driveController.update(RIGHT, ZERO, RIGHT)),
                is(equalTo(strafeRightAndTurnRight))
        );
    }

    @Test
    void strafeRightFullAndTurnLeftFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower strafeRightAndTurnRight = new DrivePower(ZERO, FORWARD, ZERO, BACKWARD);

        assertThat(
                "...",
                round2(driveController.update(RIGHT, ZERO, LEFT)),
                is(equalTo(strafeRightAndTurnRight))
        );
    }

    @Test
    void strafeLeftFullAndTurnRightFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower strafeRightAndTurnRight = new DrivePower(ZERO, BACKWARD, ZERO, FORWARD);

        assertThat(
                "...",
                round2(driveController.update(LEFT, ZERO, RIGHT)),
                is(equalTo(strafeRightAndTurnRight))
        );
    }

    @Test
    void strafeLeftFullAndTurnLeftFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower strafeRightAndTurnRight = new DrivePower(FORWARD, ZERO, BACKWARD, ZERO);

        assertThat(
                "...",
                round2(driveController.update(LEFT, ZERO, LEFT)),
                is(equalTo(strafeRightAndTurnRight))
        );
    }
}