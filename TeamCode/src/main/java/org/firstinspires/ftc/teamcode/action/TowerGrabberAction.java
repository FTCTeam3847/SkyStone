package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;

import java.util.Locale;
import java.util.function.Supplier;

public class TowerGrabberAction extends TimeAction {
    private double position;

    private final SkystoneBot bot;

    public TowerGrabberAction(Supplier<Long> msecTime, double position, SkystoneBot bot) {
        super(1000, msecTime);
        this.position = position;
        this.bot = bot;
    }

    @Override
    public void start() {
        super.start();
        bot.getTowerGrabber().setPosition(this.position);
    }

}
