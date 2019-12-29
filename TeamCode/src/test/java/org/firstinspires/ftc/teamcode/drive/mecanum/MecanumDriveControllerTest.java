package org.firstinspires.ftc.teamcode.drive.mecanum;

import org.firstinspires.ftc.teamcode.controller.HeadingController;
import org.firstinspires.ftc.teamcode.controller.HeadingControllerRadians;
import org.firstinspires.ftc.teamcode.drive.DrivePower;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;
import org.junit.jupiter.api.Test;

import static java.lang.Math.round;
import static org.hamcrest.MatcherAssert.assertThat;
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
    final HeadingControllerRadians FIXED_HEADING =
            new HeadingControllerRadians(() -> 0.0, 2.0d, 2.0d, 0.1d);
    {
        FIXED_HEADING.setTarget(0.0d);
    }

    final double LEFT = -1.0d;
    final double RIGHT = 1.0d;
    final double UP = 1.0d;
    final double DOWN = -1.0d;
    final double ZERO = 0.0d;
    final double FORWARD = 1.0d;
    final double BACKWARD = -1.0d;

    final double ERR = 0.0001;

    double round2(double d) {
        return round(d * 100.0d) / 100.0d;
    }

    DrivePower round2(DrivePower drivePower) {
        return new DrivePower(
                round2(drivePower.rightFront),
                round2(drivePower.rightBack),
                round2(drivePower.leftFront),
                round2(drivePower.leftBack)
        );
    }

    @Test
    void driveForwardFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower forward = new DrivePower(FORWARD, FORWARD, FORWARD, FORWARD);

        driveController.setTarget(ZERO, UP, ZERO);
        assertThat(
                "Forward should power all wheels forward at full power",
                round2(driveController.getControl()),
                is(equalTo(forward))
        );
    }

    @Test
    void driveBackwardFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower backward = new DrivePower(BACKWARD, BACKWARD, BACKWARD, BACKWARD);

        driveController.setTarget(ZERO, DOWN, ZERO);
        assertThat(
                "Backward should power all wheels backward at negative full power",
                round2(driveController.getControl()),
                is(equalTo(backward))
        );
    }

    @Test
    void strafeLeftFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower left = new DrivePower(FORWARD, BACKWARD, BACKWARD, FORWARD);

        driveController.setTarget(LEFT, ZERO, ZERO);
        assertThat(
                "Left should provide full power to RF and LB, and negative full power to LF and RB.",
                round2(driveController.getControl()),
                is(equalTo(left))
        );
    }

    @Test
    void strafeRightFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower right = new DrivePower(BACKWARD, FORWARD, FORWARD, BACKWARD);

        driveController.setTarget(RIGHT, ZERO, ZERO);
        assertThat(
                "Left should provide full power to LF and RB, and negative full power to RF and LB.",
                round2(driveController.getControl()),
                is(equalTo(right))
        );
    }

    @Test
    void turnLeftFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower turnLeft = new DrivePower(FORWARD, FORWARD, BACKWARD, BACKWARD);

        driveController.setTarget(ZERO, ZERO, LEFT);
        assertThat(
                "Turn left should provide full power to the right wheels, and negative full power to the left wheels.",
                round2(driveController.getControl()),
                is(equalTo(turnLeft))
        );
    }

    @Test
    void turnRightFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower turnRight = new DrivePower(BACKWARD, BACKWARD, FORWARD, FORWARD);

        driveController.setTarget(ZERO, ZERO, RIGHT);
        assertThat(
                "Turn left should provide full power to the left wheels, and negative full power to the right wheels.",
                round2(driveController.getControl()),
                is(equalTo(turnRight))
        );
    }

    @Test
    void strafeRightFullAndTurnRightFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower strafeRightAndTurnRight = new DrivePower(BACKWARD, ZERO, FORWARD, ZERO);

        driveController.setTarget(RIGHT, ZERO, RIGHT);
        assertThat(
                "...",
                round2(driveController.getControl()),
                is(equalTo(strafeRightAndTurnRight))
        );
    }

    @Test
    void strafeRightFullAndTurnLeftFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower strafeRightAndTurnRight = new DrivePower(ZERO, FORWARD, ZERO, BACKWARD);

        driveController.setTarget(RIGHT, ZERO, LEFT);
        assertThat(
                "...",
                round2(driveController.getControl()),
                is(equalTo(strafeRightAndTurnRight))
        );
    }

    @Test
    void strafeLeftFullAndTurnRightFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower strafeRightAndTurnRight = new DrivePower(ZERO, BACKWARD, ZERO, FORWARD);

        driveController.setTarget(LEFT, ZERO, RIGHT);
        assertThat(
                "...",
                round2(driveController.getControl()),
                is(equalTo(strafeRightAndTurnRight))
        );
    }

    @Test
    void strafeLeftFullAndTurnLeftFull() {
        MecanumDriveController driveController = new MecanumDriveController(FIXED_HEADING);
        DrivePower strafeRightAndTurnRight = new DrivePower(FORWARD, ZERO, BACKWARD, ZERO);

        driveController.setTarget(LEFT, ZERO, LEFT);
        assertThat(
                "...",
                round2(driveController.getControl()),
                is(equalTo(strafeRightAndTurnRight))
        );
    }

    @Test
    void strafeBackwardPolar() {
        DrivePower drivePower = MecanumDriveController.strafePower(new PolarCoord(1, Math.PI));
        assertThat(
                "..",
                round2(drivePower),
                is(equalTo(new DrivePower(BACKWARD, BACKWARD, BACKWARD, BACKWARD)))
        );
    }

    @Test
    void strafeForwardPolar() {
        DrivePower drivePower = MecanumDriveController.strafePower(new PolarCoord(1, 0));
        assertThat(
                "..",
                round2(drivePower),
                is(equalTo(new DrivePower(FORWARD, FORWARD, FORWARD, FORWARD)))
        );
    }

    @Test
    void strafeRightPolar() {
        DrivePower drivePower = MecanumDriveController.strafePower(new PolarCoord(1, 3*Math.PI / 2));
        assertThat(
                "..",
                round2(drivePower),
                is(equalTo(new DrivePower(BACKWARD, FORWARD, FORWARD, BACKWARD)))
        );
    }

    @Test
    void strafeLeftPolar() {
        DrivePower drivePower = MecanumDriveController.strafePower(new PolarCoord(1, Math.PI / 2));
        assertThat(
                "..",
                round2(drivePower),
                is(equalTo(new DrivePower(FORWARD, BACKWARD, BACKWARD, FORWARD)))
        );
    }
}