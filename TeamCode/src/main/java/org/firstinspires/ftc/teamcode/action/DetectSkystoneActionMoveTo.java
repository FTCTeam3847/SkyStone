package org.firstinspires.ftc.teamcode.action;

import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.controller.FieldPositionController;
import org.firstinspires.ftc.teamcode.controller.RangeController;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;

import static org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower.mecanumPower;

public class DetectSkystoneActionMoveTo implements RoboAction
{
    private boolean isDone = false;
    private boolean isStarted = false;

    private final FieldPosition targetFieldPosition;
    FieldPositionController fieldPositionController;

    private final double targetDistance;
    RangeController rangeController;

    private final SkystoneBot bot;

    FieldPosition startingLocation;


    public DetectSkystoneActionMoveTo(FieldPosition targetFieldPosition, double targetDistance, SkystoneBot bot)
    {
        this.targetFieldPosition = targetFieldPosition;
        this.targetDistance = targetDistance;
        this.bot = bot;
    }

    @Override
    public RoboAction start()
    {
        //Range Controller
        rangeController = new RangeController(bot.getRangeSensor()::getCurrent);
        rangeController.setTarget(targetDistance);

        //Position Controller
        fieldPositionController = new FieldPositionController(bot.getLocalizer()::getCurrent, bot.getAutonomousSpeed());
        fieldPositionController.setTarget(targetFieldPosition);

        startingLocation = fieldPositionController.getCurrent();//where the bot is on start


        isStarted = true;
        return null;
    }

    @Override
    public void loop()
    {
        ColorSensor sensorColor = bot.getColorSensor();
        double red = sensorColor.red();
        double blue = sensorColor.blue();

        //Determines if bot sees non-yellow(ie skystone) element based on the ratio of blue to red
        if (blue/red > 0.5) {
            stop();

            double distanceTraveled = fieldPositionController.getCurrent().distance(startingLocation); //distance from start
            if(distanceTraveled <= 6)//starts at most inner stone
            {
                bot.setInnerSkystone(6); //sets inner and outer
            }
            else if(distanceTraveled <= 12)
            {
                bot.setInnerSkystone(5); //sets inner and outer
            }
            else
            {
                bot.setInnerSkystone(4); //sets inner and outer
            }
        }


        if (isStarted && !isDone) {
            if (!fieldPositionController.getError().equals(PolarCoord.ORIGIN)) {
                MecanumPower fieldPositionControl = fieldPositionController.getControl();
                MecanumPower power = mecanumPower(bot.getAutonomousSpeed(), fieldPositionControl.strafe.theta, fieldPositionControl.turn);

                power.add(rangeController.getControl()); //stays the same distance from the line, adds power

                bot.getMecanumDrive().setPower(power);
            } else {
                stop();
                isStarted = false;
                bot.getMecanumDrive().setPower(MecanumPower.ZERO);//stop
            }
        } else {
            bot.getMecanumDrive().setPower(mecanumPower(PolarCoord.ORIGIN, 0)); //stop
        }
    }

    @Override
    public void stop()
    {
        isDone = true;
    }

    @Override
    public boolean isDone()
    {
        return isDone;
    }

    @Override
    public boolean isStarted()
    {
        return isStarted;
    }
}
