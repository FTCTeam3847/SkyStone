package org.firstinspires.ftc.teamcode;

import java.util.function.Supplier;

import static org.firstinspires.ftc.teamcode.PolarUtil.fromTo;
import static org.firstinspires.ftc.teamcode.PolarUtil.fromXY;
import static org.firstinspires.ftc.teamcode.PolarUtil.subtractRadians;

public class PositionController {
    private final Supplier<FieldPosition> fieldPositionSupplier;
    private FieldPosition targetFieldPosition;

    public PositionController(Supplier<FieldPosition> fieldPositionSupplier) {
        this.fieldPositionSupplier = fieldPositionSupplier;
    }

    public void setTargetLocation(FieldPosition targetFieldPosition) {
        this.targetFieldPosition = targetFieldPosition;
    }

    public PolarCoord getTargetFieldRelative() {
        FieldPosition currentFieldPosition = fieldPositionSupplier.get();
        if (currentFieldPosition == null || targetFieldPosition == null) {
            return new PolarCoord(0, 0);
        }

        PolarCoord currentPolar = fromXY(currentFieldPosition.x, currentFieldPosition.y);
        PolarCoord targetPolar = fromXY(targetFieldPosition.x, targetFieldPosition.y);
        return fromTo(currentPolar, targetPolar);
    }

    public PolarCoord loop() {
        FieldPosition currentFieldPosition = fieldPositionSupplier.get();

        PolarCoord targetFieldRelative = getTargetFieldRelative();
        double power = Math.min(targetFieldRelative.radius/12, 1);

        PolarCoord strafe = new PolarCoord(power, subtractRadians(targetFieldRelative.theta, currentFieldPosition.h));

        return strafe;
    }
}
