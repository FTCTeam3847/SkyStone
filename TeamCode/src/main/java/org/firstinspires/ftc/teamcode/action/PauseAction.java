package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;

import java.util.function.Supplier;

public class PauseAction extends TimeAction{
    private final SkystoneBot bot;


    public PauseAction(long dur, Supplier<Long> msecTime, SkystoneBot bot) {
        super(dur, msecTime);
        this.bot = bot;
    }

    @Override
    public void loop() {
        super.loop();
        if(!isDone())
        {
            bot.move(MecanumPower.ZERO);
        }
    }
}
