package org.firstinspires.ftc.teamcode.polar;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static org.firstinspires.ftc.teamcode.polar.CartesianCoord.xy;
import static org.firstinspires.ftc.teamcode.polar.PolarCoord.ORIGIN;
import static org.firstinspires.ftc.teamcode.polar.PolarCoord.polar;

public class PolarUtil {
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
        return polar(radius, theta);
    }

    public static CartesianCoord toXY(PolarCoord polarCoord){
        double x = polarCoord.radius * Math.cos(polarCoord.theta);
        double y = polarCoord.radius * Math.sin(polarCoord.theta);
        return xy(x, y);
    }

    public static PolarCoord subtract(PolarCoord thiz, PolarCoord minusThat) {
        if (thiz.equals(minusThat)) {
            return ORIGIN;
        } else if (thiz.radius == 0.0d && minusThat.radius == 0.0d) {
            if (thiz.theta > minusThat.theta) {
                return polar(0.0d, minusThat.theta);
            }
            return polar(0.0d, addRadians(thiz.theta, PI));
        }

        double fromX = thiz.radius * cos(thiz.theta);
        double fromY = thiz.radius * sin(thiz.theta);

        double toX = minusThat.radius * cos(minusThat.theta);
        double toY = minusThat.radius * sin(minusThat.theta);

        double distance = sqrt(pow(toY - fromY, 2) + pow(toX - fromX, 2));
        double theta = normalize(atan2(toY - fromY, toX - fromX));

        return polar(distance, theta);
    }

    public static PolarCoord add(PolarCoord thiz, PolarCoord plusThat) {
        if (thiz.equals(plusThat)) {
            return thiz;
        } else if (thiz.radius == 0.0d) {
            return plusThat;
        } else if (plusThat.radius == 0.0d) {
            return thiz;
        }

        double fromX = thiz.radius * cos(thiz.theta);
        double fromY = thiz.radius * sin(thiz.theta);

        double toX = plusThat.radius * cos(plusThat.theta);
        double toY = plusThat.radius * sin(plusThat.theta);
        double x = fromX + toX;
        double y = fromY + toY;

        double distance = sqrt(pow(y, 2) + pow(x, 2));
        double theta = normalize(atan2(y, x));

        return polar(distance, theta);
    }

    public static PolarCoord fromCartesian(CartesianCoord destination) {
        return fromXY(destination.x, destination.y);
    }

}
