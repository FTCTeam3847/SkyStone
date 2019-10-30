package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Hardware.AngularPController;

import static java.lang.Math.pow;
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

    public DrivePower update(double left_x, double left_y, double right_x) {
        int sensitivity = 3;
        final DrivePower drivePower;
        double angle = headingController.update();
        if (right_x != 0) { //Turn
            targetAngle = addAngle(angle, pow(-right_x, sensitivity) * 90.0);
        } else if (last_x != 0) { //stop turning and hold angle
            targetAngle = angle;
        }

        headingController.setDesired(targetAngle);

        if (left_x == 0 && left_y == 0 && right_x == 0) {
            drivePower = new DrivePower(0,0,0,0);
            targetAngle = angle;
        } else {
            double correction = headingController.getControlValue();

            left_y = -left_y;
            double rad = Math.sqrt(pow(left_x, 2) + pow(left_y, 2));
            double theta = Math.atan2(left_y, left_x);
            double magnitute = pow(rad, sensitivity);

            drivePower = new DrivePower(
                    Math.sin(theta - 45) * magnitute + correction,
                    Math.sin(theta + 45) * magnitute + correction,
                    Math.sin(theta + 45) * magnitute - correction,
                    Math.sin(theta - 45) * magnitute - correction);
        }
        last_x = right_x;
        return drivePower;

    }


}
