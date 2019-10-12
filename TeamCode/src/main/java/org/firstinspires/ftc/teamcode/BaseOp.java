package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Hardware.AcceleratingDcMotor;
import org.firstinspires.ftc.teamcode.Hardware.ClampingDcMotor;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;


public class BaseOp extends OpMode {

    //drivetrain
    public DcMotor leftFrontMotor;
    public DcMotor leftBackMotor;
    public DcMotor rightFrontMotor;
    public DcMotor rightBackMotor;

    @Override
    public void init() {
        leftFrontMotor = hardwareMap.get(DcMotor.class, "motor-left-front");
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);

        leftBackMotor = hardwareMap.get(DcMotor.class, "motor-left-back");
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);

        rightFrontMotor = hardwareMap.get(DcMotor.class, "motor-right-front");

        rightBackMotor = hardwareMap.get(DcMotor.class, "motor-right-back");
        //working on accelerating DcMotor, but it ain't working yet. The motor stays at a constant rate instead of
        //accelerating.
//        leftFrontMotor = new AcceleratingDcMotor(new ClampingDcMotor(hardwareMap.dcMotor.get("motor-left-front")));
//        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        leftBackMotor = new AcceleratingDcMotor(new ClampingDcMotor(hardwareMap.dcMotor.get("motor-left-back")));
//        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        rightFrontMotor = new AcceleratingDcMotor(new ClampingDcMotor(hardwareMap.dcMotor.get("motor-right-front")));
//
//        rightBackMotor = new AcceleratingDcMotor(new ClampingDcMotor(hardwareMap.dcMotor.get("motor-right-back")));
    }

    @Override
    public void loop() {
        leftFrontMotor.setPower(leftFrontMotor.getPower());
        leftBackMotor.setPower(leftBackMotor.getPower());
        rightFrontMotor.setPower(rightFrontMotor.getPower());
        rightBackMotor.setPower(rightBackMotor.getPower());
    }

    public void move4(double leftFront, double leftBack, double rightFront, double rightBack) {
        leftFrontMotor.setPower(leftFront);
        leftBackMotor.setPower(leftBack);
        rightFrontMotor.setPower(rightFront);
        rightBackMotor.setPower(rightBack);
    }

//    private void moveLR(double left, double right) {
//        move4(left, left, right, right);
//    }

//    public void turn(double power) {
//        move4(-power, -power, power, power);
//    }

    public void moveStop() {
        move4(0, 0, 0, 0);
    }

//    public void moveStraight(double power) {
//        moveLR(power, power);
//    }
//
//    public void strafeLeft (double power) {
//        move4(power, -power, power, -power);
//    }
//
//    public void strafeRight (double power) {
//        move4(-power, power, -power, power);
//    }

//    static double addRadians(double a, double b) {
//        double tmp = (a + b + PI) % (2 * PI);
//        if (tmp < 0.0) tmp = (2 * PI) + tmp;
//        return tmp - PI;
//    }
//
//    static double subtractRadians(double a, double b) {
//        return addRadians(a, -b);
//    }
//
//    void geometricDrive(double x, double y) {
//        double x2 = pow(x, 2.0);
//        double y2 = pow(y, 2.0);
//        double radius = sqrt(x2 + y2);
//
//        // Larger sensitivity values correspond to lower
//        // twitchiness. 1 is most twitchy. 3 to 5 feel
//        // pretty good. 7 is kinda mushy.
//        double sensitivity = 3;
//        double magnitude = pow(radius, sensitivity);
//
//        double theta = atan2(y, x);
//        double rad45 = toRadians(45.0);
//        double angle = subtractRadians(theta, rad45);
//
//        double cosAngle = cos(angle);
//        double cos45 = cos(rad45); // constant, same as √2 / 2
//        double left = magnitude * (cosAngle / cos45);
//
//        double sinAngle = sin(angle);
//        double sin45 = sin(rad45); // constant, same as √2 / 2
//        double right = magnitude * (sinAngle / sin45);
//
//        moveLR(left, right);
//    }

}
