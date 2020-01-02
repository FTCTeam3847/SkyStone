package org.firstinspires.ftc.teamcode.controller;

import java.util.Locale;
import java.util.function.Supplier;

import static java.lang.Math.PI;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtractRadians;

public class HeadingLocalizer implements Localizer<Double> {
    private double zero;
    private double lastAbsolute;

    private final Supplier<Double> absolute;
    private double current;

    public HeadingLocalizer(Supplier<Double> absolute) {
        this.absolute = absolute;
    }

    /**
     * Reads from the absolute angle provider, and returns the
     * calibration-adjusted current angle. This method must be
     * called in order for the localizer to update its state.
     * e.g. call from within a main loop().
     *
     * @return a value in radians within [0..2·π].
     * @see HeadingLocalizer#calibrate(Double)
     */
    @Override
    public Double getCurrent() {
        readAbsolute();
        current = subtractRadians(lastAbsolute, zero);
        return current;
    }

    /**
     * Adjusts the localizer's current value based on a provided
     * reference value.
     *
     * @param reference angle in radians between [0..2·π]
     */
    @Override
    public void calibrate(Double reference) {
        checkAngleArgument(reference);
        this.zero = subtractRadians(readAbsolute(), reference);
    }

    private double readAbsolute() {
        this.lastAbsolute = absolute.get();
        checkAngleArgument(this.lastAbsolute);
        return this.lastAbsolute;
    }

    private static void checkAngleArgument(double given) {
        if (given < 0 || given > 2 * PI)
            throw new IllegalArgumentException(
                    "Angle value must be between [0..2·π] radians!"
            );
    }

    @Override
    public String toString() {
        return String.format(Locale.US,
                "crnt=%.2f·π, abs=%.2f·π, zero=%.2f·π",
                current / PI,
                lastAbsolute / PI,
                zero / PI
        );
    }
}