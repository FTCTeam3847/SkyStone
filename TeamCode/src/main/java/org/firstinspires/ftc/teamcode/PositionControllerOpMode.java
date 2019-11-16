package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
@Autonomous
public class PositionControllerOpMode extends OpMode {

    SkyStoneLocalizer skyStoneLocalizer = new SkyStoneLocalizer();
    VuforiaLocalizer vuforiaLocalizer;
    PositionController positionController;

    @Override
    public void init() {
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
        positionController = new PositionController( ()->skyStoneLocalizer.loop(telemetry) );

    }

    @Override
    public void loop() {
        skyStoneLocalizer.loop(telemetry);
        positionController.setTargetLocation(new LocationRotation(0, 0, 0));
        telemetry.addData("driveCommand: ", positionController.loop());
        telemetry.update();
    }
}
