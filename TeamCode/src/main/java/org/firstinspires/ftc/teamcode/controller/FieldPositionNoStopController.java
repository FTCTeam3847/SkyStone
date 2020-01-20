package org.firstinspires.ftc.teamcode.controller;

import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;
import org.firstinspires.ftc.teamcode.polar.PolarUtil;

import java.util.Locale;
import java.util.function.Supplier;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower.mecanumPower;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtract;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtractRadians;

public class FieldPositionNoStopController implements Controller<FieldPosition, MecanumPower> {
    private final Supplier<FieldPosition> fieldPositionSupplier;
    private final double autonomousSpeed;
    private FieldPosition targetFieldPosition;
    private PolarCoord lastError = PolarCoord.ORIGIN;
    private MecanumPower lastControl = MecanumPower.ZERO;
    private FieldPosition startPosition;
    private PolarCoord startToTargetCoord;

    public FieldPositionNoStopController(Supplier<FieldPosition> fieldPositionSupplier, double autonomousSpeed) {
        this.fieldPositionSupplier = fieldPositionSupplier;
        this.autonomousSpeed = autonomousSpeed;
    }

    public void setTarget(FieldPosition targetFieldPosition) {
        this.targetFieldPosition = targetFieldPosition;
        this.startPosition = getCurrent();
        this.startToTargetCoord = subtract(targetFieldPosition.polarCoord, startPosition.polarCoord);
    }

    public FieldPosition getCurrent() {
        return fieldPositionSupplier.get();
    }

    public MecanumPower getControl() {
        PolarCoord error = getError();

        if (error.equals(PolarCoord.ORIGIN))
            return lastControl;

        FieldPosition currentFieldPosition = fieldPositionSupplier.get();
        double mecanumTheta = subtractRadians(error.theta, currentFieldPosition.heading);
        lastControl = mecanumPower(autonomousSpeed, mecanumTheta, 0);
        return lastControl;
    }

    public PolarCoord getError() {
        PolarCoord current = getCurrent().polarCoord;
        double startToCurrentRadius = subtract(current, startPosition.polarCoord).radius;
        double startToTargetRadius = startToTargetCoord.radius;

        if (startToCurrentRadius >= startToTargetRadius) {
            this.lastError = PolarCoord.ORIGIN;
        } else {
            this.lastError = subtract(current, targetFieldPosition.polarCoord);
        }

        return lastError;
    }

    @Override
    public String toString() {
        return String.format(Locale.US,
                "tgt=%s, err=%s, ctrl=%s",
                targetFieldPosition,
                lastError,
                lastControl
        );
    }

}
