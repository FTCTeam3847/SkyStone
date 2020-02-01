package org.firstinspires.ftc.teamcode.action;

import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.controller.FieldPositionController;
import org.firstinspires.ftc.teamcode.controller.RangeController;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;
import org.firstinspires.ftc.teamcode.polar.PolarUtil;

import static org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower.mecanumPower;
import static org.firstinspires.ftc.teamcode.polar.CartesianCoord.xy;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.toXY;

public class DetectSkystoneActionMoveTo implements RoboAction {
    private boolean isDone = false;
    private boolean isStarted = false;

    private final FieldPosition targetFieldPosition;
    FieldPositionController fieldPositionController;

    private final SkystoneBot bot;


    public DetectSkystoneActionMoveTo(FieldPosition targetFieldPosition, SkystoneBot bot) {
        this.targetFieldPosition = targetFieldPosition;
        this.bot = bot;
    }

    @Override
    public RoboAction start() {
        //Position Controller, drives at half speed
        fieldPositionController = new FieldPositionController(bot.getLocalizer()::getCurrent, bot.getAutonomousSpeed() / 2);
        fieldPositionController.setTarget(targetFieldPosition);

        isStarted = true;
        return null;
    }

    @Override
    public void loop() {
        ColorSensor sensorColor = bot.getColorSensor();
        double red = sensorColor.red();
        double blue = sensorColor.blue();

        //Determines if bot sees non-yellow(ie skystone) element based on the ratio of blue to red
        if (blue / red > 0.5) {
            stop();
            isStarted = false;
            bot.getMecanumDrive().stop();//stop

            double xDist;
            if (toXY(targetFieldPosition.polarCoord).y < 0){ //red
                xDist = bot.getRangeLeft();
                bot.getLocalizer().calibrate(FieldPosition.fieldPosition(xy(-72+xDist+7,toXY(bot.getLocalizer().getCurrent().polarCoord).y), bot.getFieldRelativeHeading()));

            } else { //blue
                xDist = bot.getRangeRight();
                bot.getLocalizer().calibrate(FieldPosition.fieldPosition(xy(72-xDist-7,toXY(bot.getLocalizer().getCurrent().polarCoord).y), bot.getFieldRelativeHeading()));
            }


            if (xDist > 40) {
                bot.setInnerSkystone(6); //sets inner and outer
            } else if (xDist > 32) {
                bot.setInnerSkystone(5);
            } else {
                bot.setInnerSkystone(4);
            }

        }


        if (isStarted && !isDone) {
            if (!fieldPositionController.getError().equals(PolarCoord.ORIGIN)) {
                MecanumPower fieldPositionControl = fieldPositionController.getControl();
                MecanumPower power = mecanumPower(bot.getAutonomousSpeed(), fieldPositionControl.strafe.theta, fieldPositionControl.turn);

                bot.getMecanumDrive().setPower(power);
            } else {
                stop();
                isStarted = false;
                bot.getMecanumDrive().stop();//stop
            }
        } else {
            bot.getMecanumDrive().stop(); //stop
        }
    }

    @Override
    public void stop() {
        isDone = true;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public boolean isStarted() {
        return isStarted;
    }
}
