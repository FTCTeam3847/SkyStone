package org.firstinspires.ftc.teamcode.controller;

import java.util.Locale;
import java.util.function.Supplier;

import static java.lang.Math.PI;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtractRadians;

public class HeadingLocalizer implements Localizer<Double> {
    private double zero;
    private double lastAbsolute;

    private final Supplier<Double> absolute;
    private double lastUpdated;

    public HeadingLocalizer(Supplier<Double> absolute) {
        this.absolute = absolute;
        update();
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
        return update();
    }

    @Override
    public Double getLast() {
        return lastUpdated;
    }

    private double update() {
        readAbsolute();
        lastUpdated = subtractRadians(lastAbsolute, zero);
        return lastUpdated;
    }

    /**
     * Adjusts the localizer's current value based on a provided
     * reference value.
     *
     * @param reference angle in radians between [0..2·π]
     */
    @Override
    public void calibrate(Double reference) {
        if (isCalibrationLocked) return;
        internalCalibrate(reference);
    }

    private boolean isCalibrationLocked = false;

    public void lockCalibration(double reference) {
        isCalibrationLocked = false;
        internalCalibrate(reference);
    }

    public void unlockCalibration() {
        isCalibrationLocked = false;
    }

    private void internalCalibrate(Double reference) {
        checkAngleArgument(reference);
        this.zero = subtractRadians(readAbsolute(), reference);
        update();
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
                "%.2f·π, abs=%.2f·π, zero=%.2f·π",
                lastUpdated / PI,
                lastAbsolute / PI,
                zero / PI
        );
    }
}