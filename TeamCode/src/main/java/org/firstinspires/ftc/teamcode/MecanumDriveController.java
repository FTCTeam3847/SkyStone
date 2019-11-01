package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Hardware.AngularPController;

import java.util.Arrays;
import java.util.Collections;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.signum;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static org.firstinspires.ftc.teamcode.Hardware.AngularPController.addAngle;

public class MecanumDriveController {
    private final AngularPController headingController;
    private double targetAngle;

    public double getTargetAngle() {
        return targetAngle;
    }

    public double getCurrentAngle() {
        return headingController.getCurrent();
    }

    private double last_x;


    public MecanumDriveController(AngularPController headingController) {
        this.headingController = headingController;
    }

    private double maxMagnitude(Double... ds) {
        double ret = abs(ds[0]);
        for (int i = 1; i < ds.length; i++) {
            double dsi = abs(ds[i]);
            ret = (ret > dsi) ? ret : dsi;
        }
        return ret;
    }

    public DrivePower update(double left_x, double left_y, double right_x) {
        int sensitivity = 3;
        final DrivePower drivePower;
        double currentAngle = headingController.update();

        if (left_x == 0 && left_y == 0 && right_x == 0) {
            drivePower = new DrivePower(0, 0, 0, 0);
            targetAngle = currentAngle;
            headingController.setDesired(targetAngle);
        } else {
            if (right_x != 0) { // Turning
                double turnDirection = signum(-right_x);
                double turnPower = pow(abs(right_x), sensitivity);
                targetAngle = addAngle(currentAngle, turnDirection * turnPower * 90.0);
            } else if (last_x != 0) { // Stopped turning, hold currentAngle
                targetAngle = currentAngle;
            }

            headingController.setDesired(targetAngle);
            headingController.update();
            double headingCorrection = headingController.getControlValue();

            left_y = -left_y;
            double strafeMagnitude = Math.sqrt(pow(left_x, 2) + pow(left_y, 2));
            double theta = Math.atan2(left_y, left_x);
            double strafePower = pow(strafeMagnitude, sensitivity);

            double PI_4 = PI / 4;
            double ROOT_2_2 = sin(PI_4);

            double strafeRFLB = sin(theta - PI / 4) / ROOT_2_2 * strafePower;
            double strafeRBLB = sin(theta + PI / 4) / ROOT_2_2 * strafePower;

            double rightForWithTurn = strafeRFLB + headingCorrection;
            double rightBackWithTurn = strafeRBLB + headingCorrection;
            double leftForWithTurn = strafeRBLB - headingCorrection;
            double leftBackWithTurn = strafeRFLB - headingCorrection;

            double scale = maxMagnitude(
                    1.0,
                    rightForWithTurn,
                    rightBackWithTurn,
                    leftForWithTurn,
                    leftBackWithTurn
            );

            double rightFor = rightForWithTurn / scale;
            double rightBack = rightBackWithTurn / scale;
            double leftFor = leftForWithTurn / scale;
            double leftBack = leftBackWithTurn / scale;

            drivePower = new DrivePower(
                    rightFor,
                    rightBack,
                    leftFor,
                    leftBack);
        }
        last_x = right_x;
        return drivePower;

    }


}
