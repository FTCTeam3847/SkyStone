package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
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
import org.firstinspires.ftc.teamcode.controller.RangeSensor;
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

    public CRServo capstoneLifterServo;


    public int innerSkystone = 6;//[3-6] close to bridge, assume 6th block
    public int outerSkystone = 3;//[1-3] close to wall, assume 3rd block

    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;
    private BNO055IMU imu;
    private HeadingController headingController;
    private HeadingLocalizer headingLocalizer;
    private MecanumDrive mecanum;
    private MecanumDriveController mDrive;
    private MecanumLocalizer mecanumLocalizer;
    private VuforiaLocalizer vuforiaLocalizer;
    private SkyStoneLocalizer skyStoneLocalizer;
    private BufferingLocalizer bufferingLocalizer;

    private CombinedLocalizer combinedLocalizer;


    TowerBuilder towerBuilder;
    ColorSensor sensorColor;

    RangeSensor rangeLeft;
    RangeSensor rangeRight;
    RangeSensor rangeBack;
    RangeSensor rangeFront;
    RangeSensor rangeTop;



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
                2.0d,
                0.0d
        );
        mecanumLocalizer = new MecanumLocalizer(
                System::nanoTime,
                headingLocalizer::getLast,
                52.0
        );
        mDrive = new MecanumDriveController(headingController, this::setDrivePower);
        mecanum = new LocalizingMecanumDrive(mDrive, mecanumLocalizer);
        vuforiaLocalizer = initVuforia(hardwareMap);
        skyStoneLocalizer = new SkyStoneLocalizer(vuforiaLocalizer);

        bufferingLocalizer = new BufferingLocalizer(skyStoneLocalizer);


        combinedLocalizer = new CombinedLocalizer(headingLocalizer, mecanumLocalizer, bufferingLocalizer, this::isInMotion);

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

        capstoneLifterServo = hardwareMap.get(CRServo.class, "capstone-lifter");

        capstoneLifterServo.setPower(0.0d);

        CapstoneLifter capstoneLifter =
                new CapstoneLifter(
                        capstoneLifterServo::setPower,
                        capstoneLifterServo::getPower
                );
//        capstoneLifter.setPosition(0.01);


        towerBuilder = new TowerBuilder(towerGrabber, towerLifter, blockLifter, blockExtender, blockGrabber, capstoneLifter);


// get a reference to the color sensor.
        sensorColor = hardwareMap.get(ColorSensor.class, "color1");

        DistanceSensor rangeSensorLeft = hardwareMap.get(DistanceSensor.class, "rangeLeft");
        rangeLeft = new RangeSensor(() -> rangeSensorLeft.getDistance(DistanceUnit.INCH));

        DistanceSensor rangeSensorRight = hardwareMap.get(DistanceSensor.class, "rangeRight");
        rangeRight = new RangeSensor(() -> rangeSensorRight.getDistance(DistanceUnit.INCH));

        DistanceSensor rangeSensorBack = hardwareMap.get(DistanceSensor.class, "rangeBack");
        rangeBack = new RangeSensor(() -> rangeSensorBack.getDistance(DistanceUnit.INCH));

        DistanceSensor rangeSensorFront = hardwareMap.get(DistanceSensor.class, "rangeFront");
        rangeFront = new RangeSensor(() -> rangeSensorFront.getDistance(DistanceUnit.INCH));

        DistanceSensor rangeSensorTop = hardwareMap.get(DistanceSensor.class, "rangeTop");
        rangeTop = new RangeSensor(() -> rangeSensorTop.getDistance(DistanceUnit.INCH));
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

        //rangeSensor.getCurrent();

        updateTelemetry();
    }

    @Override
    public void stop() {
        mecanum.stop();
        towerBuilder.stop();
        skyStoneLocalizer.stop(); // stops vuforia tracking until OpMode is restarted
    }

    private void updateTelemetry() {
        telemetry.addData("inner", innerSkystone);
        telemetry.addData("outer", outerSkystone);

//        telemetry.addData("pos", combinedLocalizer);
//        telemetry.addData("mecanumLocalizer", mecanumLocalizer);
//        telemetry.addData("skyStoneLocalizer", skyStoneLocalizer);
//        telemetry.addData("buffering", bufferingLocalizer);
//        telemetry.addData("heading", headingLocalizer);

        telemetry.addLine("range: ")
                .addData("l", "%.2f", rangeLeft.getLast())
                .addData("r", "%.2f", rangeRight.getLast())
                .addData("b", "%.2f", rangeBack.getLast())
                .addData("f", "%.2f", rangeFront.getLast())
                .addData("t", "%.2f", rangeTop.getLast())
        ;
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
    public void setInnerSkystone(int innerSkystone) {
        this.innerSkystone = innerSkystone;
        this.outerSkystone = innerSkystone - 3;
    }

    @Override
    public void setOuterSkystone(int outerSkystone) {
        this.outerSkystone = outerSkystone;
        this.innerSkystone = outerSkystone + 3;
    }

    @Override
    public int getInnerSkystone() {
        return innerSkystone;
    }

    @Override
    public int getOuterSkystone() {
        return outerSkystone;
    }

    @Override
    public ColorSensor getColorSensor() {
        return sensorColor;
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

    @Override
    public boolean isInMotion() {
        return mDrive.isMoving();
    }

    public RangeSensor getRangeLeft() {
        return rangeLeft;
    }

    @Override
    public RangeSensor getRangeRight() {
        return rangeRight;
    }

    @Override
    public RangeSensor getRangeBack() {
        return rangeBack;
    }

    @Override
    public RangeSensor getRangeFront() {
        return rangeFront;
    }

    @Override
    public RangeSensor getRangeTop() {
        return rangeTop;
    }

}
