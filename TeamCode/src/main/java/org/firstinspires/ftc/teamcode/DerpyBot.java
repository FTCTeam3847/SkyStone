package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
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
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;

import java.util.function.Supplier;

import static org.firstinspires.ftc.teamcode.HardwareMapUtils.initImu;
import static org.firstinspires.ftc.teamcode.HardwareMapUtils.initVuforia;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.normalize;

public class DerpyBot implements SkystoneBot {

    public static final int NANOS_PER_SEC = 1_000_000_000;
    public DcMotor leftFrontMotor;
    public DcMotor leftBackMotor;
    public DcMotor rightFrontMotor;
    public DcMotor rightBackMotor;

    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;
    private final Supplier<Long> nanoTime;
    private long loopEndTime;
    private double loopDuration;

    ColorSensor sensorColor;

    DistanceSensor sensorDistance;

    private MecanumDrive mecanum;
    private BNO055IMU imu;
    HeadingLocalizer headingLocalizer;
    HeadingController headingController;
    SkyStoneLocalizer skyStoneLocalizer;
    MecanumLocalizer mecanumLocalizer;

    CombinedLocalizer combinedLocalizer;

    VuforiaLocalizer vuforiaLocalizer;
    private BufferingLocalizer bufferingLocalizer;

    DistanceSensor distanceSensor;

    public int innerSkystone = 6;//[3-6] close to bridge, assume 6th block
    public int outerSkystone = 3;//[1-3] close to wall, assume 3rd block

    public DerpyBot(
            HardwareMap hardwareMap,
            Telemetry telemetry,
            Supplier<Long> nanoTime
    ) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.nanoTime = nanoTime;
    }

    @Override
    public void init() {
        imu = initImu(hardwareMap);
        headingLocalizer = new HeadingLocalizer(
                () -> normalize((double) imu.getAngularOrientation().firstAngle)
        );
        headingController = new HeadingController(
                () -> normalize((double) imu.getAngularOrientation().firstAngle),
                0.0d,
                4.0d,
                0.0d);
        MecanumDriveController drive = new MecanumDriveController(headingController, this::move);
        mecanumLocalizer = new MecanumLocalizer(nanoTime, headingLocalizer::getLast, 28.6); //derpy-28.6, 25.6
        mecanum= new LocalizingMecanumDrive(drive, mecanumLocalizer);

        vuforiaLocalizer = initVuforia(hardwareMap);
        skyStoneLocalizer = new SkyStoneLocalizer(vuforiaLocalizer);
        bufferingLocalizer = new BufferingLocalizer(skyStoneLocalizer);


        combinedLocalizer = new CombinedLocalizer(headingLocalizer, mecanumLocalizer, bufferingLocalizer, this::isInMotion);


        //Primary Port 3
        leftFrontMotor = hardwareMap.get(DcMotor.class, "motor-left-front");
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Primary Port 1
        leftBackMotor = hardwareMap.get(DcMotor.class, "motor-left-back");
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Primary Port 2
        rightFrontMotor = hardwareMap.get(DcMotor.class, "motor-right-front");
        rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Primary Port 0
        rightBackMotor = hardwareMap.get(DcMotor.class, "motor-right-back");
        rightBackMotor.setDirection(DcMotor.Direction.FORWARD);
        rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        loopEndTime = nanoTime.get();

        // get a reference to the color sensor.
        sensorColor = hardwareMap.get(ColorSensor.class, "color1");

        // get a reference to the distance sensor that shares the same name.
        sensorDistance = hardwareMap.get(DistanceSensor.class, "color1");
    }

    private void updateLoopTimer() {
        long endTime = nanoTime.get();
        loopDuration = (double)(endTime - loopEndTime) / (double)NANOS_PER_SEC;
        loopEndTime = endTime;
    }

    @Override
    public void init_loop() {
        combinedLocalizer.getCurrent();
        updateTelemetry();
        updateLoopTimer();
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        combinedLocalizer.getCurrent();
        updateTelemetry();
        updateLoopTimer();
    }

    @Override
    public void stop() {
        skyStoneLocalizer.stop();
        mecanum.setPower(MecanumPower.ZERO);
    }

    private void updateTelemetry() {
        telemetry.addData("combined", combinedLocalizer);
        telemetry.addData("buffering", bufferingLocalizer);
        telemetry.addData("skystone", skyStoneLocalizer);
        telemetry.addData("heading", headingLocalizer);
        telemetry.addData("mecanum", mecanumLocalizer);


        telemetry.addData("loop", "%.2fsec", loopDuration);
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
    public void setInnerSkystone(int innerSkystone)
    {
        this.innerSkystone = innerSkystone;
        this.outerSkystone = innerSkystone-2;
    }

    @Override
    public void setOuterSkystone(int outerSkystone)
    {
        this.outerSkystone = outerSkystone;
        this.innerSkystone = outerSkystone+2;
    }

    @Override
    public ColorSensor getColorSensor()
    {
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
    public double getAutonomousSpeed() {
        return 0.6;
    }

    @Override
    public boolean isInMotion()
    {
        return getMecanumDrive().equals(DrivePower.ZERO);
    }
}
