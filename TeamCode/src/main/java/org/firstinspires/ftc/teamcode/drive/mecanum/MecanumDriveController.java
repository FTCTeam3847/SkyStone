package org.firstinspires.ftc.teamcode.drive.mecanum;

import org.firstinspires.ftc.teamcode.controller.HeadingController;
import org.firstinspires.ftc.teamcode.drive.DrivePower;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;

import java.util.function.Consumer;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower.mecanumPower;
import static org.firstinspires.ftc.teamcode.polar.PolarCoord.polar;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.addRadians;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.fromXY;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtractRadians;

public class MecanumDriveController implements MecanumDrive {
    private final HeadingController headingController;
    private final Consumer<DrivePower> drive;

    public MecanumDriveController(HeadingController headingController, Consumer<DrivePower> drive) {
        this.headingController = headingController;
        this.drive = drive;
    }

    private static double QTR_PI = PI / 4;
    private static double ROOT_2_OVER_2 = sin(QTR_PI);
    private double lastTurn;

    public void setPower(MecanumPower target) {
        DrivePower drivePower = calculateDrivePower(target);
        drive.accept(drivePower);
    }

    public void setPower(double strafe_x, double strafe_y, double turn) {
        PolarCoord xypolar = fromXY(strafe_x, strafe_y);
        PolarCoord strafe = polar(xypolar.radius, subtractRadians(xypolar.theta, 2 * QTR_PI));
        setPower(mecanumPower(strafe, turn));
    }

    private static DrivePower turnPower(double turn) {
        if (Double.isFinite(turn)) {
            return new DrivePower(-turn, -turn, turn, turn);
        } else {
            return DrivePower.ZERO;
        }
    }

    static DrivePower strafePower(PolarCoord strafe) {
        double rblf = strafe.radius * cos(addRadians(strafe.theta, QTR_PI)) / ROOT_2_OVER_2;
        double rflb = strafe.radius * cos(subtractRadians(strafe.theta, QTR_PI)) / ROOT_2_OVER_2;

        return new DrivePower(rflb, rblf, rblf, rflb);
    }

    private DrivePower calculateDrivePower(MecanumPower mecanumPower) {
        double currentAngle = headingController.getCurrent();

        DrivePower drivePower;

        if (mecanumPower.strafe.radius == 0.0d && mecanumPower.turn == 0.0d) {
            // we're stopped
            headingController.setTarget(currentAngle);
            drivePower = DrivePower.ZERO;
        } else {
            // we're moving
            if (mecanumPower.turn == 0.0d && lastTurn != 0.0d) {
                // the user just stopped turning, so hold this angle
                headingController.setTarget(currentAngle);
            }

            DrivePower strafePower = strafePower(mecanumPower.strafe);
            DrivePower turnPower;
            if (mecanumPower.turn == 0.0d) {
                // the user isn't asking to turn, so getLast a
                // correction value to hold our desired heading
                turnPower = turnPower(headingController.getControl());
            } else {
                turnPower = turnPower(mecanumPower.turn);
            }

            drivePower = DrivePower.combine(strafePower, turnPower);
        }
        lastTurn = mecanumPower.turn;
        return drivePower;
    }

}