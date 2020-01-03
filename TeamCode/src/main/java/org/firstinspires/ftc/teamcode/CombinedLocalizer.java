package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.controller.HeadingLocalizer;
import org.firstinspires.ftc.teamcode.controller.Localizer;
import org.firstinspires.ftc.teamcode.drive.mecanum.LocalizingMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumLocalizer;

import java.lang.reflect.Field;
import java.util.Locale;

public class CombinedLocalizer implements Localizer<FieldPosition> {

    FieldPosition currentPosition;

    private final HeadingLocalizer headingLocalizer;
    private final MecanumLocalizer mecanumLocalizer;
    private final SkyStoneLocalizer skyStoneLocalizer;

    public CombinedLocalizer(HeadingLocalizer headingLocalizer, MecanumLocalizer mecanumLocalizer, SkyStoneLocalizer skyStoneLocalizer) {
        this.headingLocalizer = headingLocalizer;
        this.mecanumLocalizer = mecanumLocalizer;
        this.skyStoneLocalizer = skyStoneLocalizer;
    }

    @Override
    public FieldPosition getCurrent() {
        FieldPosition mecanumGuess = mecanumLocalizer.getCurrent();
        FieldPosition skyStoneGuess = skyStoneLocalizer.getCurrent();

        FieldPosition bestGuess;

//        if(skyStoneLocalizer.currentVisibleTarget != null)
//        {
//            calibrate(skyStoneGuess);
//            bestGuess = skyStoneGuess;
//        } else{
//            bestGuess = mecanumGuess;
//        }
        bestGuess = mecanumGuess;


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
