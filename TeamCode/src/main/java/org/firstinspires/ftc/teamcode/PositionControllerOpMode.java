package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Hardware.AngularPController;

import static org.firstinspires.ftc.teamcode.DrivePower.scale;

@Autonomous
public class PositionControllerOpMode extends BaseOp {

    SkyStoneLocalizer skyStoneLocalizer = new SkyStoneLocalizer();
    VuforiaLocalizer vuforiaLocalizer;
    PositionController positionController;
    public BNO055IMU imu;
    public MecanumDriveController driverController;

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
        positionController = new PositionController(() -> skyStoneLocalizer.loop());

    }

    @Override
    public void init_loop() {
        super.init_loop();
        FieldPosition fieldPosition = skyStoneLocalizer.loop();
        FieldPosition targetFieldPosition = new FieldPosition(24, 24, 0, "");

        positionController.setTargetLocation(targetFieldPosition);

        PolarCoord strafe = positionController.loop();
        telemetry.addData("targetPos", targetFieldPosition);
        telemetry.addData("fieldPos", fieldPosition);
        telemetry.addData("strafe(bot)", strafe);

        telemetry.update();
    }

    @Override
    public void loop() {
        super.loop();
        FieldPosition fieldPosition = skyStoneLocalizer.loop();
        FieldPosition targetFieldPosition = new FieldPosition(24, 24, 0, "");

        positionController.setTargetLocation(targetFieldPosition);

        PolarCoord strafe = positionController.loop();

        telemetry.addData("targetPos", targetFieldPosition);
        telemetry.addData("fieldPos", fieldPosition);
        telemetry.addData("strafe(bot)", strafe);

        telemetry.update();

        DrivePower drivepower = driverController.update(strafe, 0);
        move(scale(drivepower, 0.5));
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
}
