package org.firstinspires.ftc.teamcode.polar;

import java.util.Locale;
import java.util.Objects;

import static java.lang.Math.atan2;
import static java.lang.Math.sqrt;
import static java.lang.String.format;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.normalize;

public class CartesianCoord {

    public final double x;
    public final double y;

    public CartesianCoord(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return format(Locale.US, "{%.2f, %.2f}", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartesianCoord that = (CartesianCoord) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
