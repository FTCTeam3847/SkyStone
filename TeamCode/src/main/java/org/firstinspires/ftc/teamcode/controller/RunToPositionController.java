package org.firstinspires.ftc.teamcode.controller;

import java.util.Locale;
import java.util.function.Supplier;

import static java.lang.Double.NaN;
import static java.lang.Double.isNaN;
import static java.lang.Math.signum;

public class RunToPositionController implements Controller<Double, Double> {
    private final Supplier<Double> power;
    private final Supplier<Double> position;

    private boolean isBusy = false;
    private double targetPosition = 0.0d;
    private double lastControl;

    public RunToPositionController(Supplier<Double> power, Supplier<Double> position) {
        this.power = power;
        this.position = position;
    }

    @Override
    public void setTarget(Double targetPosition) {
        this.targetPosition = targetPosition;
        this.isBusy = true;
    }

    // one of [-1.0, 0.0, 1.0]. This is not a proportional controller.
    @Override
    public Double getControl() {
        double pwr = power.get();
        double pos = position.get();

        boolean isDone = (pwr < 0.0d && pos <= targetPosition) || (pwr > 0.0d && pos >= targetPosition);

        if (isDone) stop();

        return lastControl = isDone ? 0.0d : signum(targetPosition - pos);
    }

    // if this controller hasn't reached its last requested target position yet, or has been stopped
    @Override
    public boolean isBusy() {
        return isBusy;
    }

    @Override
    public void stop() {
        isBusy = false;
    }

    @Override
    public String toString() {
        return String.format(
                Locale.US,
                "bsy:%b, tgt:%.2f, ctl:%.2f",
                isBusy,
                targetPosition,
                lastControl
        );
    }
}
