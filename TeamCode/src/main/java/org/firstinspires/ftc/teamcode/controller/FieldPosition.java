package org.firstinspires.ftc.teamcode.controller;

import org.firstinspires.ftc.teamcode.polar.CartesianCoord;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;
import org.firstinspires.ftc.teamcode.polar.PolarUtil;

import java.util.Locale;
import java.util.Objects;

import static java.lang.Math.PI;

public class FieldPosition {
    public static final FieldPosition ORIGIN =
            new FieldPosition(PolarCoord.ORIGIN, 0.0d);

    public static final FieldPosition UNKNOWN =
            new FieldPosition(PolarCoord.UNKNOWN, -0.0d) {
                @Override
                public String toString() {
                    return "UNKNOWN";
                }
            };

    public final PolarCoord polarCoord;
    public final double heading;

    public FieldPosition(PolarCoord polarCoord, double heading) {
        this.polarCoord = polarCoord;
        this.heading = heading;
    }

    public FieldPosition(CartesianCoord cartesianCoord, double heading) {
        this(PolarUtil.fromCartesian(cartesianCoord), heading);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldPosition that = (FieldPosition) o;
        return Double.compare(that.heading, heading) == 0 &&
                polarCoord.equals(that.polarCoord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(polarCoord, heading);
    }

    @Override
    public String toString() {
        return String.format(
                Locale.US,
                "{%s, h=%.2f·π}",
                polarCoord,
                heading / PI
        );
    }
}
