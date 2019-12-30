package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.controller.HeadingController;
import org.firstinspires.ftc.teamcode.drive.DrivePower;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumDriveController;
import org.firstinspires.ftc.teamcode.gamepad.ToggleButton;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.signum;
import static org.firstinspires.ftc.teamcode.HardwareMapUtils.initImu;
import static org.firstinspires.ftc.teamcode.HardwareMapUtils.initVuforia;

@TeleOp(name = "SkottOp", group = "1")
public class SkottOp extends BaseOp {
    ToggleButton toggleButtonA = new ToggleButton(() -> gamepad1.a);
    ToggleButton toggleButtonB = new ToggleButton(() -> gamepad1.b);
    public long lastTime = System.currentTimeMillis();
    public BNO055IMU imu;
    public MecanumDriveController driverController;
    HeadingController headingController;

    SkyStoneLocalizer skyStoneLocalizer;
    VuforiaLocalizer vuforiaLocalizer;


    int targetPosition = 0;

    @Override
    public void init() {
        telemetry.addLine("Initializing...");
        telemetry.update();

        super.init();
        imu = initImu(hardwareMap);
        headingController = new HeadingController(
                () -> (double) imu.getAngularOrientation().firstAngle,
                0.0d,
                10.0d,
                0.0d);
        driverController = new MecanumDriveController(headingController);

        vuforiaLocalizer = initVuforia(hardwareMap);
        skyStoneLocalizer = new SkyStoneLocalizer(vuforiaLocalizer);
    }

    @Override
    public void init_loop() {
        super.init_loop();
        telemetry.addLine("Initialized.");
        telemetry.update();
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

        if (gamepad1.dpad_up) {
            driverController.setTarget(0, 1, 0);
        } else if (gamepad1.dpad_down) {
            driverController.setTarget(0, -1, 0);
        } else if (gamepad1.dpad_left) {
            driverController.setTarget(-1, 0, 0);
        } else if (gamepad1.dpad_right) {
            driverController.setTarget(1, 0, 0);
        } else {
            driverController.setTarget(
                    sensitivity(gamepad1.right_stick_x, SENSITIVITY),
                    sensitivity(-gamepad1.right_stick_y, SENSITIVITY),
                    sensitivity(gamepad1.left_stick_x, SENSITIVITY)
            );
        }
        drivePower = driverController.getControl();

//
//        if(gamepad1.right_bumper)
//        {
////            leftTowerLifter.setTargetPosition(targetPosition += 1);
////            rightTowerLifter.setTargetPosition(targetPosition += 1);
////
////            leftTowerLifter.setPower(.1);
////            rightTowerLifter.setPower(.1);
//
//            extender.setPosition();
//
//        }
//        else if(gamepad1.left_bumper)
//        {
////            leftTowerLifter.setTargetPosition(targetPosition -= 1);
////            rightTowerLifter.setTargetPosition(targetPosition -= 1);
////
////            leftTowerLifter.setPower(-.1);
////            rightTowerLifter.setPower(-.1);
//
//
//        } else {
//            leftTowerLifter.setPower(0);
//            rightTowerLifter.setPower(0);
//        }
//
//        telemetry.addData("left motor position", leftTowerLifter.getCurrentPosition());
//        telemetry.addData("right motor position", rightTowerLifter.getCurrentPosition());
//
//        telemetry.addData("blockExtender position", extender.getPosition());


        drivePower = slowMode ? drivePower.scale(0.5) : drivePower;

        move(drivePower);

        telemetry.addData("fieldPosition", skyStoneLocalizer.getCurrent());
        telemetry.addData("h correct", -headingController.getControl());
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