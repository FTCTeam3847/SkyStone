package org.firstinspires.ftc.teamcode;

import static java.lang.Math.*;

public class PolarUtil {
    public static PolarCoord ORIGIN = new PolarCoord(0.0d, 0.0d);

    public static double addRadians(double t1, double t2) {
        return (t1 + t2 + (2 * PI)) % (2 * PI);
    }

    public static double subtractRadians(double t1, double t2) {
        return addRadians(t1, -t2);
    }

    public static PolarCoord reverse(PolarCoord coord) {
        return new PolarCoord(coord.radius, addRadians(coord.theta, PI));
    }

    public static PolarCoord fromTo(PolarCoord from, PolarCoord to) {
        if (from.equals(to) || (from.radius == 0.0d && to.radius == 0.0d))
            return new PolarCoord(0.0d, 0.0d);

        // moving CW from source, solve for CCW and reverse theta
        // moving towards origin, solve for away from origin and reverse theta
        if ((to.theta < from.theta) || (ORIGIN.equals(to) && !ORIGIN.equals(from))) {
            return reverse(fromTo(to, from));
        }

        double r1 = from.radius;
        double r2 = to.radius;
        double deltaT = to.theta - from.theta;
        double deltaR = r2 - r1;

        if (deltaT == 0.0) {
            if (deltaR > 0.0) {
                // increasing radius
                return new PolarCoord(deltaR, from.theta);
            } else {
                // decreasing radius
                double newTheta = (from.theta + PI) % (2 * PI);
                return new PolarCoord(-deltaR, newTheta);
            }
        }

        double r3 = sqrt(pow(r1, 2) + pow(r2, 2) - (2 * r1 * r2 * cos(deltaT)));


        double t2 = asin((r2 * sin(abs(deltaT))) / r3);

//        double t3 = 2 * PI - (t2 - (from.theta - PI));
        double t3 = subtractRadians(addRadians(from.theta, PI), t2);

        PolarCoord p3 = new PolarCoord(r3, t3);

        return p3;
    }
}
