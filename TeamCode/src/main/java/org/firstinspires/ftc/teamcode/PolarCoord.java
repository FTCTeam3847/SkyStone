package org.firstinspires.ftc.teamcode;

import java.util.Objects;

import static java.lang.Math.*;

class PolarCoord
{
    public final double theta;
    public final double radius;

    public PolarCoord(double radius, double theta) {
        this.theta = theta;
        this.radius = radius;
    }

    public static PolarCoord fromXY(double x, double y) {
        double radius = sqrt(x*x+y*y);
        double theta = PolarUtil.normalize(atan2(y, x));
        return new PolarCoord(radius, theta);
    }

    @Override
    public String toString() {
        return "PolarCoord{" +
                "radius=" + radius +
                ", theta=" + theta +
                '}';
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
