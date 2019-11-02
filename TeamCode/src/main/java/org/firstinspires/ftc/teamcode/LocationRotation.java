package org.firstinspires.ftc.teamcode;

public class LocationRotation
{
    private double x;
    private double y;
    private double z;

    private double r;
    private double p;
    private double h;

    public LocationRotation(double x, double y, double z, double r, double p, double h)
    {
        this.x = x;
        this.y = y;
        this.z = z;

        this.r = r;
        this.p = p;
        this.h = h;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getR() {
        return r;
    }

    public double getP() {
        return p;
    }

    public double getH() {
        return h;
    }
}
