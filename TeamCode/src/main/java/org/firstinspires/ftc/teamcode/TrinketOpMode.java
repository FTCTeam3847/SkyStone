// left  0.30 up, 0.14 down
// right 0.30 up, 0.11 down

// leftPos  Start 0.60, End 0.15
// rightPos Start 0.25, End 0.70

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Trinkets.TowerGrabber;
import org.firstinspires.ftc.teamcode.Trinkets.TowerLifter;
import org.firstinspires.ftc.teamcode.controller.HeadingController;
import org.firstinspires.ftc.teamcode.controller.HeadingLocalizer;
import org.firstinspires.ftc.teamcode.drive.DrivePower;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumDriveController;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.gamepad.PushButton;
import org.firstinspires.ftc.teamcode.gamepad.ToggleButton;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.signum;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.normalize;


@TeleOp(name = "TrinketOpMode", group = "1")
@Disabled // obsolete now
public class TrinketOpMode extends BaseOp {
    public BNO055IMU imu;
    public MecanumDriveController mecanum;
    HeadingLocalizer headingLocalizer;
    HeadingController headingController;

    ToggleButton toggleRunMode = new ToggleButton(() -> gamepad1.left_stick_button);
    PushButton resetEncoder = new PushButton(() -> gamepad1.left_stick_button);
    ToggleButton toggleSlowMode = new ToggleButton(() -> gamepad1.right_stick_button);
    PushButton doEverything = new PushButton(() -> gamepad1.back);
    TowerLifter towerLifter;
    TowerGrabber towerGrabber;
    PushButton leftUp = new PushButton(() -> gamepad2.y);
    PushButton leftDown = new PushButton(() -> gamepad2.x);
    PushButton rightUp = new PushButton(() -> gamepad2.b);
    PushButton rightDown = new PushButton(() -> gamepad2.a);
    boolean slowMode;

    double leftPower = 0.0d;
    double rightPower = 0.0d;

    double blockGrabberOpen = 0.8;
    double blockGrabberClosed = 0.5;

    private BNO055IMU initImu(BNO055IMU imu) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        imu.initialize(parameters);
        return imu;
    }

    @Override
    public void init() {
        telemetry.addLine("Initializing...");
        telemetry.update();

        super.init();
        imu = initImu(hardwareMap.get(BNO055IMU.class, "imu"));
        headingLocalizer = new HeadingLocalizer(
                () -> normalize((double) imu.getAngularOrientation().firstAngle)
        );
        headingController = new HeadingController(
                headingLocalizer::getCurrent,
                0.0d,
                10.0d,
                0.0d);
        mecanum = new MecanumDriveController(headingController, this::move);
        towerLifter =
                new TowerLifter(
                        leftTowerLifter::setPower,
                        rightTowerLifter::setPower,
                        leftTowerLifter::getCurrentPosition,
                        rightTowerLifter::getCurrentPosition
                );
        leftTowerLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightTowerLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftTowerLifter.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
        rightTowerLifter.setMode((DcMotor.RunMode.RUN_USING_ENCODER));

        towerGrabber = new TowerGrabber(
                leftTowerGrabber::setPosition,
                rightTowerGrabber::setPosition,
                leftTowerGrabber::getPosition,
                rightTowerGrabber::getPosition
        );
        towerGrabber.setPosition(0.5);
    }

    @Override
    public void init_loop() {
        super.init_loop();
        telemetry.addLine("Initialized.");
        telemetry_loop();
        telemetry.update();
    }

    void telemetry_loop() {
//        telemetry.addData("leftBlockLifterStd", "%d", leftBlockLifterStd.getPosition());
        telemetry.addData("leftBlockLifter", "%.2f, %.2f", leftBlockLifter.getPower(), leftPower);
        telemetry.addData("rightBlockLifter", "%.2f, %.2f", rightBlockLifter.getPower(), rightPower);
    }

    private static double sensitivity(double base, double exp) {
        return signum(base) * pow(abs(base), exp);
    }

    private static final int SENSITIVITY = 3;


    @Override
    public void loop() {
        super.loop();
        slowMode = toggleSlowMode.getCurrent();

        if (doEverything.getCurrent()) {

        }
//
//        if (leftUp.getCurrent()) {
//            leftBlockLifter.setPower(leftPower += 0.1);
//        } else if (leftDown.getCurrent()) {
//            leftBlockLifter.setPower(leftPower -= 0.1);
//        }
//        if (rightUp.getCurrent()) {
//            rightBlockLifter.setPower(rightPower += 0.1);
//        } else if (rightDown.getCurrent()) {
//            rightBlockLifter.setPower(rightPower -= 0.1);
//        }

        if (gamepad1.left_trigger >= 0.5) {
            leftBlockLifter.setPower(-0.5);
            rightBlockLifter.setPower(0.5);
        } else if (gamepad1.right_trigger >= 0.5) {
            leftBlockLifter.setPower(0.5);
            rightBlockLifter.setPower(-0.5);
        } else if (!gamepad1.a && !gamepad1.b) {
            leftBlockLifter.setPower(0);
            rightBlockLifter.setPower(0);
        }

        if (gamepad1.left_bumper) {
            grabber.setPosition(blockGrabberOpen);
        } else if (gamepad1.right_bumper) {
            grabber.setPosition(blockGrabberClosed);
        }

        double tehSpeeds = 0.05;

        if (gamepad1.y) {
            towerGrabber.setPosition(towerGrabber.getPosition() + tehSpeeds);
        }

        if (gamepad1.x) {
            towerGrabber.setPosition(towerGrabber.getPosition() - tehSpeeds);
        }


        if (resetEncoder.getCurrent()) {
            leftTowerLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightTowerLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        if (toggleRunMode.getCurrent()) {
            leftTowerLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightTowerLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            telemetry.addLine("speed mode, with encoder");
        } else {
            leftTowerLifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightTowerLifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telemetry.addLine("power mode, without encoder");
        }

        if (gamepad1.dpad_up) {
            extender.setPower(-1.0);
        } else if (gamepad1.dpad_down) {
            extender.setPower(1.0);
        } else {
            extender.setPower(0);
        }

        if (gamepad1.b) {
            towerLifter.setPower(1.0d);
        } else if (gamepad1.a) {
            towerLifter.setPower(-1.0d);
        } else {
            towerLifter.setPower(0.0d);
        }

        mecanum.setPower(MecanumPower.fromGamepadXYTurn(
                sensitivity(gamepad1.right_stick_x, SENSITIVITY),
                sensitivity(-gamepad1.right_stick_y, SENSITIVITY),
                sensitivity(gamepad1.left_stick_x, SENSITIVITY)
        ));

        telemetry.addData("TowerLifter", towerLifter);

        telemetry_loop();
        telemetry.update();
    }

    public void move(DrivePower drivePower) {
        super.move(slowMode ? drivePower.scale(0.5) : drivePower);
    }
}