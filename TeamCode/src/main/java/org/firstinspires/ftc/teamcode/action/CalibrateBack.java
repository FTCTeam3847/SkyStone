package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.controller.FieldPosition;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static org.firstinspires.ftc.teamcode.polar.CartesianCoord.xy;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtractRadians;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.toXY;

public class CalibrateBack implements RoboAction {


    private boolean isDone = false;
    private boolean isStarted = false;

    private final SkystoneBot bot;


    public CalibrateBack(SkystoneBot bot) {
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

        double backDist = bot.getRangeBack().reset().getCurrent();

        double heading = bot.getLocalizer().getCurrent().heading;

        if (abs(subtractRadians(heading, (PI / 2))) < 0.05) { //facing blue
            bot.getLocalizer().calibrate(FieldPosition.fieldPosition(xy(toXY(bot.getLocalizer().getCurrent().polarCoord).x,72 - backDist - 6.25), bot.getFieldRelativeHeading()));
        } else if (abs(heading) < 0.05) { //facing red
            bot.getLocalizer().calibrate(FieldPosition.fieldPosition(xy(toXY(bot.getLocalizer().getCurrent().polarCoord).x,-72 + backDist + 6.25), bot.getFieldRelativeHeading()));
        } else if (abs(heading) < 0.05) { //facing foundation side
            bot.getLocalizer().calibrate(FieldPosition.fieldPosition(xy(72 - backDist - 6.25, toXY(bot.getLocalizer().getCurrent().polarCoord).y), bot.getFieldRelativeHeading()));
        } else{ //facing skystone side
            bot.getLocalizer().calibrate(FieldPosition.fieldPosition(xy(-72 + backDist + 6.25, toXY(bot.getLocalizer().getCurrent().polarCoord).y), bot.getFieldRelativeHeading()));
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
