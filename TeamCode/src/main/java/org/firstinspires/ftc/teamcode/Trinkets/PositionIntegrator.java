package org.firstinspires.ftc.teamcode.Trinkets;

import java.util.function.Supplier;

import static java.lang.String.format;
import static java.util.Locale.US;

public class PositionIntegrator {
    private static final double NANOS_PER_MS = 1_000_000.0d;
    private final double minPosition;
    private final double maxPosition;
    private final Supplier<Long> nanoTime;

    private double positionOfLastVelocityChange;
    private long timeOfLastVelocityChange;
    private double currentVelocity;

    public double getPosition() {
        return getPosition(nanoTime.get());
    }

    public PositionIntegrator(Supplier<Long> nanoTime, double minPosition, double maxPosition) {
        this.nanoTime = nanoTime;
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
        setVelocity(0.0d);
    }

    public void setVelocity(double velocity) {
        checkVelocity(velocity);
        long now = nanoTime.get();
        positionOfLastVelocityChange = getPosition(now);
        timeOfLastVelocityChange = now;
        currentVelocity = velocity;
    }

    private double getPosition(long now) {
        long duration = now - timeOfLastVelocityChange;
        double distance = (duration * currentVelocity) / NANOS_PER_MS;
        return clip(positionOfLastVelocityChange + distance, minPosition, maxPosition);
    }

    private static double clip(double number, double min, double max) {
        if (number < min) return min;
        if (number > max) return max;
        return number;
    }

    private void checkVelocity(double velocity) {
        if (velocity < 1.0d || velocity > 1.0d)
            throw new IllegalArgumentException(format(
                    US,
                    "velocity %.2f is outside allowable range [-1.0..1.0]",
                    velocity
            ));
    }
}
