package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.controller.FieldPosition;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static org.firstinspires.ftc.teamcode.polar.CartesianCoord.xy;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtractRadians;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.toXY;

public class CalibrateLeftRight implements RoboAction {


    private boolean isDone = false;
    private boolean isStarted = false;

    private final SkystoneBot bot;


    public CalibrateLeftRight(SkystoneBot bot) {
        this.bot = bot;
    }

    @Override
    public RoboAction start() {
        //Position Controller, drives at half speed
        isStarted = true;
        return null;
    }

    //assumes the robot is facing blue or red wall
    @Override
    public void loop() {

        double leftDist = bot.getRangeLeft().reset().getCurrent();
        double rightDist = bot.getRangeRight().reset().getCurrent();

        double heading = bot.getLocalizer().getCurrent().heading;

        if (toXY(bot.getLocalizer().getCurrent().polarCoord).x < 0) { //skystone side
            if (abs(subtractRadians(heading, (PI / 2))) < 0.05) { //facing blue
                bot.getLocalizer().calibrate(FieldPosition.fieldPosition(xy(-72 + leftDist + 7, toXY(bot.getLocalizer().getCurrent().polarCoord).y), bot.getFieldRelativeHeading()));

            } else if (abs(heading) < 0.05) { //facing red
                bot.getLocalizer().calibrate(FieldPosition.fieldPosition(xy(72 - rightDist - 7, toXY(bot.getLocalizer().getCurrent().polarCoord).y), bot.getFieldRelativeHeading()));
            } else//foundation side
            {
                if (abs(subtractRadians(heading, (PI / 2))) < 0.05) { //facing blue
                    bot.getLocalizer().calibrate(FieldPosition.fieldPosition(xy(-72 + rightDist + 7, toXY(bot.getLocalizer().getCurrent().polarCoord).y), bot.getFieldRelativeHeading()));
                } else if (abs(heading) < 0.05) { //facing red
                    bot.getLocalizer().calibrate(FieldPosition.fieldPosition(xy(72 - leftDist - 7, toXY(bot.getLocalizer().getCurrent().polarCoord).y), bot.getFieldRelativeHeading()));
                }
            }
        }
        stop();
        isStarted = false;
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
