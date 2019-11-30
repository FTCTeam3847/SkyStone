package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Hardware.AngularPController;
import org.firstinspires.ftc.teamcode.gamepad.ToggleButton;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.signum;

@TeleOp(name = "DerpyOp", group = "1")
public class DerpyOp extends BaseOp {
    ToggleButton toggleButtonA = new ToggleButton(() -> gamepad1.a);
    ToggleButton toggleButtonB = new ToggleButton(() -> gamepad1.b);
    public long lastTime = System.currentTimeMillis();
    public BNO055IMU imu;
    public MecanumDriveController driverController;
    AngularPController headingController;

    SkyStoneLocalizer skyStoneLocalizer;
    VuforiaLocalizer vuforiaLocalizer;

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

        vuforiaLocalizer = ClassFactory.getInstance().createVuforia(parameters);
        skyStoneLocalizer = new SkyStoneLocalizer(vuforiaLocalizer);


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
        boolean slowMode = toggleButtonB.getCurrent();

        DrivePower drivePower;

        if (gamepad1.dpad_up)
            drivePower = driverController.update(0, 1, 0);
        else if (gamepad1.dpad_down)
            drivePower = driverController.update(0, -1, 0);
        else if (gamepad1.dpad_left)
            drivePower = driverController.update(-1, 0, 0);
        else if (gamepad1.dpad_right)
            drivePower = driverController.update(1, 0, 0);
        else
            drivePower = driverController.update(
                    sensitivity(gamepad1.right_stick_x, SENSITIVITY),
                    sensitivity(-gamepad1.right_stick_y, SENSITIVITY),
                    sensitivity(gamepad1.left_stick_x, SENSITIVITY)
            );


        drivePower = slowMode ? drivePower.scale(0.5) : drivePower;

        move(drivePower);

        FieldPosition fieldPosition = skyStoneLocalizer.getCurrent();
        telemetry.addData("fieldPosition", fieldPosition);
        telemetry.addData("h correct", -headingController.getControlValue());
        telemetry.addData("Slow Mode", slowMode);
        telemetry.addData("loopMS:", currentTime - lastTime);
        telemetry.update();
        lastTime = currentTime;
    }

    @Override
    public void stop() {
        super.stop();
        skyStoneLocalizer.stop();
    }
}