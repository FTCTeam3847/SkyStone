package org.firstinspires.ftc.teamcode.controller;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.SensorREVColorDistance;


public class RangeSensor implements Sensor
{
    public DistanceSensor rangeSensor;

    public RangeSensor(HardwareMap hardwareMap, String deviceName)
    {
        rangeSensor = hardwareMap.get(DistanceSensor.class, deviceName);
    }

    public void init()
    {
    }

    public void stop()
    {
    }

    @Override
    public Double getCurrent()
    {
        return rangeSensor.getDistance(DistanceUnit.INCH);
    }

}
