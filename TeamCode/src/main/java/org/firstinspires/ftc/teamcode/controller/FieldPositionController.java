package org.firstinspires.ftc.teamcode.controller;

import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;
import org.firstinspires.ftc.teamcode.polar.PolarUtil;

import java.util.Locale;
import java.util.function.Supplier;

import static java.lang.Math.min;
import static org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower.mecanumPower;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.addRadians;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtractRadians;

public class FieldPositionController implements Controller<FieldPosition, MecanumPower> {
    private final Supplier<FieldPosition> fieldPositionSupplier;
    private FieldPosition targetFieldPosition;
    private PolarCoord lastError = PolarCoord.ORIGIN;
    private MecanumPower lastControl = MecanumPower.ZERO;

    public FieldPositionController(Supplier<FieldPosition> fieldPositionSupplier) {
        this.fieldPositionSupplier = fieldPositionSupplier;
    }

    public void setTarget(FieldPosition targetFieldPosition) {
        this.targetFieldPosition = targetFieldPosition;
    }

    public FieldPosition getCurrent() {
        return fieldPositionSupplier.get();
    }

    public MecanumPower getControl() {
        FieldPosition currentFieldPosition = fieldPositionSupplier.get();

        PolarCoord error = getError();
        double mecanumTheta = subtractRadians(error.theta, currentFieldPosition.heading);
        double power = min(error.radius / 6, 1) / 2;

        lastControl = mecanumPower(power, mecanumTheta, 0);

        return lastControl;
    }

    public PolarCoord getError() {
        this.lastError = PolarUtil.subtract(getCurrent().polarCoord, targetFieldPosition.polarCoord);

        //tolerance
        if (lastError.radius <= 1) {
            return PolarCoord.ORIGIN;
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
