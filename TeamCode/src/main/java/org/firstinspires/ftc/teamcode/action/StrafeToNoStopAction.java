package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.controller.FieldPositionController;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;

import java.util.Locale;

import static java.lang.String.format;
import static org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower.mecanumPower;

public class StrafeToNoStopAction implements RoboAction {

    private boolean started = false;
    private boolean isDone = false;
    private final FieldPosition targetFieldPosition;
    FieldPositionController fieldPositionController;

    private SkystoneBot bot;

    public StrafeToNoStopAction(FieldPosition targetFieldPosition, SkystoneBot bot) {
        this.targetFieldPosition = targetFieldPosition;
        this.bot = bot;
    }

    @Override
    public StrafeToNoStopAction start() {
        started = true;
        fieldPositionController = new FieldPositionController(bot.getLocalizer()::getCurrent, bot.getAutonomousSpeed());
        fieldPositionController.setTarget(targetFieldPosition);
        return this;
    }

    @Override
    public void loop() {
        if (started && !isDone) {
            if (fieldPositionController.getError() != PolarCoord.ORIGIN) {
                MecanumPower control = fieldPositionController.getControl();
                MecanumPower power = mecanumPower(bot.getAutonomousSpeed(), control.strafe.theta, control.turn);
                bot.getMecanumDrive().setPower(power);
            } else {
                stop();
                started = false;
                bot.getMecanumDrive().setPower(MecanumPower.ZERO);
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
