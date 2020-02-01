package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.controller.FieldPositionController;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;

import java.util.Locale;
import java.util.function.Supplier;

import static java.lang.String.format;
import static org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower.mecanumPower;
import static org.firstinspires.ftc.teamcode.polar.PolarCoord.polar;

public class StrafeUntil implements RoboAction {

    private boolean started = false;
    private boolean isDone = false;

    private final Supplier<Boolean> thereYet;
    final double direction;

    private SkystoneBot bot;

    public StrafeUntil(Supplier<Boolean> thereYet, double direction, SkystoneBot bot) {
        this.thereYet = thereYet;
        this.direction = direction;
        this.bot = bot;
    }

    @Override
    public StrafeUntil start() {
        started = true;
        return this;
    }

    @Override
    public void loop() {
        if (started && !isDone) {
            if (thereYet.get()) {
                stop();
            } else {
                bot.getMecanumDrive().setPower(new MecanumPower(polar(bot.getAutonomousSpeed(), direction),0));
            }
        }
    }

    @Override
    public void stop() {
        bot.getMecanumDrive().stop();
        started = false;
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
                "strafeUntil{%s}",
                "" + thereYet.get()
        );
    }
}
