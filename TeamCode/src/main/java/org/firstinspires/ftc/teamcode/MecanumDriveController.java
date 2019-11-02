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
            double magnitude = pow(rad, sensitivity);
            double rightFor = (Math.sin(theta - 45) * magnitude + correction)/Math.abs((Math.sin(theta - 45)));
            double rightBack = (Math.sin(theta + 45) * magnitude + correction)/Math.abs((Math.sin(theta - 45)));
            double leftFor = (Math.sin(theta + 45) * magnitude - correction)/Math.abs((Math.sin(theta - 45)));
            double leftBack = (Math.sin(theta - 45) * magnitude - correction)/Math.abs((Math.sin(theta - 45)));


            double maxOffset = maxAbs(rightFor, rightBack, leftFor, leftBack, 1.0);
            rightFor = rightFor/maxOffset;
            rightBack = rightBack/maxOffset;
            leftFor = leftFor/maxOffset;
            leftBack = leftBack/maxOffset;
            double[] wheels = new double[]{rightFor, rightBack, leftFor, leftBack};
            for (int i = 0; i < wheels.length; i++) {
                if (Math.abs(wheels[i]) < 0.09) {
                    wheels[i] = 0.0;
                }
            }
            rightFor = wheels[0];
            rightBack = wheels[1];
            leftFor = wheels[2];
            leftBack = wheels[3];


            drivePower = new DrivePower(
                    rightFor, rightBack, leftFor, leftBack);
        }
        last_x = right_x;
        return drivePower;

    }
    private static double maxAbs(double... wheels) {
        double max = 0.0;
        for (int i = 0; i < wheels.length; i++) {
            max = Math.max(Math.abs(wheels[i]), max);
        }

        return max;
    }

}