package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.controller.HeadingController;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;

import java.util.Locale;

import static java.lang.String.format;

public class TurnToAction implements RoboAction {

    private boolean started = false;
    private boolean isDone = false;
    double targetAngle;
    HeadingController headingController;

    private SkystoneBot bot;


    public TurnToAction(double targetAngle, SkystoneBot bot) {
        this.targetAngle = targetAngle;
        this.bot = bot;
    }

    @Override
    public void start() {
        started = true;
        headingController = new HeadingController(bot::getFieldRelativeHeading, Math.PI / 16, 1.0, 0.1);
        headingController.setTarget(targetAngle);
    }

    @Override
    public void loop() {

        if (started && !isDone) {
            if (headingController.getError() != 0) {
                bot.getMecanumDrive().setPower(new MecanumPower(PolarCoord.ORIGIN, headingController.getControl()));
            } else {
                stop();
                started = false;
                bot.getMecanumDrive().setPower(MecanumPower.ZERO);
            }
        } else {
            bot.getMecanumDrive().setPower(new MecanumPower(PolarCoord.ORIGIN, 0));
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
        return started;
    }


    @Override
    public String toString() {
        return format(Locale.US,
                "TurnToAction{%s}",
                headingController
        );
    }

}
