package org.firstinspires.ftc.teamcode;

import java.util.function.Supplier;

import static org.firstinspires.ftc.teamcode.PolarUtil.fromTo;
import static org.firstinspires.ftc.teamcode.PolarUtil.fromXY;
import static org.firstinspires.ftc.teamcode.PolarUtil.subtractRadians;

public class PositionController {
    private final Supplier<LocationRotation> locationRotationSupplier;
    private LocationRotation targetLocationRotation;

    public PositionController(Supplier<LocationRotation> locationRotationSupplier) {
        this.locationRotationSupplier = locationRotationSupplier;
    }

    public void setTargetLocation(LocationRotation targetLocationRotation) {
        this.targetLocationRotation = targetLocationRotation;
    }

    public PolarCoord loop() {
        LocationRotation currentLocationRotation = locationRotationSupplier.get();
        if (currentLocationRotation == null || targetLocationRotation == null) {
            return new PolarCoord(0, 0);
        }

        PolarCoord currentPolar = fromXY(currentLocationRotation.x, currentLocationRotation.y);
        PolarCoord targetPolar = fromXY(targetLocationRotation.x, targetLocationRotation.y);
        PolarCoord relativePolar = fromTo(currentPolar, targetPolar);

        if (relativePolar.radius <= 12) {
            double power = relativePolar.radius / 12;
            return new PolarCoord(power, relativePolar.theta);
        } else {
            return new PolarCoord(1, subtractRadians(relativePolar.theta, currentLocationRotation.h));
        }

    }
}
