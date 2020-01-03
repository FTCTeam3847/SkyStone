package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.controller.HeadingLocalizer;
import org.firstinspires.ftc.teamcode.controller.Localizer;
import org.firstinspires.ftc.teamcode.drive.mecanum.LocalizingMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumLocalizer;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;

import java.lang.reflect.Field;
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
        double headingGuess = headingLocalizer.getCurrent();
        FieldPosition mecanumGuess = mecanumLocalizer.getCurrent();
        FieldPosition skyStoneGuess = FieldPosition.UNKNOWN;
        if(loopCount % 10 == 0)
        {
            skyStoneGuess = skyStoneLocalizer.getCurrent();
        }
        FieldPosition bestGuess;

        if(!skyStoneGuess.equals(FieldPosition.UNKNOWN))
        {
            calibrate(skyStoneGuess);
            bestGuess = skyStoneGuess;
        } else{
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
        //eadingLocalizer.calibrate(fieldPosition.heading);
        mecanumLocalizer.calibrate(fieldPosition);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s", currentPosition);
    }
}
