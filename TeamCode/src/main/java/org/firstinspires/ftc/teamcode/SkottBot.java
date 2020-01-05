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
import org.firstinspires.ftc.teamcode.Trinkets.CapstoneLifter;
import org.firstinspires.ftc.teamcode.Trinkets.TowerBuilder;
import org.firstinspires.ftc.teamcode.Trinkets.TowerGrabber;
import org.firstinspires.ftc.teamcode.Trinkets.TowerLifter;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.controller.HeadingController;
import org.firstinspires.ftc.teamcode.controller.HeadingLocalizer;
import org.firstinspires.ftc.teamcode.controller.Localizer;
import org.firstinspires.ftc.teamcode.drive.DrivePower;
import org.firstinspires.ftc.teamcode.drive.mecanum.LocalizingMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumDrive;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumDriveController;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumLocalizer;

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

    public  Servo capstoneLifterServo;

    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;
    private BNO055IMU imu;
    private HeadingController headingController;
    private HeadingLocalizer headingLocalizer;
    private MecanumDrive mecanum;
    private MecanumLocalizer mecanumLocalizer;
    private VuforiaLocalizer vuforiaLocalizer;
    private SkyStoneLocalizer skyStoneLocalizer;

    private CombinedLocalizer combinedLocalizer;


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
        headingLocalizer = new HeadingLocalizer(
                () -> normalize((double) imu.getAngularOrientation().firstAngle)
        );
        headingController = new HeadingController(
                headingLocalizer::getLast,
                0.0d,
                4.0d,
                0.0d
        );
        MecanumLocalizer mecanumLocalizer = new MecanumLocalizer(
                System::nanoTime,
                headingLocalizer::getLast,
                35.0
        );
        this.mecanumLocalizer = mecanumLocalizer;
        MecanumDrive mDrive = new MecanumDriveController(headingController, this::setDrivePower);
        mecanum = new LocalizingMecanumDrive(mDrive, mecanumLocalizer);
        vuforiaLocalizer = initVuforia(hardwareMap);
        skyStoneLocalizer = new SkyStoneLocalizer(vuforiaLocalizer);

        combinedLocalizer = new CombinedLocalizer(headingLocalizer, mecanumLocalizer, skyStoneLocalizer);


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
        extender.setDirection(FORWARD);

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

        capstoneLifterServo = hardwareMap.get(Servo.class, "capstone-lifter");

        CapstoneLifter capstoneLifter =
                new CapstoneLifter(
                        capstoneLifterServo::setPosition,
                        capstoneLifterServo::getPosition
                );

        towerBuilder = new TowerBuilder(towerGrabber, towerLifter, blockLifter, blockExtender, blockGrabber, capstoneLifter);
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
        headingLocalizer.getCurrent();
        mecanumLocalizer.getCurrent();
        skyStoneLocalizer.getCurrent();
        updateTelemetry();
    }

    @Override
    public void stop() {
        skyStoneLocalizer.stop();
    }

    private void updateTelemetry() {
        telemetry.addData("heading", headingLocalizer);
        telemetry.addData("skyStoneLocalizer", skyStoneLocalizer);
        telemetry.addData("mecanumLocalizer", mecanumLocalizer);
    }

    private void setPower4(double leftFront, double leftBack, double rightFront, double rightBack) {
        leftFrontMotor.setPower(leftFront);
        leftBackMotor.setPower(leftBack);
        rightFrontMotor.setPower(rightFront);
        rightBackMotor.setPower(rightBack);
    }

    private void setDrivePower(DrivePower drivePower) {
        setPower4(drivePower.leftFront, drivePower.leftBack, drivePower.rightFront, drivePower.rightBack);
    }

    @Override
    public double getFieldRelativeHeading() {
        return headingLocalizer.getCurrent();
    }

    @Override
    public Localizer<FieldPosition> getLocalizer() {
        return combinedLocalizer;
    }

    @Override
    public MecanumDrive getMecanumDrive() {
        return mecanum;
    }

    @Override
    public TowerBuilder getTowerBuilder() {
        return towerBuilder;
    }


}
