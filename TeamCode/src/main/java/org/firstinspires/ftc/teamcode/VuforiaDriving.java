package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@Autonomous(name = "Vuforia Driving", group = "1")
public class VuforiaDriving extends OpMode {
    SkyStoneLocalizer skyStoneLocalizer = new SkyStoneLocalizer();
    VuforiaLocalizer vuforiaLocalizer;


    @Override
    public void init() {
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
    public void init_loop() {
        super.init_loop();
    }

    @Override
    public void loop() {
        //super.loop();
        LocationRotation locrot = skyStoneLocalizer.loop(telemetry);
        telemetry.update();

    }

    public boolean driveTo(LocationRotation current, LocationRotation destination) {
        if (Math.abs(destination.x - current.x) < 10 && Math.abs(destination.y - current.y) > 10) {
            return true;
        }
        double driveAngle = Math.atan2(destination.y - current.y, destination.x - current.x);
        return false;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        skyStoneLocalizer.stop();
    }
}
