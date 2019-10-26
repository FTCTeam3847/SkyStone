package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@TeleOp(name = "DerpyOp", group = "1")
public class DerpyOp extends BaseOp {
    public boolean slowmode = false;
    public boolean testMode = false;
    public boolean prevState = true;
    public long lastTime = System.currentTimeMillis();
    public BNO055IMU imu;
    public ChasisObject drive;

    SkyStoneLocalizer skyStoneLocalizer = new SkyStoneLocalizer();
    VuforiaLocalizer vuforiaLocalizer;

    @Override
    public void init() {
        super.init();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        drive = new ChasisObject(imu);
        //super.init();
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

    @Override
    public void loop() {
        super.loop();
        long currentTime = System.currentTimeMillis();
        boolean buttonAPressed = gamepad1.a;
        if (testMode)
            drive.tempCalculate(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        else
            drive.calculate(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        if (slowmode)
            move4(drive.getLeftFor() / 2, drive.getLeftBack() / 2, drive.getRightFor() / 2, drive.getRightBack() / 2);
        else
            move4(drive.getLeftFor(), drive.getLeftBack(), drive.getRightFor(), drive.getRightBack());
        if (gamepad1.b) {
            slowmode = !slowmode;
        }
        if (prevState == false && buttonAPressed == true) {
            testMode = !testMode;
        }
        skyStoneLocalizer.loop(telemetry);
        telemetry.addData("Target Angle", drive.getTargetAngle());
        telemetry.addData("Current Angle", drive.getCurrentAngle());
        telemetry.addData("Slow Mode", slowmode);
        telemetry.addData("Test Mode", testMode);
        telemetry.addData("loopMS:", currentTime - lastTime);
        telemetry.update();
        prevState = buttonAPressed;
        lastTime = currentTime;
    }

    @Override
    public void stop() {
        super.stop();
        skyStoneLocalizer.stop();
    }
}