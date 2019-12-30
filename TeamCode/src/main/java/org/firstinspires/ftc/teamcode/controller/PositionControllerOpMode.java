//package org.firstinspires.ftc.teamcode.controller;
//
//import com.qualcomm.hardware.bosch.BNO055IMU;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//
//import org.firstinspires.ftc.robotcore.external.ClassFactory;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
//import org.firstinspires.ftc.teamcode.BaseOp;
//import org.firstinspires.ftc.teamcode.GameConstants;
//import org.firstinspires.ftc.teamcode.Hardware.AngularPController;
//import org.firstinspires.ftc.teamcode.HardwareMapUtils;
//import org.firstinspires.ftc.teamcode.SkyStoneLocalizer;
//import org.firstinspires.ftc.teamcode.drive.DrivePower;
//import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumDriveController;
//import org.firstinspires.ftc.teamcode.polar.PolarCoord;
//import org.firstinspires.ftc.teamcode.polar.PolarUtil;
//
//@Autonomous
//public class PositionControllerOpMode extends BaseOp {
//    SkyStoneLocalizer skyStoneLocalizer = new SkyStoneLocalizer();
//    VuforiaLocalizer vuforiaLocalizer;
//    PositionController positionController;
//    public BNO055IMU imu;
//    public MecanumDriveController driverController;
//    public FieldPosition lastFieldPosition;
//
//    @Override
//    public void init() {
//        super.init();
//        imu = HardwareMapUtils.initImu(hardwareMap);
//        HeadingController headingController = new HeadingController(
//                () -> (double) imu.getAngularOrientation().firstAngle,
//                2.0d,
//                1.0d,
//                0.1d);
//        driverController = new MecanumDriveController(headingController);
//        int cameraMonitorViewId =
//                hardwareMap
//                        .appContext
//                        .getResources()
//                        .getIdentifier(
//                                "cameraMonitorViewId",
//                                "id",
//                                hardwareMap.appContext.getPackageName()
//                        );
//        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
//
//        parameters.vuforiaLicenseKey = GameConstants.VUFORIA_KEY;
//        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
//        parameters.useExtendedTracking = true;
//        parameters.useExtendedTracking = false;
//
//        this.vuforiaLocalizer = ClassFactory.getInstance().createVuforia(parameters);
//        skyStoneLocalizer.init(vuforiaLocalizer);
//        positionController = new PositionController(() -> skyStoneLocalizer.loop());
//
//    }
//
//    @Override
//    public void init_loop() {
//        super.init_loop();
//        FieldPosition fieldPosition = skyStoneLocalizer.loop();
//        FieldPosition targetFieldPosition = new FieldPosition(24, 24, 0, "");
//
//        positionController.setTargetLocation(targetFieldPosition);
//
//        PolarCoord strafe = positionController.loop();
//        //PolarCoord strafe = positionController.loop();
//        telemetry.addData("targetPos", targetFieldPosition);
//        telemetry.addData("fieldPos", fieldPosition);
//        telemetry.addData("strafe(bot)", strafe);
//        //telemetry.addData("strafe(bot)", strafe);
//
//        telemetry.update();
//    }
//    @Override
//    public void loop() {
//        super.loop();
//        FieldPosition fieldPosition = skyStoneLocalizer.loop();
//        FieldPosition targetFieldPosition = new FieldPosition(24, 24, 0, "");
//        FieldPosition targetFieldPosition = new FieldPosition(24, 48, 0, "");
//
//        positionController.setTargetLocation(targetFieldPosition);
//
//        telemetry.addData("targetPos", targetFieldPosition);
//        telemetry.addData("fieldPos", fieldPosition);
//        //clamps distance
//        if(Math.sqrt(Math.pow(targetFieldPosition.y-fieldPosition.y,2)+ Math.pow(targetFieldPosition.x-fieldPosition.x,2)) > 5) {
//
//            if (fieldPosition != fieldPosition.UNKNOWN) {
//                lastFieldPosition = fieldPosition;
//            }
//
//            positionController.setTargetLocation(targetFieldPosition);
//
//            PolarCoord strafe;
//            telemetry.addData("targetPos", targetFieldPosition);
//            telemetry.addData("fieldPos", fieldPosition);
//            telemetry.addData("lastfieldPos", lastFieldPosition);
//
//            PolarCoord strafe;
//
////        if (FieldPosition.UNKNOWN != fieldPosition) {
////            strafe = positionController.loop();
////        } else {
////            strafe = ;
////        }
//
//            if (FieldPosition.UNKNOWN != fieldPosition) {
//                strafe = positionController.loop();
//            } else {
//                strafe = PolarUtil.ORIGIN;
//            }
//
//            telemetry.addData("strafe(bot)", strafe);
//            DrivePower drivepower = driverController.update(strafe, 0).scale(0.5);
//            telemetry.addData("drivepower", drivepower);
//            move(drivepower);
//            if (!strafe.equals(PolarUtil.ORIGIN)) {
//                telemetry.addData("strafe(bot)", strafe);
//                DrivePower drivepower = driverController.update(strafe, 0).scale(0.5);
//                telemetry.addData("drivepower", drivepower);
//
//                telemetry.addData("numValues", positionController.getNumValues());
//                telemetry.addData("runningAverage", positionController.getRunningAverage());
//
//                move(drivepower);
//            }
//
//        }
//        telemetry.update();
//    }