package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Hardware.AngularPController;
import org.firstinspires.ftc.teamcode.gamepad.ToggleButton;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.signum;

@TeleOp(name = "DerpyOp", group = "1")
public class DerpyOp extends BaseOp {
    ToggleButton slowMode = new ToggleButton(() -> gamepad1.y);
    public long lastTime = System.currentTimeMillis();
    public BNO055IMU imu;
    public MecanumDriveController driverController;
    AngularPController headingController;

    SkyStoneLocalizer skyStoneLocalizer;
    VuforiaLocalizer vuforiaLocalizer;
    public static final double TOWER_GRABBER_SPEED = 0.05;
    public static final double BLOCK_GRABBER_SPEED = 0.05;

    @Override
    public void init() {
        super.init();
        imu = initImu(hardwareMap.get(BNO055IMU.class, "imu"));
        headingController = new AngularPController(
                () -> (double) imu.getAngularOrientation().firstAngle,
                0.0d,
                10.0d,
                0.0d);
        driverController = new MecanumDriveController(headingController);

        int cameraMonitorViewId =
                hardwareMap
                        .appContext
                        .getResources()
                        .getIdentifier(
                                "cameraMonitorViewId",
                                "id",
                                hardwareMap.appContext.getPackageName()
                        );
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);


        parameters.vuforiaLicenseKey = GameConstants.VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        parameters.useExtendedTracking = false; //Disables extended tracking on vuforia

//        vuforiaLocalizer = ClassFactory.getInstance().createVuforia(parameters);
//        skyStoneLocalizer = new SkyStoneLocalizer(vuforiaLocalizer);


    }

    private BNO055IMU initImu(BNO055IMU imu) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        imu.initialize(parameters);
        return imu;
    }

    private static double sensitivity(double base, double exp) {
        return signum(base) * pow(abs(base), exp);
    }

    private static final int SENSITIVITY = 3;

    @Override
    public void loop() {
        super.loop();
        long currentTime = System.currentTimeMillis();
        boolean slowMode = this.slowMode.getCurrent();

        DrivePower drivePower;

        driverController.setTarget(
                sensitivity(gamepad1.right_stick_x, SENSITIVITY),
                sensitivity(-gamepad1.right_stick_y, SENSITIVITY),
                sensitivity(gamepad1.left_stick_x, SENSITIVITY)
        );
        drivePower = driverController.getControl();
        drivePower = slowMode ? drivePower.scale(0.5) : drivePower;
        move(drivePower);

        // DRAFT tower grabber
        if(gamepad1.a) {
            // OPEN
            leftGrabber.setPosition(min(leftGrabber.getPosition() + TOWER_GRABBER_SPEED, 0.58));
            rightGrabber.setPosition(max(rightGrabber.getPosition() - TOWER_GRABBER_SPEED, 0.16));
        }else if(gamepad1.b) {
            // CLOSE
            leftGrabber.setPosition(max(leftGrabber.getPosition() - TOWER_GRABBER_SPEED, 0.13));
            rightGrabber.setPosition(min(rightGrabber.getPosition() + TOWER_GRABBER_SPEED, 0.66));
        }

        // DRAFT tower lifter
        if (gamepad1.y) {
            // UP
            leftGrabberLifter.setPower(-0.25);
            rightGrabberLifter.setPower(-0.25);
        } else if (gamepad1.x) {
            // DOWN
            leftGrabberLifter.setPower(0.25);
            rightGrabberLifter.setPower(0.25);
        } else {
            // DOWN
            leftGrabberLifter.setPower(0);
            rightGrabberLifter.setPower(0);
        }

        // DRAFT slider lifter
        //   - rightSliderLifter is setup as CS and direction is correct
        //   - leftSliderLifter servo IS NOT set for CR, can't dial it in yet
        if (gamepad1.right_trigger != 0.0) {
            // UP
            // leftSliderLifter.setPower(0.25);
            rightSliderLifter.setPower(-0.25);
        } else if (gamepad1.left_trigger != 0.0) {
            // DOWN
            // leftSliderLifter.setPower(-0.25);
            rightSliderLifter.setPower(0.25);
        } else {
            leftSliderLifter.setPower(0.0);
            rightSliderLifter.setPower(0.0);
        }


        // DRAFT block grabber
        if (gamepad1.right_bumper) {
            //CLOSE 0.35
            blockGrabber.setPosition(max(blockGrabber.getPosition() - BLOCK_GRABBER_SPEED, 0.35));

        } else if (gamepad1.left_bumper) {
            // OPEN 0.82
            blockGrabber.setPosition(min(blockGrabber.getPosition() + BLOCK_GRABBER_SPEED, 0.82));
        }

        // DRAFT block slider
        if (gamepad1.dpad_up) {
            // FORWARD
            slider.setPower(-1.0);
        } else if (gamepad1.dpad_down) {
            // BACKWARD
            slider.setPower(1.0);
        } else {
            slider.setPower(0.0);
        }

//        FieldPosition fieldPosition = skyStoneLocalizer.getCurrent();
//        telemetry.addData("fieldPosition", fieldPosition);
        telemetry.addData("h correct", -headingController.getControl());
        telemetry.addData("Slow Mode", slowMode);
        telemetry.addData("loopMS:", currentTime - lastTime);
        telemetry.update();
        lastTime = currentTime;
    }

    @Override
    public void stop() {
        super.stop();
//        skyStoneLocalizer.stop();
    }
}