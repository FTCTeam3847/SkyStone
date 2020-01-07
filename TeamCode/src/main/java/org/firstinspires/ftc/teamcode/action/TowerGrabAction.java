package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.Trinkets.TowerGrabber;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;

import java.util.Locale;
import java.util.function.Supplier;

import static java.lang.String.format;

public class TowerGrabAction extends TimeAction {
    private double position;

    private TowerGrabber grabber;

    public TowerGrabAction(Supplier<Long> msecTime, double position, SkystoneBot bot) {
        super(100, msecTime);
        this.position = position;
        grabber = bot.getTowerBuilder().towerGrabber;
    }

    @Override
    public TowerGrabAction start() {
        super.start();
        grabber.setPosition(this.position);
        return this;
    }

    @Override
    public String toString() {
        return format(Locale.US,
                "grab{%.2f,%s}",
                position,
                grabber
        );
    }
}
