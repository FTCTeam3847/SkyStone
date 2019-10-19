package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "DerpyOp", group = "1")
public class DerpyOp extends BaseOp {
    public boolean slowmode = false;
    public boolean testMode = false;
    public BNO055IMU imu;
    public ChasisObject drive;

    @Override
    public void init() {
        super.init();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        drive = new ChasisObject(imu);
    }

    @Override
    public void loop() {
            if (testMode) drive.tempCalculate(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            else drive.calculate(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            if (slowmode)
                move4(drive.getLeftFor()/2, drive.getLeftBack()/2, drive.getRightFor()/2, drive.getRightBack()/2);
            else
                move4(drive.getLeftFor(), drive.getLeftBack(), drive.getRightFor(), drive.getRightBack());
        if (gamepad1.b) {
            slowmode = !slowmode;
        }
        if (gamepad1.a) {
            testMode = !testMode;
        }
        telemetry.addData("Target Angle", drive.getTargetAngle());
        telemetry.addData("Current Angle", drive.getCurrentAngle());
        telemetry.addData("Slow Mode", slowmode);
        telemetry.addData("Test Mode", testMode);
        telemetry.update();
    }
}