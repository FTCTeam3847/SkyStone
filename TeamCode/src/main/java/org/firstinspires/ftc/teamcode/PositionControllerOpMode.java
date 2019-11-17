package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Hardware.AngularPController;

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
        positionController = new PositionController(() -> skyStoneLocalizer.loop(telemetry));

    }

    @Override
    public void init_loop() {
        super.init_loop();
        skyStoneLocalizer.loop(telemetry);

        positionController.setTargetLocation(new LocationRotation(0, 0, 0));

        PolarCoord driveCommand = positionController.loop();

        telemetry.addData("driveCommand: ", driveCommand);

        double x = driveCommand.radius * Math.cos(PolarUtil.subtractRadians(driveCommand.theta, Math.PI));
        double y = (driveCommand.radius * Math.sin(PolarUtil.subtractRadians(driveCommand.theta, Math.PI)));

        telemetry.addData("X Value: ", x);
        telemetry.addData("Y Value: ", y);

        telemetry.update();


    }

    @Override
    public void loop() {
        super.loop();
        PolarCoord driveCommand = positionController.loop();
        skyStoneLocalizer.loop(telemetry);

        double x = driveCommand.radius * Math.cos(PolarUtil.subtractRadians(driveCommand.theta, Math.PI));
        double y = (driveCommand.radius * Math.sin(PolarUtil.subtractRadians(driveCommand.theta, Math.PI)));

        telemetry.addData("X Value: ", x);
        telemetry.addData("Y Value: ", y);

        DrivePower drivePower = driverController.update(x, y, 0);

        move4(drivePower.leftFor, drivePower.leftBack, drivePower.rightFor, drivePower.rightBack);


        telemetry.addData("driveCommand: ", driveCommand);
        telemetry.update();
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
