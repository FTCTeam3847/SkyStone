package org.firstinspires.ftc.teamcode.Trinkets;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.lang.Math.*;
import static java.lang.String.format;

public class TowerLifter {
    public static final TowerLifter NIL = new TowerLifter((x) -> {},(x) -> {},()->0,()->0);

    private final Consumer<Double> leftPower;
    private final Consumer<Double> rightPower;
    private final Supplier<Integer> leftPosition;
    private final Supplier<Integer> rightPosition;

    private static final double MAX_LEFT_UP = 0.30;
    private static final double MAX_RIGHT_UP = 0.30;
    private static final double MAX_LEFT_DOWN = 0.14;
    private static final double MAX_RIGHT_DOWN = 0.11;
    private static final double MAX_POSITION = 3000.0d;
    private double power = 0.0d;

    public TowerLifter(Consumer<Double> leftPower,
                       Consumer<Double> rightPower,
                       Supplier<Integer> leftPosition,
                       Supplier<Integer> rightPosition) {

        this.leftPower = leftPower;
        this.rightPower = rightPower;
        this.leftPosition = leftPosition;
        this.rightPosition = rightPosition;
    }

    public double getPower() {
        return power;
    }

    // [-1.0..1.0] negative is down, positive is up
    public void setPower(double pwr) {
        power = limit(-1.0d, pwr, 1.0d);
        leftPower.accept(leftPwr(power));
        rightPower.accept(rightPwr(power));
    }

    // [0.0..1.0] 0.0 is down, 1.0 is up
    public double getPosition() {
        return limit(0.0d, leftPosition.get() / MAX_POSITION, 1.0d);
    }

    private static double limit(double min, double n, double max) {
        return min(max(min, n), max);
    }

    private static double leftPwr(double pwr) {
        return pwr * ((pwr >= 0.0) ? MAX_LEFT_UP : MAX_LEFT_DOWN);
    }

    private static double rightPwr(double pwr) {
        return pwr * ((pwr >= 0.0) ? MAX_RIGHT_UP : MAX_RIGHT_DOWN);
    }

    @Override
    public String toString() {
        return format(
                Locale.ENGLISH,
                "pwr:%.2f,pos:%.2f L%.2f,%d R%.2f,%d",
                getPower(), getPosition(),
                leftPwr(power), leftPosition.get(),
                rightPwr(power), rightPosition.get()
        );
    }
}
