package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.controller.Localizer;

import java.util.Locale;
import java.util.function.Supplier;

public class CombinedLocalizer implements Localizer<FieldPosition> {

    FieldPosition currentPosition;

    private final Localizer<Double> headingLocalizer;
    private final Localizer<FieldPosition> mecanumLocalizer;
    private final Localizer<FieldPosition> skyStoneLocalizer;
    private final Supplier<Boolean> isBotInMotion;

    public CombinedLocalizer(Localizer<Double> headingLocalizer, Localizer<FieldPosition> mecanumLocalizer, Localizer<FieldPosition> skyStoneLocalizer, Supplier<Boolean> isBotInMotion) {
        this.headingLocalizer = headingLocalizer;
        this.mecanumLocalizer = mecanumLocalizer;
        this.skyStoneLocalizer = skyStoneLocalizer;
        this.isBotInMotion = isBotInMotion;
    }

    @Override
    public FieldPosition getCurrent() {
        // must ask the headingLocalizer to get a fresh reading
        headingLocalizer.getCurrent();

        FieldPosition mecanumGuess = mecanumLocalizer.getCurrent();

        FieldPosition bestGuess;

        if (!isBotInMotion.get()) { //determines if the bot is moving
            FieldPosition skyStoneGuess = skyStoneLocalizer.getCurrent();
            if(!skyStoneGuess.equals(FieldPosition.UNKNOWN))//determines if vuforia thinks it knows where the bot is
            {
                calibrate(skyStoneGuess);
                bestGuess = skyStoneGuess;
            }else {
                bestGuess = mecanumGuess;
            }
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
