package org.firstinspires.ftc.teamcode.Trinkets;

import org.firstinspires.ftc.teamcode.controller.RunToPositionController;

import java.util.Locale;
import java.util.function.Consumer;

import static com.qualcomm.robotcore.util.Range.clip;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.String.format;

public class BlockLifter {
    private static final Consumer<Double> NOOP = unused -> {
    };
    public static final BlockLifter NIL = new BlockLifter(NOOP, NOOP);

    //Consumer takes a variable and returns a void
    Consumer<Double> leftPower;
    Consumer<Double> rightPower;

    private double power = 0.0d;
    private static final double MAX_POSITION = 2200.0d;
    private final PositionIntegrator position = new PositionIntegrator(System::nanoTime, 0.0d, MAX_POSITION);
    private RunToPositionController runToPositionController = new RunToPositionController(this::getPower, this::getPosition);

    public BlockLifter(Consumer<Double> leftPower,
                       Consumer<Double> rightPower
    ) {
        this.leftPower = leftPower;
        this.rightPower = rightPower;
    }

    public void loop() {
        if (isRunningPastLimits()) {
            stop();
        } else if (runToPositionController.isBusy()) {
            runAtPower(runToPositionController.getControl());
        }
    }

    // Position: [0.0..1.0] 0.0 is down, 1.0 is up
    public void setPosition(double targetPosition) {
        runToPositionController.setTarget(targetPosition);
    }

    public double getPosition() {
        return limit(0.0d, position.getPosition() / MAX_POSITION, 1.0d);
    }

    // Power: [-1.0..1.0] negative is down, positive is up
    public void setPower(double pwr) {
        // if the user starts controlling the power, then cancel the rtp controller
        runToPositionController.stop();
        runAtPower(pwr);
    }

    public double getPower() {
        return this.power;
    }

    public void stop() {
        runToPositionController.stop();
        runAtPower(0.0d);
    }

    private boolean isRunningPastLimits() {
        return power > 0 && position.getPosition() >= MAX_POSITION;
    }

    private void runAtPower(double power) {
        if (isRunningPastLimits()) {
            power = 0;
        } else {
            power = clip(power, -1.0d, 1.0d);
        }

        position.setVelocity(power);
        leftPower.accept(power);
        rightPower.accept(power);
        this.power = power;
    }

    private static double limit(double min, double n, double max) {
        return min(max(min, n), max);
    }

    @Override
    public String toString() {
        return format(
                Locale.US,
                "pwr:%.2f,pos:%.2f",
                power,
                getPosition()
        );
    }

}
