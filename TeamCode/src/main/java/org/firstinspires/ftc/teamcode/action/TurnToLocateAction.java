package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;

import java.util.Locale;

import static org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower.mecanumPower;

public class TurnToLocateAction implements RoboAction {

    private boolean started = false;
    private boolean isDone = false;

    private SkystoneBot bot;


    public TurnToLocateAction(SkystoneBot bot) {
        this.bot = bot;
    }

    @Override
    public TurnToLocateAction start() {
        started = true;
        return this;
    }

    @Override
    public void loop() {
        if (!started || isDone) return;

        if (bot.getLocalizer().getCurrent().fix != FieldPosition.Fix.ABSOLUTE) {
            bot.getMecanumDrive().setPower(mecanumPower(0, 0, 0.15));
        } else {
            stop();
        }
    }

    @Override
    public void stop() {
        bot.getMecanumDrive().setPower(MecanumPower.ZERO);
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
        return String.format(Locale.US,
                "TurnToLocateAction{...}"
        );
    }

}
