package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.DrivePower;


public class BaseOp extends OpMode {

    //drivetrain
    public DcMotor leftFrontMotor;
    public DcMotor leftBackMotor;
    public DcMotor rightFrontMotor;
    public DcMotor rightBackMotor;

    //Tower lifter
    public DcMotor leftTowerLifter;
    public DcMotor rightTowerLifter;

    public Servo leftTowerGrabber;
    public Servo rightTowerGrabber;

    //Individual block lifter
    public Servo grabber;

    public CRServo extender;
    public CRServo leftBlockLifter;
    public CRServo rightBlockLifter;

    @Override
    public void init() {
        //Drivetrain:

        //Primary Port 3
        leftFrontMotor = hardwareMap.get(DcMotor.class, "motor-left-front");
        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Primary Port 1
        leftBackMotor = hardwareMap.get(DcMotor.class, "motor-left-back");
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Primary Port 2
        rightFrontMotor = hardwareMap.get(DcMotor.class, "motor-right-front");
        rightFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Primary Port 0
        rightBackMotor = hardwareMap.get(DcMotor.class, "motor-right-back");
        rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Tower Grabber + Lifter:

        //Secondary Port 0
        leftTowerLifter = hardwareMap.get(DcMotor.class, "left-grabber-lifter");
        leftTowerLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftTowerLifter.setTargetPosition(0);
        //leftTowerLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftTowerLifter.setDirection(DcMotor.Direction.REVERSE);
        //leftTowerLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftTowerLifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //Secondary Port 1
        rightTowerLifter = hardwareMap.get(DcMotor.class, "right-grabber-lifter");
        rightTowerLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightTowerLifter.setTargetPosition(0);
        //rightTowerLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //rightTowerLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightTowerLifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Primary Port 0
        leftTowerGrabber = hardwareMap.get(Servo.class, "left-grabber");

        //Primary Port 1
        rightTowerGrabber = hardwareMap.get(Servo.class, "right-grabber");

        //Block Grabber + Placer:

        //Primary Port 2
        grabber = hardwareMap.get(Servo.class, "block-grabber");

        //Primary Port 3
        extender = hardwareMap.get(CRServo.class, "extender");

        //Primary Port 4
        leftBlockLifter = hardwareMap.get(CRServo.class, "left-extender-lifter");

        //Primary Port 5
        rightBlockLifter = hardwareMap.get(CRServo.class, "right-extender-lifter");
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
}
