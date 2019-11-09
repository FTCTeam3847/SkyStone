package org.firstinspires.ftc.teamcode;

import java.util.Objects;

class PolarCoord
{
    public final double theta;
    public final double radius;

    public PolarCoord(double radius, double theta) {
        this.theta = theta;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "PolarCoord{" +
                "theta=" + theta +
                ", radius=" + radius +
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
