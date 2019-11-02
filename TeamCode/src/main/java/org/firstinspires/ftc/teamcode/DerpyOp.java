package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Hardware.AngularPController;
import org.firstinspires.ftc.teamcode.gamepad.ToggleButton;

@TeleOp(name = "DerpyOp", group = "1")
public class DerpyOp extends BaseOp {
    ToggleButton toggleButtonA = new ToggleButton(() -> gamepad1.a);
    ToggleButton toggleButtonB = new ToggleButton(() -> gamepad1.b);
    public long lastTime = System.currentTimeMillis();
    public BNO055IMU imu;
    public MecanumDriveController driverController;

    SkyStoneLocalizer skyStoneLocalizer = new SkyStoneLocalizer();
    VuforiaLocalizer vuforiaLocalizer;

    @Override
    public void init() {
        super.init();
        imu = initImu(hardwareMap.get(BNO055IMU.class, "imu"));
        AngularPController headingController = new AngularPController(
                () -> (double) imu.getAngularOrientation().firstAngle,
                2.0d,
                1.0d,
                0.1d);
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

        this.vuforiaLocalizer = ClassFactory.getInstance().createVuforia(parameters);
        skyStoneLocalizer.init(vuforiaLocalizer);


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

    @Override
    public void loop() {
        super.loop();
        long currentTime = System.currentTimeMillis();
        boolean slowMode = toggleButtonB.get();

        DrivePower drivePower;

        if (gamepad1.dpad_up)
            drivePower = driverController.update(0, -1, 0);
        else if (gamepad1.dpad_down)
            drivePower = driverController.update(0, 1, 0);
        else if (gamepad1.dpad_left)
            drivePower = driverController.update(-1, 0, 0);
        else if (gamepad1.dpad_right)
            drivePower = driverController.update(1, 0, 0);
        else
            drivePower = driverController.update(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

        if (slowMode)
            move4(
                    drivePower.leftFor / 2,
                    drivePower.leftBack / 2,
                    drivePower.rightFor / 2,
                    drivePower.rightBack / 2
            );
        else
            move4(drivePower.leftFor, drivePower.leftBack, drivePower.rightFor, drivePower.rightBack);

        skyStoneLocalizer.loop(telemetry);
        telemetry.addData("Target Angle", driverController.getTargetAngle());
        telemetry.addData("Current Angle", driverController.getCurrentAngle());
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