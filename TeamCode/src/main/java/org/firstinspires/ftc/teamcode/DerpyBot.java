package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
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
import org.firstinspires.ftc.teamcode.polar.PolarUtil;

import static org.firstinspires.ftc.teamcode.HardwareMapUtils.initImu;
import static org.firstinspires.ftc.teamcode.HardwareMapUtils.initVuforia;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.normalize;

public class DerpyBot implements SkystoneBot {

    public DcMotor leftFrontMotor;
    public DcMotor leftBackMotor;
    public DcMotor rightFrontMotor;
    public DcMotor rightBackMotor;

    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;
    private MecanumDrive mecanum;
    private BNO055IMU imu;
    HeadingLocalizer headingLocalizer;
    HeadingController headingController;
    SkyStoneLocalizer skyStoneLocalizer;
    MecanumLocalizer mecanumLocalizer;

    CombinedLocalizer combinedLocalizer;
    FieldPosition lastLoc = FieldPosition.ORIGIN;

    VuforiaLocalizer vuforiaLocalizer;


    long lapse;

    public DerpyBot(
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
                () -> normalize((double) imu.getAngularOrientation().firstAngle),
                0.0d,
                4.0d,
                0.0d);
        MecanumDriveController drive = new MecanumDriveController(headingController, this::move);
        mecanumLocalizer = new MecanumLocalizer(System::nanoTime, headingLocalizer::getLast, 32); //derpy-28.6, 25.6
        mecanum= new LocalizingMecanumDrive(drive, mecanumLocalizer);

        vuforiaLocalizer = initVuforia(hardwareMap);
        skyStoneLocalizer = new SkyStoneLocalizer(vuforiaLocalizer);


        combinedLocalizer = new CombinedLocalizer(headingLocalizer, mecanumLocalizer, skyStoneLocalizer);


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
        long startTime = System.nanoTime();
        lastLoc = combinedLocalizer.getCurrent();
        updateTelemetry();
        long endTime = System.nanoTime();
        lapse = (endTime-startTime)/1_000_000_000;
    }

    @Override
    public void stop() {
        skyStoneLocalizer.stop();
    }

    private void updateTelemetry() {
        telemetry.addData("skystone", skyStoneLocalizer);
        telemetry.addData("heading", headingLocalizer);
        telemetry.addData("mecanum", mecanumLocalizer);

        //telemetry.addData("xy", PolarUtil.toXY(lastLoc.polarCoord));
        telemetry.addData("combined", combinedLocalizer);

        telemetry.addData("time", lapse);
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


}
