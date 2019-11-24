package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
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

    public CRServo liftingServo;

    @Override
    public void init() {
        leftFrontMotor = hardwareMap.get(DcMotor.class, "motor-left-front");
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftBackMotor = hardwareMap.get(DcMotor.class, "motor-left-back");
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightFrontMotor = hardwareMap.get(DcMotor.class, "motor-right-front");
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightBackMotor = hardwareMap.get(DcMotor.class, "motor-right-back");
        rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftingServo = hardwareMap.crservo.get("lservo");
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
    }

    public void move4(double leftFront, double leftBack, double rightFront, double rightBack) {
        leftFrontMotor.setPower(leftFront);
        leftBackMotor.setPower(leftBack);
        rightFrontMotor.setPower(rightFront);
        rightBackMotor.setPower(rightBack);
    }

    public void move(DrivePower drivePower) {
        move4(drivePower.leftFor, drivePower.leftBack, drivePower.rightFor, drivePower.rightBack);
    }


    public void lift() {
        if (gamepad1.x){
            liftingServo.setPower(0.5);
        } else if (gamepad1.y) {
            liftingServo.setPower(-0.5);
        } else {
            liftingServo.setPower(0.0);
        }
    }
}
