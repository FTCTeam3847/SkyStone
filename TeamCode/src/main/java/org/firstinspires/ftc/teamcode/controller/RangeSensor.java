package org.firstinspires.ftc.teamcode.controller;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.wpilib.MedianFilter;

import java.util.function.Supplier;


public class RangeSensor implements Sensor
{
    private final MedianFilter filter;
    private final int sampleSize = 10;
    private final Supplier<Double> range;

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

    public RangeSensor fill() {
        while (filter.size() < sampleSize-1) getCurrent();
        return this;
    }

    @Override
    public Double getCurrent()
    {
        double ret = filter.calculate(range.get());
        return (filter.size() < sampleSize) ? Double.NaN : ret;
    }

}
