package org.firstinspires.ftc.teamcode.controller;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.wpilib.MedianFilter;

import java.util.function.Supplier;


public class RangeSensor implements Sensor
{
    private final MedianFilter filter;
    private final int sampleSize = 10;
    private final Supplier<Double> range;
    private double last = Double.NaN;

    public RangeSensor(Supplier<Double> range)
    {
        this.range = range;
        this.filter = new MedianFilter(sampleSize);
    }

    public void init()
    {
        filter.reset();
    }

    public void stop()
    {
    }

    public boolean between(double low, double high) {
        double curr = getCurrent();
        if (! Double.isFinite(low)) return false;
        if (! Double.isFinite(high)) return false;
        if (! Double.isFinite(curr)) return false;

        return curr > low && curr < high;
    }

    public boolean lessThan(double val) {
        double curr = getCurrent();
        if (! Double.isFinite(val)) return false;
        if (! Double.isFinite(curr)) return false;
        return curr < val;
    }

    public boolean greaterThan(double val) {
        double curr = getCurrent();
        if (! Double.isFinite(val)) return false;
        if (! Double.isFinite(curr)) return false;
        return curr > val;
    }

    public RangeSensor reset() {
        filter.reset();
        this.last = Double.NaN;
        while (filter.size() < sampleSize-1) getCurrent();
        return this;
    }

    @Override
    public Double getCurrent()
    {
        double raw = range.get();
        if (raw < 325) {
            double ret = filter.calculate(raw);
            this.last = (filter.size() < sampleSize) ? Double.NaN : ret;
        } else {
            this.last = Double.NaN;
        }

        return this.last;
    }

    public double getLast() {
        return this.last;
    }
}
