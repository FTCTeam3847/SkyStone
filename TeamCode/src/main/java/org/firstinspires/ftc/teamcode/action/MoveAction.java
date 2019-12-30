package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;

import java.util.Locale;
import java.util.function.Supplier;

public class MoveAction extends TimeAction {
    private MecanumPower mecanumPower;

    private final SkystoneBot bot;

    public MoveAction(long dur, Supplier<Long> msecTime, MecanumPower mecanumPower, SkystoneBot bot) {
        super(dur, msecTime);
        this.mecanumPower = mecanumPower;
        this.bot = bot;
    }

    @Override
    public void loop() {
        super.loop();
        if (!isDone()) {
            bot.move(mecanumPower);
        }
    }

    @Override
    public void stop() {
        super.stop();
        bot.move(MecanumPower.ZERO);
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
