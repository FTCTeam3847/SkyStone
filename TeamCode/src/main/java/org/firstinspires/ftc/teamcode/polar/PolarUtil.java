package org.firstinspires.ftc.teamcode.polar;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class PolarUtil {
    public static PolarCoord ORIGIN = new PolarCoord(0.0d, 0.0d);

    public static double normalize(double t1) {
        return (t1 + 2.0d * PI) % (2.0d * PI);
    }

    public static double addRadians(double t1, double t2) {
        return normalize(t1 + t2);
    }

    public static double subtractRadians(double t1, double t2) {
        return addRadians(t1, -t2);
    }

    public static PolarCoord fromXY(double x, double y) {
        double radius = sqrt(x * x + y * y);
        double t1 = atan2(y, x);
        double theta = normalize(t1);
        return new PolarCoord(radius, theta);
    }

    public static PolarCoord subtract(PolarCoord from, PolarCoord to) {
        if (from.equals(to)) {
            return ORIGIN;
        } else if (from.radius == 0.0d && to.radius == 0.0d) {
            if (from.theta > to.theta) {
                return new PolarCoord(0.0d, to.theta);
            }
            return new PolarCoord(0.0d, addRadians(from.theta, PI));
        }

        double fromX = from.radius * cos(from.theta);
        double fromY = from.radius * sin(from.theta);

        double toX = to.radius * cos(to.theta);
        double toY = to.radius * sin(to.theta);

        double distance = sqrt(pow(toY - fromY, 2) + pow(toX - fromX, 2));
        double theta = normalize(atan2(toY - fromY, toX - fromX));

        return new PolarCoord(distance, theta);
    }

    public static PolarCoord add(PolarCoord from, PolarCoord to) {
        if (from.equals(to)) {
            return from;
        } else if (from.radius == 0.0d) {
            return to;
        } else if (to.radius == 0.0d) {
            return from;
        }

        double fromX = from.radius * cos(from.theta);
        double fromY = from.radius * sin(from.theta);

        double toX = to.radius * cos(to.theta);
        double toY = to.radius * sin(to.theta);
        double x = fromX + toX;
        double y = fromY + toY;

        double distance = sqrt(pow(y, 2) + pow(x, 2));
        double theta = normalize(atan2(y, x));

        return new PolarCoord(distance, theta);
    }
}
