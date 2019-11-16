package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Hardware.AngularPController;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.signum;
import static java.lang.Math.sin;
import static org.firstinspires.ftc.teamcode.DrivePower.ZERO;
import static org.firstinspires.ftc.teamcode.PolarUtil.addRadians;
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

    private static double powSign(double base, double exp) {
        return signum(base) * pow(abs(base), exp);
    }

    private static DrivePower turnPower(double turn, int sensitivity) {
        double scaled = powSign(turn, sensitivity);
        return new DrivePower(-scaled, -scaled, scaled, scaled);
    }

    private static DrivePower strafePower(PolarCoord strafe, int sensitivity) {
        double magnitude = powSign(strafe.radius, sensitivity);

        double rblf = magnitude * sin(addRadians(strafe.theta, QTR_PI)) / ROOT_2_OVER_2;
        double rflb = magnitude * sin(subtractRadians(strafe.theta, QTR_PI)) / ROOT_2_OVER_2;

        return new DrivePower(rflb, rblf, rblf, rflb);
    }


    public DrivePower update(double left_x, double left_y, double right_x) {
        PolarCoord strafe = PolarCoord.fromXY(left_x, left_y);
        int sensitivity = 3;
        return update(strafe, right_x, sensitivity);
    }

    public DrivePower update(PolarCoord strafe, double turn, int sensitivity) {
        double currentAngle = headingController.update();

        DrivePower drivePower;

        if (strafe.radius == 0.0d && turn == 0.0d) {
            // we're stopped
            headingController.setDesired(currentAngle);
            drivePower = ZERO;
        } else {
            // we're moving
            if (turn == 0.0d && lastTurn != 0) {
                // the user just stopped turning, so hold this angle
                headingController.setDesired(currentAngle);
            }

            DrivePower strafePower = strafePower(strafe, sensitivity);
            DrivePower turnPower;
            if (turn == 0.0d) {
                // the user isn't asking to turn, so get a
                // correction value to hold our desired heading
                turnPower = turnPower(headingController.getControlValue(), 1);
            } else {
                turnPower = turnPower(turn, sensitivity);
            }

            drivePower = DrivePower.combine(strafePower, turnPower);
        }
        lastTurn = turn;
        return drivePower;
    }
}