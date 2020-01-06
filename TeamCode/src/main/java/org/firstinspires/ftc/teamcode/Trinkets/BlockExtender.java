package org.firstinspires.ftc.teamcode.Trinkets;

import org.firstinspires.ftc.teamcode.controller.RunToPositionController;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.qualcomm.robotcore.util.Range.clip;
import static com.qualcomm.robotcore.util.Range.scale;
import static java.lang.String.format;

public class BlockExtender {
    private static final Consumer<Double> NOOP = unused -> {
    };
    private static final Supplier<Double> NOOPS = () -> 0.0d;
    public static final BlockExtender NIL = new BlockExtender(NOOP, NOOPS);

    //Consumer takes a variable and returns a void
    Consumer<Double> servoPower;
    Supplier<Double> servoPowerSupplier;
    double position = 0.0d;
    double power;
    long lastTime = 0L;
    double MAX_POSITION = 1720.0d;
    private RunToPositionController runToPositionController = new RunToPositionController(this::getPower, this::getPosition);

    public BlockExtender(Consumer<Double> servoPower, Supplier<Double> servoPowerSupplier) {
        this.servoPower = servoPower;
        this.servoPowerSupplier = servoPowerSupplier;
    }

    public boolean isRunningPastLimits() {
        return (this.power < 0.0d && getPosition() <= 0.0) || (this.power > 0.0d && getPosition() >= MAX_POSITION);
    }

    public void loop() {
        if (isRunningPastLimits()) {
            stop();
        } else if (runToPositionController.isBusy()) {
            runAtPower(runToPositionController.getControl());
        }
    }

    public void setPosition(double targetPosition) {
        runToPositionController.setTarget(targetPosition);
    }

    public void stop() {
        runToPositionController.stop();
        runAtPower(0.0d);
    }

    private void runAtPower(double power) {
        if (isRunningPastLimits()) {
            power = 0;
        } else {
            power = clip(power, -1.0d, 1.0d);
        }
        integratePosition();
        this.power = power;
        servoPower.accept(power);
    }

    private void integratePosition() {
        long now = System.nanoTime();
        long duration = now - lastTime;
        position = clip(position + (duration * getPower()) / 1_000_000.0d, 0.0d, MAX_POSITION);
        lastTime = now;
    }

    public void setPower(double power) {
        runAtPower(power);
    }

    public double getPower() {
        return servoPowerSupplier.get();
    }

    public double getPosition() {
        integratePosition();
        return scale(position, 0.0d, MAX_POSITION, 0.0d, 1.0d);
    }

    @Override
    public String toString() {
        return format(
                Locale.US,
                "pwr:%.2f,pos:%.2f,absPos:%.2f",
                getPower(),
                getPosition(),
                position
        );
    }

}
