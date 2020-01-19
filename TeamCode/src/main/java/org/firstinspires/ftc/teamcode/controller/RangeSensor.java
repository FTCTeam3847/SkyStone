package org.firstinspires.ftc.teamcode.controller;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;

//A Modern Robotics Range Sensor has an ultrasonic sensor which outputs distance in centimeters with a trustable range of ~10-255cm. It also has an ODS with a trustable range of ~0.25-8cm
//The Optical Distance Sensor(ODS) outputs values from 0 to 255, (0-1023 on a standalone ods), converting this value to cm is dependent on the object as it will reflect a different amount of light.
//In order to find the equation to convert this value to cm, you manually record the output from a set of given cm away, graph, and adjust the value via an equation until it closely matches the cm.
//The base equation is 1/sqrt(ODSval) as this inverts the exponential outputs of the ODS.
public class RangeSensor implements Sensor
{
    public byte[] range1Cache; //The read will return an array of bytes. They are stored in this variable. 0x04 is ultrasound(long distance), 0x05 is ODS(short distance). 0-1023. 1023/4=255.
    I2cAddr range1Address = new I2cAddr(0x14); //Default I2C address for MR Range (7-bit)
    public static final int RANGE1_REG_START = 0x04; //Register to start reading
    public static final int RANGE1_READ_LENGTH = 2; //Number of byte to read
    public I2cDeviceSynch range1Reader;

    public RangeSensor(HardwareMap hardwareMap)
    {
        range1Reader = new I2cDeviceSynchImpl(hardwareMap.i2cDevice.get("range1"), range1Address, false);
    }

    public void init()
    {
        range1Reader.engage();
    }

    public void stop()
    {
        range1Reader.disengage();
    }

    @Override
    public Double getCurrent()
    {
        range1Cache = range1Reader.read(RANGE1_REG_START, RANGE1_READ_LENGTH);

        double ultraSonicDistance = range1Cache[0];
        double ODSDistance = convertODSToCMYellowStone(range1Cache[1]);

        //Pick which sensor to use
        if(ultraSonicDistance < 7 && ODSDistance < 7)
        {
            return ODSDistance;
        }
        else
        {
            return ultraSonicDistance;
        }
    }

    //specific to yellow stone element,
    private double convertODSToCMYellowStone(int odsVal)
    {
        if(odsVal == 0)
        {
            return Double.POSITIVE_INFINITY;
        }
        return (56 / Math.sqrt(4*odsVal))-0.75; //turns ODS output into cm, based on the exponential output of the range sensor when looking at yellow stone element
    }
}
