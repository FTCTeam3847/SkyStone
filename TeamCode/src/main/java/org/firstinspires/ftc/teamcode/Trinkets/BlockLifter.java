package org.firstinspires.ftc.teamcode.Trinkets;

import java.util.Locale;
import java.util.function.Consumer;

import static com.qualcomm.robotcore.util.Range.clip;
import static com.qualcomm.robotcore.util.Range.scale;
import static java.lang.Math.max;
import static java.lang.String.format;

public class BlockLifter {
    private static final Consumer<Double> NOOP = unused -> {
    };
    public static final BlockLifter NIL = new BlockLifter(NOOP, NOOP);

    //Consumer takes a variable and returns a void
    Consumer<Double> leftPower;
    Consumer<Double> rightPower;

    double power = 0.0d;
    double position = 0.0d;
    long lastTime = 0L;
    double MAX_POSITION = 2200.0d;

    public BlockLifter(Consumer<Double> leftPower,
                       Consumer<Double> rightPower
    ) {
        this.leftPower = leftPower;
        this.rightPower = rightPower;

    }

    private void integratePosition() {
        long now = System.nanoTime();
        long duration = now - lastTime;
        position = max(0.0d, position + (duration * this.power) / 1_000_000.0d);
        lastTime = now;
    }

    public void setPower(double power) {
        power = clip(power, -1.0d, 1.0d);
        integratePosition();
        leftPower.accept(power);
        rightPower.accept(power);
        this.power = power;
    }

    public double getPower() {
        return this.power;
    }

    public double getPosition() {
        integratePosition();
        double position = clip(this.position, 0.0d, MAX_POSITION);
        return scale(position, 0.0d, MAX_POSITION, 0.0d, 1.0d);
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
