package org.firstinspires.ftc.teamcode;

public class LocationRotation
{
    private double x;
    private double y;
    private double z;

    private double r;
    private double p;
    private double h;

    public LocationRotation(double x, double y, double h)
    {
        this.x = x;
        this.y = y;

        this.h = h;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getH() {
        return h;
    }
}
