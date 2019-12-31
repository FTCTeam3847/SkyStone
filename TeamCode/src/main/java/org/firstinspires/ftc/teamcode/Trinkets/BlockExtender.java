package org.firstinspires.ftc.teamcode.Trinkets;

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
    long lastTime = 0L;
    double MAX_POSITION = 3600.0d;

    public BlockExtender(Consumer<Double> servoPower, Supplier<Double> servoPowerSupplier) {
        this.servoPower = servoPower;
        this.servoPowerSupplier = servoPowerSupplier;
    }

    private void integratePosition() {
        long now = System.nanoTime();
        long duration = now - lastTime;
        position = clip(position + (duration * getPower()) / 1_000_000.0d, 0.0d, MAX_POSITION);
        lastTime = now;
    }

    public void setPower(double power) {
        power = clip(power, -1.0d, 1.0d);
        integratePosition();
        servoPower.accept(power);
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
                "pwr:%.2f,pos:%.2f",
                getPower(),
                getPosition()
        );
    }

}
