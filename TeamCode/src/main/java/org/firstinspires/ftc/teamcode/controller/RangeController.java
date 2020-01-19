package org.firstinspires.ftc.teamcode.controller;

import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;

import java.util.Locale;
import java.util.function.Supplier;

import static java.lang.Math.E;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.pow;

//The RangeController determines how to move to reach a given distance
public class RangeController implements Controller<Double, MecanumPower>
{
    private static final double DECELERATION_CONSTANT = 1.5; //This determines how quickly the robot will slow down as it reaches its target distance. A larger number decelerates quickly as it gets very near.
    private static final double MAX_OUTPUT = 0.7; //[0-1], This determines the maximum speed at which it would drive toward the distance.
    private final Supplier<Double> distance;

    private double targetDistance = 0;

    private MecanumPower lastControl;

    public RangeController(Supplier<Double> distance)
    {
        this.distance = distance;
    }

    @Override
    public void setTarget(Double targetDistance)
    {
        this.targetDistance = targetDistance;
    }

    //Current distance from object - desired distance from object
    public double getError()
    {
        return this.distance.get() - targetDistance;
    }

    //determines how the robot has to move to approach the target distance, theta of 0 drives forward, theta of pi drives backwards
    @Override
    public MecanumPower getControl()
    {
        double difference = getError();


        //This equation is graphed at https://www.desmos.com/calculator/hzcxgldgks
        //Variable d represents the DECELERATION_CONSTANT, while variable m represents MAX_OUTPUT
        double radius = -(MAX_OUTPUT/ pow(E, DECELERATION_CONSTANT * abs(difference)))+MAX_OUTPUT; //proportional speed [0 -> 1]

        double theta;
        if (difference > 0)
        {
            theta = 0;//forwards
        }
        else
        {
            theta = PI;//backwards
        }

        return MecanumPower.mecanumPower(radius, theta, 0);
    }



    @Override
    public void stop()
    {

    }

    @Override
    public String toString()
    {
        return String.format(
                Locale.US,
                "dist:%.2f, tgt:%.2f, ctl:%.2f",
                distance.get(),
                targetDistance,
                lastControl
        );
    }
}
