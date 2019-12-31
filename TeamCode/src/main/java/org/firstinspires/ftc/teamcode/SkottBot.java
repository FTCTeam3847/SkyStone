package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Trinkets.BlockExtender;
import org.firstinspires.ftc.teamcode.Trinkets.BlockGrabber;
import org.firstinspires.ftc.teamcode.Trinkets.BlockLifter;
import org.firstinspires.ftc.teamcode.Trinkets.TowerBuilder;
import org.firstinspires.ftc.teamcode.Trinkets.TowerGrabber;
import org.firstinspires.ftc.teamcode.Trinkets.TowerLifter;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.controller.HeadingController;
import org.firstinspires.ftc.teamcode.drive.DrivePower;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumDriveController;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static org.firstinspires.ftc.teamcode.HardwareMapUtils.initImu;
import static org.firstinspires.ftc.teamcode.HardwareMapUtils.initVuforia;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.normalize;

public class SkottBot implements SkystoneBot {

    public DcMotor leftFrontMotor;
    public DcMotor leftBackMotor;
    public DcMotor rightFrontMotor;
    public DcMotor rightBackMotor;

    public Servo leftTowerGrabber;
    public Servo rightTowerGrabber;
    public DcMotor leftTowerLifter;
    public DcMotor rightTowerLifter;

    public Servo grabber;

    public CRServo extender;
    public CRServo leftBlockLifter;
    public CRServo rightBlockLifter;

    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;
    private MecanumDriveController mecanum;
    private BNO055IMU imu;
    private HeadingController headingController;
    private SkyStoneLocalizer skyStoneLocalizer;
    private VuforiaLocalizer vuforiaLocalizer;

    TowerBuilder towerBuilder;

    public SkottBot(
            HardwareMap hardwareMap,
            Telemetry telemetry
    ) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }

    @Override
    public void init() {
        imu = initImu(hardwareMap);
        headingController = new HeadingController(
                () -> normalize((double) imu.getAngularOrientation().firstAngle),
                0.0d,
                4.0d,
                0.0d);
        mecanum = new MecanumDriveController(headingController);
        vuforiaLocalizer = initVuforia(hardwareMap);
        skyStoneLocalizer = new SkyStoneLocalizer(vuforiaLocalizer);

        //Primary Port 3
        leftFrontMotor = hardwareMap.get(DcMotor.class, "motor-left-front");
        leftFrontMotor.setDirection(FORWARD);
        leftFrontMotor.setZeroPowerBehavior(BRAKE);
        leftFrontMotor.setMode(RUN_USING_ENCODER);

        //Primary Port 1
        leftBackMotor = hardwareMap.get(DcMotor.class, "motor-left-back");
        leftBackMotor.setDirection(REVERSE);
        leftBackMotor.setZeroPowerBehavior(BRAKE);
        leftBackMotor.setMode(RUN_USING_ENCODER);

        //Primary Port 2
        rightFrontMotor = hardwareMap.get(DcMotor.class, "motor-right-front");
        rightFrontMotor.setDirection(REVERSE);
        rightFrontMotor.setZeroPowerBehavior(BRAKE);
        rightFrontMotor.setMode(RUN_USING_ENCODER);

        //Primary Port 0
        rightBackMotor = hardwareMap.get(DcMotor.class, "motor-right-back");
        rightBackMotor.setDirection(FORWARD);
        rightBackMotor.setZeroPowerBehavior(BRAKE);
        rightBackMotor.setMode(RUN_USING_ENCODER);

        //Primary Port 0
        leftTowerGrabber = hardwareMap.get(Servo.class, "left-grabber");

        //Primary Port 1
        rightTowerGrabber = hardwareMap.get(Servo.class, "right-grabber");

        TowerGrabber towerGrabber = new TowerGrabber(
                leftTowerGrabber::setPosition,
                rightTowerGrabber::setPosition,
                leftTowerGrabber::getPosition,
                rightTowerGrabber::getPosition
        );

        //Secondary Port 0
        leftTowerLifter = hardwareMap.get(DcMotor.class, "left-grabber-lifter");
        leftTowerLifter.setMode(STOP_AND_RESET_ENCODER);
        leftTowerLifter.setTargetPosition(0);
        leftTowerLifter.setDirection(FORWARD);
        leftTowerLifter.setZeroPowerBehavior(BRAKE);
        leftTowerLifter.setMode(RUN_WITHOUT_ENCODER);

        //Secondary Port 1
        rightTowerLifter = hardwareMap.get(DcMotor.class, "right-grabber-lifter");
        rightTowerLifter.setMode(STOP_AND_RESET_ENCODER);
        rightTowerLifter.setTargetPosition(0);
        rightTowerLifter.setDirection(REVERSE);
        rightTowerLifter.setZeroPowerBehavior(BRAKE);
        rightTowerLifter.setMode(RUN_WITHOUT_ENCODER);

        TowerLifter towerLifter =
                new TowerLifter(
                        leftTowerLifter::setPower,
                        rightTowerLifter::setPower,
                        leftTowerLifter::getCurrentPosition,
                        rightTowerLifter::getCurrentPosition
                );

        //Primary Port 4
        leftBlockLifter = hardwareMap.get(CRServo.class, "left-extender-lifter");
        leftBlockLifter.setDirection(FORWARD);

        //Primary Port 5
        rightBlockLifter = hardwareMap.get(CRServo.class, "right-extender-lifter");
        rightBlockLifter.setDirection(REVERSE);

        BlockLifter blockLifter =
                new BlockLifter(
                        leftBlockLifter::setPower,
                        rightBlockLifter::setPower
                );

        //Primary Port 3
        extender = hardwareMap.get(CRServo.class, "extender");
        extender.setDirection(REVERSE);

        BlockExtender blockExtender =
                new BlockExtender(
                        extender::setPower,
                        extender::getPower
                );

        //Primary Port 2
        grabber = hardwareMap.get(Servo.class, "block-grabber");

        BlockGrabber blockGrabber =
                new BlockGrabber(
                        grabber::setPosition,
                        grabber::getPosition
                );

        towerBuilder = new TowerBuilder(towerGrabber, towerLifter, blockLifter, blockExtender, blockGrabber);
    }

    @Override
    public void init_loop() {
        skyStoneLocalizer.getCurrent();
        updateTelemetry();
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        skyStoneLocalizer.getCurrent();
        updateTelemetry();
    }

    @Override
    public void stop() {
        skyStoneLocalizer.stop();
    }

    private void updateTelemetry() {
        telemetry.addData("localizer", skyStoneLocalizer);
        telemetry.addData("heading", headingController);
        telemetry.addData("tower", towerBuilder);
        telemetry.addData("leftTowerLifter", "%.2f", leftTowerLifter.getPower());
        telemetry.addData("rightTowerLifter", "%.2f", rightTowerLifter.getPower());
    }

    private void move4(double leftFront, double leftBack, double rightFront, double rightBack) {
        leftFrontMotor.setPower(leftFront);
        leftBackMotor.setPower(leftBack);
        rightFrontMotor.setPower(rightFront);
        rightBackMotor.setPower(rightBack);
    }

    private void move(DrivePower drivePower) {
        move4(drivePower.leftFront, drivePower.leftBack, drivePower.rightFront, drivePower.rightBack);
    }

    @Override
    public void move(MecanumPower mecanumPower) {
        mecanum.setTarget(mecanumPower);
        move(mecanum.getControl());
    }


    @Override
    public double getFieldRelativeHeading() {
        return headingController.getCurrent();
    }

    @Override
    public TowerBuilder getTowerBuilder() {
        return towerBuilder;
    }


}
