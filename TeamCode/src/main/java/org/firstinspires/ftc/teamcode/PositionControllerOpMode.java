package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Hardware.AngularPController;

@Autonomous
public class PositionControllerOpMode extends BaseOp {

    SkyStoneLocalizer skyStoneLocalizer;
    VuforiaLocalizer vuforiaLocalizer;
    PositionController positionController;
    public BNO055IMU imu;
    public MecanumDriveController driverController;
    public FieldPosition lastFieldPosition;

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
        parameters.useExtendedTracking = false;

        this.vuforiaLocalizer = ClassFactory.getInstance().createVuforia(parameters);
        skyStoneLocalizer = new SkyStoneLocalizer(vuforiaLocalizer);
        positionController = new PositionController(() -> skyStoneLocalizer.getCurrent());

    }

    @Override
    public void init_loop() {
        super.init_loop();
        FieldPosition fieldPosition = skyStoneLocalizer.getCurrent();
        FieldPosition targetFieldPosition = new FieldPosition(24, 24, 0, "");

        positionController.setTarget(targetFieldPosition);

        //PolarCoord strafe = positionController.getLast();
        telemetry.addData("targetPos", targetFieldPosition);
        telemetry.addData("fieldPos", fieldPosition);
        //telemetry.addData("strafe(bot)", strafe);

        telemetry.update();
    }

    @Override
    public void loop() {
        super.loop();
        FieldPosition fieldPosition = skyStoneLocalizer.getCurrent();
        FieldPosition targetFieldPosition = new FieldPosition(24, 48, 0, "");


        //clamps distance
        if (Math.sqrt(Math.pow(targetFieldPosition.y - fieldPosition.y, 2) + Math.pow(targetFieldPosition.x - fieldPosition.x, 2)) > 5) {

            if (fieldPosition != FieldPosition.UNKNOWN) {
                lastFieldPosition = fieldPosition;
            }

            positionController.setTarget(targetFieldPosition);

            telemetry.addData("targetPos", targetFieldPosition);
            telemetry.addData("fieldPos", fieldPosition);
            telemetry.addData("lastfieldPos", lastFieldPosition);

            PolarCoord strafe;

//        if (FieldPosition.UNKNOWN != fieldPosition) {
//            strafe = positionController.getLast();
//        } else {
//            strafe = ;
//        }

            strafe = positionController.getControl();

            if (!strafe.equals(PolarUtil.ORIGIN)) {
                telemetry.addData("strafe(bot)", strafe);

                driverController.setTarget(new StrafeAndTurn(strafe, 0));
                DrivePower drivepower = driverController.getControl().scale(0.5);
                telemetry.addData("drivepower", drivepower);

                telemetry.addData("numValues", positionController.getNumValues());
                telemetry.addData("runningAverage", positionController.getRunningAverage());

                move(drivepower);
            }

        } else {
            stop(); //is supposed to make it stop when its closer than 5 inches, DOESN'T WORK!
        }
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
