package org.firstinspires.ftc.teamcode.controller;

import org.firstinspires.ftc.teamcode.polar.CartesianCoord;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;
import org.firstinspires.ftc.teamcode.polar.PolarUtil;

import java.util.Locale;
import java.util.Objects;

import static java.lang.Math.PI;
import static java.lang.Math.signum;
import static org.firstinspires.ftc.teamcode.controller.FieldPosition.Fix.ABSOLUTE;
import static org.firstinspires.ftc.teamcode.polar.PolarCoord.polar;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.fromCartesian;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtractRadians;

public class FieldPosition {
    public enum Fix {
        UNKNOWN, RELATIVE, ABSOLUTE;

        @Override
        public String toString() {
            return name();
        }
    }

    public static final FieldPosition ORIGIN =
            new FieldPosition(PolarCoord.ORIGIN, 0.0d);

    public static final FieldPosition UNKNOWN =
            new FieldPosition(PolarCoord.UNKNOWN, -0.0d, Fix.UNKNOWN) {
                @Override
                public String toString() {
                    return "UNKNOWN";
                }
            };

    public static FieldPosition fieldPosition(PolarCoord coord, double heading) {
        return new FieldPosition(coord, heading);
    }

    public static FieldPosition fieldPosition(CartesianCoord coord, double heading) {
        return fieldPosition(fromCartesian(coord), heading);
    }

    public static FieldPosition fieldPosition(double radius, double theta, double heading) {
        return fieldPosition(polar(radius, theta), heading);
    }

    public final PolarCoord polarCoord; //location relative to center of field, expressed as a polar coordinate
    public final double heading; //direction bot is facing
    public final Fix fix;

    public FieldPosition(PolarCoord polarCoord, double heading, Fix fix) {
        this.polarCoord = polarCoord;
        this.heading = heading;
        this.fix = fix;
    }

    public FieldPosition(PolarCoord polarCoord, double heading) {
        this(polarCoord, heading, ABSOLUTE);
    }

    public FieldPosition(CartesianCoord cartesianCoord, double heading) {
        this(fromCartesian(cartesianCoord), heading);
    }


    public FieldPosition subtract(FieldPosition targetFieldPosition)
    {
        double err = subtractRadians(targetFieldPosition.heading, this.heading);
        double heading = (1.0-Math.abs((err-PI)/PI))*signum(err-PI);

        return fieldPosition(PolarUtil.subtract(this.polarCoord, targetFieldPosition.polarCoord), heading);
    }

    public double distance(FieldPosition targetFieldPosition)
    {
        CartesianCoord thisCart = PolarUtil.toXY(this.polarCoord);
        CartesianCoord thatCart = PolarUtil.toXY(targetFieldPosition.polarCoord);

        return Math.sqrt(Math.pow((thatCart.y - thisCart.y),2) + Math.pow(thatCart.x-thisCart.x,2));
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
                "%s, h%.2f·π",
                polarCoord,
                heading / PI
        );
    }
}
