package org.firstinspires.ftc.teamcode;

import java.util.function.Supplier;

public class PositionController {
    private final Supplier<LocationRotation> locationRotationSupplier;
    private LocationRotation targetLocationRotation;

    public PositionController(Supplier<LocationRotation> locationRotationSupplier) {

        this.locationRotationSupplier = locationRotationSupplier;
    }

    public void setTargetLocation(LocationRotation targetLocationRotation) {

        this.targetLocationRotation = targetLocationRotation;
    }

    public PolarCoord loop () {
        LocationRotation currentLocationRotation = locationRotationSupplier.get();
        if(currentLocationRotation == null || targetLocationRotation == null)
        {
            return new PolarCoord(0,0);
        }

        PolarCoord currentPolar = PolarCoord.fromXY(currentLocationRotation.getX(), currentLocationRotation.getY());
        PolarCoord targetPolar = PolarCoord.fromXY(targetLocationRotation.getX(), targetLocationRotation.getY());
        PolarCoord relativePolar = PolarUtil.fromTo(currentPolar, targetPolar);

        if(relativePolar.radius <= 12)
        {
            double power = relativePolar.radius/12;
            return new PolarCoord(power, relativePolar.theta);
        }
        else
        {
            return new PolarCoord(1, PolarUtil.subtractRadians(relativePolar.theta,currentLocationRotation.getH()));
        }

    }
}
