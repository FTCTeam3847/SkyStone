package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.controller.FieldPositionNoStopController;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;

import java.util.Locale;

import static java.lang.String.format;

public class StrafeToNoStopAction2 implements RoboAction {

    private boolean started = false;
    private boolean isDone = false;
    private final FieldPosition targetFieldPosition;
    FieldPositionNoStopController fieldPositionController;

    private SkystoneBot bot;

    public StrafeToNoStopAction2(FieldPosition targetFieldPosition, SkystoneBot bot) {
        this.targetFieldPosition = targetFieldPosition;
        this.bot = bot;
    }

    @Override
    public StrafeToNoStopAction2 start() {
        started = true;
        fieldPositionController = new FieldPositionNoStopController(bot.getLocalizer()::getCurrent, bot.getAutonomousSpeed());
        fieldPositionController.setTarget(targetFieldPosition);
        return this;
    }

    @Override
    public void loop() {
        if (fieldPositionController.getError().equals(PolarCoord.ORIGIN)) {
            stop();
        } else {
            bot.getMecanumDrive().setPower(fieldPositionController.getControl());
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
