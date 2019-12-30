// left  0.30 up, 0.14 down
// right 0.30 up, 0.11 down

// leftPos  Start 0.60, End 0.15
// rightPos Start 0.25, End 0.70

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Trinkets.TowerLifter;
import org.firstinspires.ftc.teamcode.controller.HeadingController;
import org.firstinspires.ftc.teamcode.drive.DrivePower;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumDriveController;
import org.firstinspires.ftc.teamcode.gamepad.PushButton;
import org.firstinspires.ftc.teamcode.gamepad.ToggleButton;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.signum;


@TeleOp(name = "TrinketOpMode", group = "1")
public class TrinketOpMode extends BaseOp {
    public BNO055IMU imu;
    public MecanumDriveController driverController;
    HeadingController headingController;

    ToggleButton toggleRunMode = new ToggleButton(() -> gamepad1.right_stick_button);
    PushButton resetEncoder = new PushButton(() -> gamepad1.left_stick_button);
    ToggleButton toggleSlowMode = new ToggleButton(() -> gamepad1.back);
    TowerLifter towerLifter;


    double speedLeftUp = 0.30;
    double speedLeftDown = 0.14;
    double speedRightUp = 0.30;
    double speedRightDown = 0.11;

    double leftOpen = 0.60;
    double leftClosed = 0.05;
    double leftStart = leftOpen - ((leftOpen - leftClosed) / 2);
    double rightOpen = 0.25;
    double rightClosed = 0.80;
    double rightStart = rightOpen + ((rightClosed - rightOpen) / 2);

    double blockGrabberOpen = 0.8;
    double blockGrabberClosed = 0.5;

    private BNO055IMU initImu(BNO055IMU imu) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
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
        headingController = new HeadingController(
                () -> (double) imu.getAngularOrientation().firstAngle,
                0.0d,
                10.0d,
                0.0d);
        driverController = new MecanumDriveController(headingController);
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

        leftTowerGrabber.setPosition(leftStart);
        rightTowerGrabber.setPosition(rightStart);
    }

    @Override
    public void init_loop() {
        super.init_loop();
        telemetry.addLine("Initialized.");
        telemetry_loop();
        telemetry.update();
    }

    void telemetry_loop() {
        telemetry.addData("grabber", "l:%.2f, r:%.2f", leftTowerGrabber.getPosition(), rightTowerGrabber.getPosition());
    }

    private static double sensitivity(double base, double exp) {
        return signum(base) * pow(abs(base), exp);
    }

    private static final int SENSITIVITY = 3;


    @Override
    public void loop() {
        super.loop();
        boolean slowMode = toggleSlowMode.getCurrent();

        if (gamepad1.left_bumper) {
            leftBlockLifter.setPower(-0.5);
            rightBlockLifter.setPower(0.5);
        } else if (gamepad1.right_bumper) {
            leftBlockLifter.setPower(0.5);
            rightBlockLifter.setPower(-0.5);
        } else if (!gamepad1.a && !gamepad1.b) {
            leftBlockLifter.setPower(0);
            rightBlockLifter.setPower(0);
        }

        if (gamepad1.left_stick_button) {
            grabber.setPosition(blockGrabberOpen);
        } else if (gamepad1.right_stick_button) {
            grabber.setPosition(blockGrabberClosed);
        }

        double tehSpeeds = 0.005;

        if (gamepad2.x) {
            leftTowerGrabber.setPosition(leftTowerGrabber.getPosition() + tehSpeeds);
        } else if (gamepad2.y) {
            leftTowerGrabber.setPosition(leftTowerGrabber.getPosition() - tehSpeeds);
        }

        if (gamepad2.a) {
            rightTowerGrabber.setPosition(rightTowerGrabber.getPosition() - tehSpeeds);
        } else if (gamepad2.b) {
            rightTowerGrabber.setPosition(rightTowerGrabber.getPosition() + tehSpeeds);
        }

        if (gamepad1.x) {
            leftTowerGrabber.setPosition(leftTowerGrabber.getPosition() + tehSpeeds);
            rightTowerGrabber.setPosition(rightTowerGrabber.getPosition() - tehSpeeds);
        }

        if (gamepad1.y) {
            leftTowerGrabber.setPosition(leftTowerGrabber.getPosition() - tehSpeeds);
            rightTowerGrabber.setPosition(rightTowerGrabber.getPosition() + tehSpeeds);
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
            extender.setPower(-0.8);
        } else if (gamepad1.dpad_down) {
            extender.setPower(0.8);
        } else {
            extender.setPower(0);
        }

        if (gamepad1.a) {
            towerLifter.lift(speedLeftUp, speedRightUp);

            leftBlockLifter.setPower(0.5);
            rightBlockLifter.setPower(-0.5);

        } else if (gamepad1.b) {
            towerLifter.down(speedLeftDown, speedRightDown);

            leftBlockLifter.setPower(-0.5);
            rightBlockLifter.setPower(0.5);

        } else if (!gamepad1.left_bumper && !gamepad1.right_bumper) {
            towerLifter.stop();
            leftBlockLifter.setPower(0);
            rightBlockLifter.setPower(0);
        }

        driverController.setTarget(
                sensitivity(gamepad1.right_stick_x, SENSITIVITY),
                sensitivity(-gamepad1.right_stick_y, SENSITIVITY),
                sensitivity(gamepad1.left_stick_x, SENSITIVITY)
        );


        DrivePower drivePower;
        drivePower = driverController.getControl();

        drivePower = slowMode ? drivePower.scale(0.5) : drivePower;

        move(drivePower);


        telemetry_loop();
        telemetry.update();
    }
}
