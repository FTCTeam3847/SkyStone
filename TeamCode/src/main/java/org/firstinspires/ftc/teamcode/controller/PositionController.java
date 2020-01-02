package org.firstinspires.ftc.teamcode.controller;

import org.firstinspires.ftc.teamcode.polar.PolarCoord;
import org.firstinspires.ftc.teamcode.polar.PolarUtil;

import java.util.function.Supplier;

import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtract;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtractRadians;

public class PositionController implements Controller<FieldPosition, PolarCoord> {
    private final Supplier<FieldPosition> fieldPositionSupplier;
    private FieldPosition targetFieldPosition;
    private PolarCoord runningAverage;
    private int numValues;


    public PolarCoord getRunningAverage() {
        return runningAverage;
    }

    public int getNumValues() {
        return numValues;
    }


    public PositionController(Supplier<FieldPosition> fieldPositionSupplier) {
        this.fieldPositionSupplier = fieldPositionSupplier;

        //resets runningAverage
        numValues = 0;
        runningAverage = PolarUtil.ORIGIN;
    }

    public void setTarget(FieldPosition targetFieldPosition) {
        this.targetFieldPosition = targetFieldPosition;
    }

    public PolarCoord getCurrent() {
        FieldPosition currentFieldPosition = fieldPositionSupplier.get();
        if (currentFieldPosition == null || targetFieldPosition == null) {
            return runningAverage;
        }

        return subtract(currentFieldPosition.polarCoord, targetFieldPosition.polarCoord);
    }

    public PolarCoord getControl() {
        FieldPosition currentFieldPosition = fieldPositionSupplier.get();

        if (currentFieldPosition == FieldPosition.UNKNOWN) {
            return runningAverage;
        }

        PolarCoord targetFieldRelative = getCurrent();
        double power = Math.min(targetFieldRelative.radius / 12, 1);

        PolarCoord strafe = new PolarCoord(power, subtractRadians(targetFieldRelative.theta, currentFieldPosition.heading));

        if (runningAverage == PolarUtil.ORIGIN) {
            runningAverage = new PolarCoord(strafe.radius, strafe.theta);
        }


        numValues++;
        //double avgRadius = (runningAverage.radius * (numValues - 1) / (numValues)) + (strafe.radius * (1 / (numValues)));
        //double avgTheta = (runningAverage.theta * (numValues - 1) / (numValues)) + (strafe.theta * (1 / (numValues)));

        //double avgRadius = (runningAverage.radius * (numValues-1) + strafe.radius) / numValues; //generates an average strafe value based on all of its past information
        double avgTheta = (runningAverage.theta * (numValues - 1) + strafe.theta) / numValues;


        runningAverage = new PolarCoord(strafe.radius, avgTheta);

        return runningAverage;
    }
}
