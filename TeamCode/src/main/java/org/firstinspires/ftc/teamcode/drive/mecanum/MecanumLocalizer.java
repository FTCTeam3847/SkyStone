package org.firstinspires.ftc.teamcode.drive.mecanum;

import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.controller.Localizer;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;
import org.firstinspires.ftc.teamcode.polar.PolarUtil;

import java.util.Locale;
import java.util.function.Supplier;

import static java.lang.Math.abs;
import static org.firstinspires.ftc.teamcode.polar.PolarCoord.polar;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.add;

public class MecanumLocalizer implements Localizer<FieldPosition> {
    private final Supplier<Long> nanoTime;
    private final Supplier<Double> heading;
    private final double maxVelocityInchesPerSecond;

    private FieldPosition positionOfLastUpdate = FieldPosition.ORIGIN;
    private long timeOfLastUpdate;
    private MecanumPower currentPower = MecanumPower.ZERO;
    private FieldPosition lastUpdated = FieldPosition.ORIGIN;

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
    }

    @Override
    public void calibrate(FieldPosition fieldPosition) {
        timeOfLastUpdate = nanoTime.get();
        positionOfLastUpdate = new FieldPosition(fieldPosition.polarCoord, heading.get(), FieldPosition.Fix.RELATIVE);
        lastUpdated = positionOfLastUpdate;
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

        double directionOfTravel = PolarUtil.addRadians(currentPower.strafe.theta, heading.get());
        double velocityOfTravel = currentPower.strafe.radius * maxVelocityInchesPerSecond;
        double radiusOfTravel = velocityOfTravel * duration / 1_000_000_000.0d;
        PolarCoord deltaPosition = polar(radiusOfTravel, directionOfTravel);

        PolarCoord netVector = add(deltaPosition, getLast().polarCoord);

        lastUpdated = new FieldPosition(netVector, heading.get(), FieldPosition.Fix.RELATIVE);
        timeOfLastUpdate = nanoTime;
        return lastUpdated;
    }

    private boolean isClose(double h1, double h2) {
        return abs(h2-h1) > 0.001;
    }

    public String toString () {
        return getLast().toString();
    }

}
