package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;

import java.util.Locale;
import java.util.function.Supplier;

public class MoveAction implements RoboAction {
    private long startTime;
    private boolean isDone = false;
    private MecanumPower mecanumPower;
    private long dur;
    private Supplier<Long> msecTime;
    private boolean isStarted = false;

    private SkystoneBot bot;

    public MoveAction(long dur, MecanumPower mecanumPower, Supplier<Long> msecTime, SkystoneBot bot) {
        this.mecanumPower = mecanumPower;
        this.dur = dur;
        this.msecTime = msecTime;
        this.bot = bot;
    }

    public void start() {
        startTime = msecTime.get();
        isStarted = true;
    }

    public void loop() {
        if (!isStarted) return;

        if (runtime() < dur) {
            bot.move(mecanumPower);
        } else {
            stop();
        }
    }

    public void stop() {
        bot.move(MecanumPower.ZERO);
        isStarted = false;
        isDone = true;
    }

    public boolean isDone() {
        return isDone;
    }

    private long runtime() {
        if (isDone) return dur;
        else if (!isStarted) return 0;
        else return msecTime.get() - startTime;
    }

    @Override
    public String toString() {
        return String.format(Locale.US,
                "%s{%.2fs, %s}",
                getClass().getSimpleName(),
                runtime() / 1000.0d,
                mecanumPower
        );
    }


}
