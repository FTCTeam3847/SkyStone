package org.firstinspires.ftc.teamcode.controller;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.SensorREVColorDistance;

//A Modern Robotics Range Sensor has an ultrasonic sensor which outputs distance in centimeters with a trustable range of ~10-255cm. It also has an ODS with a trustable range of ~0.25-8cm
//The Optical Distance Sensor(ODS) outputs values from 0 to 255, (0-1023 on a standalone ods), converting this value to cm is dependent on the object as it will reflect a different amount of light.
//In order to find the equation to convert this value to cm, you manually record the output from a set of given cm away, graph, and adjust the value via an equation until it closely matches the cm.
//The base equation is 1/sqrt(ODSval) as this inverts the exponential outputs of the ODS.
public class RangeSensor implements Sensor
{
    public byte[] range1Cache; //The read will return an array of bytes. They are stored in this variable. 0x04 is ultrasonic(long distance), 0x05 is ODS(short distance). 0-1023. 1023/4=255.
    public DistanceSensor rangeSensor;

    public RangeSensor(HardwareMap hardwareMap)
    {
        //rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "range1");

        rangeSensor = hardwareMap.get(DistanceSensor.class, "color1");
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

        return rangeSensor.getDistance(DistanceUnit.CM);
//        double ultraSonicDistance = rangeSensor.cmUltrasonic();
//        double rawOptical = rangeSensor.rawOptical();
//        double ODSDistance = convertODSToCMYellowStone(rawOptical);
//
//        double builtInCalculation = rangeSensor.cmOptical();
//
//        //Pick which sensor to use
//        if(ultraSonicDistance < 7 && ODSDistance < 7)
//        {
//            return builtInCalculation;
//            //return ODSDistance;
//        }
//        else
//        {
//            return ultraSonicDistance;
//        }
    }

    //specific to yellow stone element,
    private int convertODSToCMYellowStone(double odsVal)
    {
        if(odsVal == 0)
        {
            return Integer.MAX_VALUE;
        }
        return (int)((56 / Math.sqrt(4*odsVal))-0.75); //turns ODS output into cm, based on the exponential output of the range sensor when looking at yellow stone element
    }
}
