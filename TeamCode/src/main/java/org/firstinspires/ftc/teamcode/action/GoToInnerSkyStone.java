package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.GameConstants;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.controller.FieldPositionController;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;

import java.util.Locale;

import static java.lang.String.format;
import static org.firstinspires.ftc.teamcode.GameConstants.blueSkystoneLocations;
import static org.firstinspires.ftc.teamcode.GameConstants.redSkystoneLocations;
import static org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower.mecanumPower;

public class GoToInnerSkyStone implements RoboAction {

    private boolean started = false;
    private boolean isDone = false;
    boolean redTeam;
    FieldPositionController fieldPositionController;

    private SkystoneBot bot;

    public GoToInnerSkyStone(boolean redTeam, SkystoneBot bot) {
        this.redTeam = redTeam;
        this.bot = bot;
    }

    @Override
    public GoToInnerSkyStone start() {
        started = true;
        fieldPositionController = new FieldPositionController(bot.getLocalizer()::getCurrent, bot.getAutonomousSpeed());

        if(redTeam)
        {
            fieldPositionController.setTarget(redSkystoneLocations.get(bot.getInnerSkystone()).get(0));
        }
        else
        {
            fieldPositionController.setTarget(blueSkystoneLocations.get(bot.getInnerSkystone()).get(0));
        }
        return this;
    }

    @Override
    public void loop() {
        if (started && !isDone) {
            if (!fieldPositionController.getError().equals(PolarCoord.ORIGIN)) {
                bot.getMecanumDrive().setPower(fieldPositionController.getControl());
            } else {
                stop();
                started = false;
                bot.getMecanumDrive().stop();
            }
        } else {
            bot.getMecanumDrive().setPower(mecanumPower(PolarCoord.ORIGIN, 0));
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
                "StrafeToAction{%s}",
                fieldPositionController
        );
    }
}
