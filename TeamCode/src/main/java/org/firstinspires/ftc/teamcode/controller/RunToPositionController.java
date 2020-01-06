package org.firstinspires.ftc.teamcode.controller;

import java.util.function.Supplier;

import static java.lang.Double.NaN;
import static java.lang.Double.isNaN;
import static java.lang.Math.signum;

public class RunToPositionController implements Controller<Double, Double> {
    private final Supplier<Double> power;
    private final Supplier<Double> position;
    private final double minPower;
    private final double maxPower;
    private final double gain;

    private boolean isBusy = false;
    private double target = 0.0d;

    public RunToPositionController(Supplier<Double> power, Supplier<Double> position, double minPower, double maxPower, double gain) {
        this.power = power;
        this.position = position;
        this.minPower = minPower;
        this.maxPower = maxPower;
        this.gain = gain;
    }

    @Override
    public void setTarget(Double target) {
        this.target = target;
        this.isBusy = true;
    }

    @Override
    public Double getControl() {
        double pwr = power.get();
        double pos = position.get();

        if (isRunningPastTargetPosition(pwr, pos, target)) {
            this.isBusy = false;
            return 0.0d;
        } else {
            double error = target - position.get();
            return calcControlValue(error, gain, minPower, maxPower);
        }
    }

    @Override
    public boolean isBusy() {
        return isBusy;
    }

    private static boolean isRunningPastTargetPosition(double pwr, double pos, double target) {
        return (pwr < 0.0d && pos <= target) || (pwr > 0.0d && pos >= target);
    }

    // copied this from the HeadingController
    private static double calcControlValue(double proportion, double gain, double min, double max) {
        if (isNaN(proportion)) return NaN;
        if (0.0f == proportion) return 0.0f;

        final double propGain = proportion * gain;

        if (propGain < -max) return -max;
        else if (propGain > max) return max;
        else if (propGain > -min && propGain < min) return min * signum(propGain);
        else return propGain;
    }

    @Override
    public void stop() {
        isBusy = false;
    }
}
