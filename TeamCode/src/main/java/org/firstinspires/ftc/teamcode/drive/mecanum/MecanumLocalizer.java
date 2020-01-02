package org.firstinspires.ftc.teamcode.drive.mecanum;

import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.controller.Localizer;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;

import java.util.function.Supplier;

import static java.lang.Math.abs;

public class MecanumLocalizer implements Localizer<FieldPosition> {
    private final Supplier<Long> nanoTime;
    private final Supplier<Double> heading;
    private final double maxVelocityInchesPerSecond;

    private FieldPosition positionOfLastUpdate = FieldPosition.ORIGIN;
    private long timeOfLastUpdate;
    private MecanumPower currentPower;
    private FieldPosition lastUpdated;
    private double currentHeading;

    public MecanumLocalizer(Supplier<Long> nanoTime, Supplier<Double> heading, double maxVelocityInchesPerSecond) {
        this.nanoTime = nanoTime;
        this.heading = heading;
        this.maxVelocityInchesPerSecond = maxVelocityInchesPerSecond;
    }

    public void setPower(MecanumPower mecanumPower) {
        double heading = this.heading.get();
        if (mecanumPower.equals(currentPower) && isClose(positionOfLastUpdate.heading, heading) ) return;

        long now = nanoTime.get();
        positionOfLastUpdate = update(now);
        currentPower = mecanumPower;
        currentHeading = heading;
    }

    @Override
    public void calibrate(FieldPosition fieldPosition) {
        timeOfLastUpdate = nanoTime.get();
        positionOfLastUpdate = new FieldPosition(fieldPosition.polarCoord, heading.get());
    }

    @Override
    public FieldPosition getCurrent() {
        return update(nanoTime.get());
    }

    @Override
    public FieldPosition getLast() {
        return lastUpdated;
    }

    private FieldPosition update(Long nanoTime) {
        long duration = nanoTime - timeOfLastUpdate;

        // TODO use math here, and return a real updated FieldPosition

        timeOfLastUpdate = nanoTime;
        lastUpdated = new FieldPosition(PolarCoord.ORIGIN, currentHeading);
        return lastUpdated;
    }

    private boolean isClose(double h1, double h2) {
        return abs(h2-h1) > 0.001;
    }

}
