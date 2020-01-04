package org.firstinspires.ftc.teamcode.polar;

import java.util.Locale;
import java.util.Objects;

import static java.lang.Math.PI;
import static java.lang.String.format;

public class PolarCoord {
    public static final PolarCoord ORIGIN = new PolarCoord(0.0d, 0.0d);
    public static final PolarCoord UNKNOWN = new PolarCoord(-0.0d, -0.0d) {
        @Override
        public String toString() {
            return "UNKNOWN";
        }
    };

    public final double theta;
    public final double radius;

    public PolarCoord(double radius, double theta) {
        this.radius = radius;
        this.theta = theta;
    }

    public static PolarCoord polar(double radius, double theta) {
        return new PolarCoord(radius, theta);
    }

    @Override
    public String toString() {
        return format(Locale.US, "r%.1f,θ%.2f·π,%s", radius, theta / PI, toXY());
    }

    private CartesianCoord toXY() {
        return PolarUtil.toXY(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolarCoord that = (PolarCoord) o;
        return Double.compare(that.theta, theta) == 0 &&
                Double.compare(that.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(theta, radius);
    }
}
