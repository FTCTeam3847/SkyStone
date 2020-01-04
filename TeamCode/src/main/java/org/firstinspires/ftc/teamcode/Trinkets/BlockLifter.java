package org.firstinspires.ftc.teamcode.Trinkets;

import java.util.Locale;
import java.util.function.Consumer;

import static com.qualcomm.robotcore.util.Range.clip;
import static com.qualcomm.robotcore.util.Range.scale;
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

    public BlockLifter(Consumer<Double> leftPower,
                       Consumer<Double> rightPower
    ) {
        this.leftPower = leftPower;
        this.rightPower = rightPower;

    }

    public void setPower(double power) {
        power = clip(power, -1.0d, 1.0d);
        position.setVelocity(power);
        leftPower.accept(power);
        rightPower.accept(power);
        this.power = power;
    }

    public double getPower() {
        return this.power;
    }

    //Percentage
    public double getPosition() {
        return limit(0.0d, position.getPosition() / MAX_POSITION, 1.0d);
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
