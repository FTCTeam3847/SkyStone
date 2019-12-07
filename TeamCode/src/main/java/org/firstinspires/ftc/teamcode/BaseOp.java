package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


public class BaseOp extends OpMode {

    //drivetrain
    public DcMotor leftFrontMotor;
    public DcMotor leftBackMotor;
    public DcMotor rightFrontMotor;
    public DcMotor rightBackMotor;

    public CRServo liftingServo;

    //Tower lifter
    public DcMotor leftGrabberLifter;
    public DcMotor rightGrabberLifter;

    public Servo leftGrabber;
    public Servo rightGrabber;

    //Individual block lifter
    public Servo blockGrabber;

    public Servo slider;
    public CRServo leftSliderLifter;
    public CRServo rightSliderLifter;

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
        leftGrabberLifter = hardwareMap.get(DcMotor.class, "left-grabber-lifter");
        leftGrabberLifter.setTargetPosition(0);
        leftGrabberLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftGrabberLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftGrabberLifter.setDirection(DcMotor.Direction.REVERSE);

        //Secondary Port 1
        rightGrabberLifter = hardwareMap.get(DcMotor.class, "right-grabber-lifter");
        rightGrabberLifter.setTargetPosition(0);
        rightGrabberLifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightGrabberLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Primary Port 0
        leftGrabber = hardwareMap.get(Servo.class, "left-grabber");

        //Primary Port 1
        rightGrabber = hardwareMap.get(Servo.class, "right-grabber");

        //Block Grabber + Placer:

        //Primary Port 2
        blockGrabber = hardwareMap.get(Servo.class, "block-grabber");

        //Primary Port 3
        slider = hardwareMap.get(Servo.class, "slider");

        //Primary Port 4
        leftSliderLifter = hardwareMap.get(CRServo.class, "left-slider-lifter");

        //Primary Port 5
        rightSliderLifter = hardwareMap.get(CRServo.class, "right-slider-lifter");
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

    public void moveGrabber (double power) {
        leftGrabberLifter.setPower(power);
        rightGrabberLifter.setPower(power);
    }

    public void grabTower () {
        //Need proper angles/values for grabTower to work - these are filler values
        leftGrabber.setPosition(0.5);
        rightGrabber.setPosition(0.5);
    }

    public void releaseTower () {
        //Need proper angles/values for releaseTower to work - these are filler values
        leftGrabber.setPosition(0);
        rightGrabber.setPosition(1);
    }

    public void grabBlock () {
        blockGrabber.setPosition(1); //filler value - need real value
    }

    public void releaseBlock () {
        blockGrabber.setPosition(0); //filler value - need real value
    }

    public void slide (double speed) {
        //slider.setPower(speed);
    }

    public void moveSlider (double speed) {
        leftSliderLifter.setPower(-speed);
        rightSliderLifter.setPower(speed);
    }
}
