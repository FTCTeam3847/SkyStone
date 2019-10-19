package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Hardware.AngularPController;

import java.util.ArrayList;
import java.util.function.Supplier;

import static java.lang.Math.pow;
import static org.firstinspires.ftc.teamcode.Hardware.AngularPController.addAngle;

public class ChasisObject {

    private double leftFor, leftBack, rightFor, rightBack;
    private BNO055IMU imu;
    private AngularPController headingController;
    private double targetAngle;
    private  double last_x;


    public ChasisObject(BNO055IMU imu) {
        initImu(imu);
        headingController = initAngularP();
    }

    public void calculate(double left_x, double left_y, double right_x) {
        if (right_x != 0) {
            if (right_x > 0) {
                targetAngle -= 10;
            } else {
                targetAngle += 10;
            }
            if (targetAngle < -180) targetAngle = 180;
            if (targetAngle > 180) targetAngle = -180;
        }
        headingController.update();
        headingController.setDesired(targetAngle);
        double correction = headingController.getControlValue();
        left_y = -left_y;
        double rad = Math.sqrt(pow(left_x, 2) + pow(left_y, 2));
        double theta = Math.atan2(left_y, left_x);
        int sensitivity = 3;
        double magnitute = pow(rad, sensitivity);

        rightFor = Math.sin(theta - 45) * magnitute+correction;
        leftBack = Math.sin(theta - 45) * magnitute-correction;
        leftFor = Math.sin(theta + 45) * magnitute-correction;
        rightBack = Math.sin(theta + 45) * magnitute+correction;
    }

    public void tempCalculate(double left_x, double left_y, double right_x) {
        int sensitivity = 3;
        double angle = headingController.update();
        if (right_x != 0) //turn
        {
            targetAngle = addAngle(angle,pow(-right_x, sensitivity) * 90.0);
        }
        else if (last_x != 0) //stop turning and hold angle
        {
            targetAngle = angle;
        }


        headingController.setDesired(targetAngle);
        double correction = headingController.getControlValue();

        left_y = -left_y;
        double rad = Math.sqrt(pow(left_x, 2) + pow(left_y, 2));
        double theta = Math.atan2(left_y, left_x);
        double magnitute = pow(rad, sensitivity);

        rightFor = Math.sin(theta - 45) * magnitute+correction;
        leftBack = Math.sin(theta - 45) * magnitute-correction;
        leftFor = Math.sin(theta + 45) * magnitute-correction;
        rightBack = Math.sin(theta + 45) * magnitute+correction;

        last_x = right_x;
    }

    private void initImu(BNO055IMU imu) {
        this.imu = imu;
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        targetAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        imu.initialize(parameters);
    }

    private AngularPController initAngularP() {

        return new AngularPController(
                () -> (double) imu.getAngularOrientation().firstAngle,
                2.0d,
                1.0d,
                0.1d
        );
    }

    public double getCurrentAngle() {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.firstAngle;
    }

    public double getTargetAngle() {
        return targetAngle;
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
