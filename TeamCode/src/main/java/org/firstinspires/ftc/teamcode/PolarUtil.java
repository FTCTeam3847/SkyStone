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
        double fromX = from.radius * cos(from.theta);
        double fromY = from.radius * sin(from.theta);

        double toX = to.radius * cos(to.theta);
        double toY = to.radius * sin(to.theta);

        double distance = sqrt( pow(toY-fromY, 2) + pow(toX-fromX, 2));
        double theta = atan2(toY-fromY, toX-fromX) % (2*PI);


        if(from.radius == to.radius && from.theta == to.theta || from.radius == 0 && to.radius == 0)
        {
            return new PolarCoord(0.0,0.0);
        }

        return new PolarCoord(distance, theta);

    }
}
