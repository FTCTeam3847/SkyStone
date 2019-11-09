package org.firstinspires.ftc.teamcode;

import static java.lang.Math.*;

public class PolarUtil
{
    public static PolarCoord subtract(PolarCoord p1, PolarCoord p2)
    {
        double r1 = p1.radius;
        double r2 = p2.radius;
        double t1 = abs(p2.theta-p1.theta);

        double r3 = sqrt(pow(r1,2) + pow(r2,2) - 2*r1*r2*cos(t1));


        double t2 = asin((r2*sin(abs(t1)))/r3);

        PolarCoord p3 = new PolarCoord(r3, (2*PI - (t2-(p1.theta-PI))));

        return p3;
    }
}
