package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.ArrayList;

public class ChasisObject {

    private double leftFor, leftBack, rightFor, rightBack;
    private boolean turned;
    private Orientation lastAngles = new Orientation();
    private BNO055IMU imu;
    private double targetAngle;

    public ChasisObject(BNO055IMU imu) {
        this.imu = imu;
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        targetAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        imu.initialize(parameters);
    }

    public void calculate(double left_x, double left_y, double right_x) {
        if (right_x != 0) {
            left_y = -left_y;
            left_x = -left_x;

            double rad = Math.sqrt(Math.pow(left_x, 2) + Math.pow(left_y, 2));
            double theta = Math.atan2(left_y, left_x);
            int sensitivity = 3;
            double magnitude = Math.pow(rad, sensitivity);

            rightFor = Math.sin(theta - 45) * magnitude - right_x;
            leftBack = Math.sin(theta - 45) * magnitude + right_x;
            leftFor = Math.sin(theta + 45) * magnitude + right_x;
            rightBack = Math.sin(theta + 45) * magnitude - right_x;
            turned = true;
        } else {
            if(turned) {
                Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                targetAngle = angles.firstAngle;
                turned = false;
            }
            Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            double currentAngle = angles.firstAngle;
            double deltaAngle = targetAngle - currentAngle;
            double correction = 0;
            if (deltaAngle > 0) {
                correction = 0.2;
            } else if (deltaAngle < 0) {
                correction = -0.2;
            }


            left_y = -left_y;
            left_x = -left_x;

            double rad = Math.sqrt(Math.pow(left_x, 2) + Math.pow(left_y, 2));
            double theta = Math.atan2(left_y, left_x);
            int sensitivity = 3;
            double magnitute = Math.pow(rad, sensitivity);

            rightFor = Math.sin(theta - 45) * magnitute-correction;
            leftBack = Math.sin(theta - 45) * magnitute+correction;
            leftFor = Math.sin(theta + 45) * magnitute+correction;
            rightBack = Math.sin(theta + 45) * magnitute-correction;
        }
    }

    public double getLeftFor() {
        return leftFor;
    }

    public double getLeftBack() {
        return leftBack;
    }

    public double getRightFor() {
        return rightFor;
    }

    public double getRightBack() {
        return rightBack;
    }
}
