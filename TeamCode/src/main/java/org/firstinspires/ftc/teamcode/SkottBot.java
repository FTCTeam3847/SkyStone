package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;
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
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumDrive;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumDriveController;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumLocalizer;
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

    public  Servo capstoneLifterServo;

//    public ColorSensor color1;
//    public ColorSensor color2;
//    public I2cDevice range1;
//
//    byte[] range1Cache; //The read will return an array of bytes. They are stored in this variable
//    I2cAddr range1Address = new I2cAddr(0x14); //Default I2C address for MR Range (7-bit)
//    public static final int RANGE1_REG_START = 0x04; //Register to start reading
//    public static final int RANGE1_READ_LENGTH = 2; //Number of byte to read
//    public I2cDeviceSynch range1Reader;


    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;
    private BNO055IMU imu;
    private HeadingController headingController;
    private HeadingLocalizer headingLocalizer;
    private MecanumDrive mecanum;
    private VuforiaLocalizer vuforiaLocalizer;
    private SkyStoneLocalizer skyStoneLocalizer;
    private BufferingLocalizer bufferingLocalizer;

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
                28.0
        );
        mecanum = new MecanumDriveController(headingController, this::setDrivePower);
        vuforiaLocalizer = initVuforia(hardwareMap);
        skyStoneLocalizer = new SkyStoneLocalizer(vuforiaLocalizer);

        bufferingLocalizer = new BufferingLocalizer(skyStoneLocalizer);


        combinedLocalizer = new CombinedLocalizer(headingLocalizer, mecanumLocalizer, bufferingLocalizer);

        //Primary Port 3
        leftFrontMotor = hardwareMap.get(DcMotor.class, "motor-left-front");
        leftFrontMotor.setDirection(REVERSE);
        leftFrontMotor.setZeroPowerBehavior(BRAKE);
        leftFrontMotor.setMode(RUN_USING_ENCODER);

        //Primary Port 1
        leftBackMotor = hardwareMap.get(DcMotor.class, "motor-left-back");
        leftBackMotor.setDirection(FORWARD);
        leftBackMotor.setZeroPowerBehavior(BRAKE);
        leftBackMotor.setMode(RUN_USING_ENCODER);

        //Primary Port 2
        rightFrontMotor = hardwareMap.get(DcMotor.class, "motor-right-front");
        rightFrontMotor.setDirection(FORWARD);
        rightFrontMotor.setZeroPowerBehavior(BRAKE);
        rightFrontMotor.setMode(RUN_USING_ENCODER);

        //Primary Port 0
        rightBackMotor = hardwareMap.get(DcMotor.class, "motor-right-back");
        rightBackMotor.setDirection(REVERSE);
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


//        range1 = hardwareMap.i2cDevice.get("range1");
//        range1Reader = new I2cDeviceSynchImpl(range1, range1Address, false);
//        range1Reader.engage();

        //Color Sensor 1 I2C Port 1
        //color1 = hardwareMap.colorSensor.get("color1");

        //Color Sensor 2 I2C Port 2
        //color2 = hardwareMap.colorSensor.get("color2");

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
        combinedLocalizer.getCurrent();
        updateTelemetry();
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        combinedLocalizer.getCurrent();
        towerBuilder.loop();

//        range1Cache = range1Reader.read(RANGE1_REG_START, RANGE1_READ_LENGTH);

        updateTelemetry();
    }

    @Override
    public void stop() {
        towerBuilder.stop();
        skyStoneLocalizer.stop();
        mecanum.setPower(MecanumPower.ZERO);
    }

    private void updateTelemetry() {
//        telemetry.addData("Ultra Sonic", range1Cache[0] & 0xFF);
//        telemetry.addData("ODS", range1Cache[1] & 0xFF);

        telemetry.addData("heading", headingLocalizer);
        telemetry.addData("skyStoneLocalizer", skyStoneLocalizer);
        telemetry.addData("buffering", bufferingLocalizer);
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

    @Override
    public double getAutonomousSpeed() {
        return 0.6;
    }

}
