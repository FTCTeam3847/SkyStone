package org.firstinspires.ftc.teamcode;


import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

public class DerpyLocalizer {
    private VuforiaLocalizer vuforiaLocalizer;
    private List<VuforiaTrackable> allTrackables;
    private VuforiaTrackables targetsSkyStone;

    private OpenGLMatrix lastLocation = null;
    boolean targetVisible = false;

    private static final float mmPerInch = 25.4f;


    public void init(VuforiaLocalizer vuforiaLocalizer, List<VuforiaTrackable> allTrackables, VuforiaTrackables targetsSkyStone)
    {

        this.vuforiaLocalizer = vuforiaLocalizer;
        this.allTrackables = allTrackables;
        this.targetsSkyStone = targetsSkyStone;

        targetsSkyStone.activate();
    }

    public xyzrphLoc getLocation() {
        for (VuforiaTrackable trackable : allTrackables)
        {
            if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible())
            {
                targetVisible = true;
                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null)
                {
                    lastLocation = robotLocationTransform;
                }
                break;
            }
        }
        Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
        VectorF translation = lastLocation.getTranslation();

        return new xyzrphLoc(translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch, rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
    }

    public void deInit(VuforiaLocalizer vuforia)
    {
        vuforia.getCamera().close();
    }
}