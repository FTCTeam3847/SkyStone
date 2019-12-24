package org.firstinspires.ftc.teamcode.polar;

import java.util.Objects;

import static java.lang.Math.PI;
import static java.lang.String.format;

public class PolarCoord {
    public final double theta;
    public final double radius;

    public PolarCoord(double radius, double theta) {
        this.radius = radius;
        this.theta = theta;
    }

    @Override
    public String toString() {
        return format("PolarCoord{%.2f, %.2f·π}", radius, theta / PI);
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
