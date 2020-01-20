package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.controller.HeadingLocalizer;
import org.firstinspires.ftc.teamcode.controller.Localizer;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumLocalizer;

import java.util.Locale;

public class CombinedLocalizer implements Localizer<FieldPosition> {

    FieldPosition currentPosition;

    private final Localizer<Double> headingLocalizer;
    private final Localizer<FieldPosition> mecanumLocalizer;
    private final Localizer<FieldPosition> skyStoneLocalizer;

    public CombinedLocalizer(Localizer<Double> headingLocalizer, Localizer<FieldPosition> mecanumLocalizer, Localizer<FieldPosition> skyStoneLocalizer) {
        this.headingLocalizer = headingLocalizer;
        this.mecanumLocalizer = mecanumLocalizer;
        this.skyStoneLocalizer = skyStoneLocalizer;
    }

    @Override
    public FieldPosition getCurrent() {
        // must ask the headingLocalizer to get a fresh reading
        headingLocalizer.getCurrent();

        FieldPosition mecanumGuess = mecanumLocalizer.getCurrent();

        FieldPosition skyStoneGuess = skyStoneLocalizer.getCurrent();

        FieldPosition bestGuess;

        if (!skyStoneGuess.equals(FieldPosition.UNKNOWN)) {
            calibrate(skyStoneGuess);
            bestGuess = skyStoneGuess;
        } else {
            bestGuess = mecanumGuess;
        }

        currentPosition = bestGuess;

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
