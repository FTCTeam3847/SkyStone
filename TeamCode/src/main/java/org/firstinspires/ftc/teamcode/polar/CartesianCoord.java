package org.firstinspires.ftc.teamcode.polar;

import java.util.Locale;
import java.util.Objects;

import static java.lang.String.format;

public class CartesianCoord {
    public static final CartesianCoord ORIGIN = new CartesianCoord(0.0d, 0.0d);
    public static final CartesianCoord UNKNOWN = new CartesianCoord(-0.0d, -0.0d) {
        @Override
        public String toString() {
            return "{UNKNOWN}";
        }
    };

    public final double x;
    public final double y;

    public CartesianCoord(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static CartesianCoord xy(double x, double y) {
        return new CartesianCoord(x, y);
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
