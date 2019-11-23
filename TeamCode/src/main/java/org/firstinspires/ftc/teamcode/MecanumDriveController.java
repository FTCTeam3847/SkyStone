package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Hardware.AngularPController;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.firstinspires.ftc.teamcode.DrivePower.ZERO;
import static org.firstinspires.ftc.teamcode.PolarUtil.addRadians;
import static org.firstinspires.ftc.teamcode.PolarUtil.fromXY;
import static org.firstinspires.ftc.teamcode.PolarUtil.subtractRadians;

public class MecanumDriveController {
    private final AngularPController headingController;

    public MecanumDriveController(AngularPController headingController) {
        this.headingController = headingController;
    }

    private static double QTR_PI = PI / 4;
    private static double ROOT_2_OVER_2 = sin(QTR_PI);
    private double lastTurn;

    public double getCurrentAngle() {
        return headingController.getCurrent();
    }

    private static DrivePower turnPower(double turn) {
        return new DrivePower(-turn, -turn, turn, turn);
    }

     static DrivePower strafePower(PolarCoord strafe) {
        double rblf = strafe.radius * cos(addRadians(strafe.theta, QTR_PI)) / ROOT_2_OVER_2;
        double rflb = strafe.radius * cos(subtractRadians(strafe.theta, QTR_PI)) / ROOT_2_OVER_2;

        return new DrivePower(rflb, rblf, rblf, rflb);
    }

    public DrivePower update(double left_x, double left_y, double right_x) {
        PolarCoord xypolar = fromXY(left_x, left_y);
        PolarCoord strafe = new PolarCoord(xypolar.radius, subtractRadians(xypolar.theta, 2*QTR_PI));
        return update(strafe, right_x);
    }

    public DrivePower update(PolarCoord strafe, double turn) {
        double currentAngle = headingController.update();

        DrivePower drivePower;

        if (strafe.radius == 0.0d && turn == 0.0d) {
            // we're stopped
            headingController.setDesired(currentAngle);
            drivePower = ZERO;
        } else {
            // we're moving
            if (turn == 0.0d && lastTurn != 0.0d) {
                // the user just stopped turning, so hold this angle
                headingController.setDesired(currentAngle);
            }

            DrivePower strafePower = strafePower(strafe);
            DrivePower turnPower;
            if (turn == 0.0d) {
                // the user isn't asking to turn, so get a
                // correction value to hold our desired heading
                //turnPower = turnPower(headingController.getControlValue());
                turnPower = ZERO;
                //Turned off DriveController because of movement bug
            } else {
                turnPower = turnPower(turn);
            }

            drivePower = DrivePower.combine(strafePower, turnPower);
        }
        lastTurn = turn;
        return drivePower;
    }
}