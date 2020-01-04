package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.controller.HeadingLocalizer;
import org.firstinspires.ftc.teamcode.controller.Localizer;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumLocalizer;

import java.util.Locale;

public class CombinedLocalizer implements Localizer<FieldPosition> {

    FieldPosition currentPosition;

    private final HeadingLocalizer headingLocalizer;
    private final MecanumLocalizer mecanumLocalizer;
    private final SkyStoneLocalizer skyStoneLocalizer;

    private long loopCount = 0;

    public CombinedLocalizer(HeadingLocalizer headingLocalizer, MecanumLocalizer mecanumLocalizer, SkyStoneLocalizer skyStoneLocalizer) {
        this.headingLocalizer = headingLocalizer;
        this.mecanumLocalizer = mecanumLocalizer;
        this.skyStoneLocalizer = skyStoneLocalizer;
    }

    @Override
    public FieldPosition getCurrent() {
        // must ask the headingLocalizer to get a fresh reading
        headingLocalizer.getCurrent();

        FieldPosition mecanumGuess = mecanumLocalizer.getCurrent();

        // don't read from vuforia every time since it's CPU intensive
        FieldPosition skyStoneGuess = (loopCount % 10 == 0) ? FieldPosition.UNKNOWN : skyStoneLocalizer.getCurrent();

        FieldPosition bestGuess;

        if (!skyStoneGuess.equals(FieldPosition.UNKNOWN)) {
            calibrate(skyStoneGuess);
            bestGuess = skyStoneGuess;
        } else {
            bestGuess = mecanumGuess;
        }

        currentPosition = bestGuess;

        loopCount++;
        return bestGuess;
    }

    @Override
    public FieldPosition getLast() {
        return currentPosition;
    }

    @Override
    public void calibrate(FieldPosition fieldPosition) {
        headingLocalizer.calibrate(fieldPosition.heading);
        mecanumLocalizer.calibrate(fieldPosition);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s", currentPosition);
    }
}
