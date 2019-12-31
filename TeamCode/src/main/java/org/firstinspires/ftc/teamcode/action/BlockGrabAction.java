package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.Trinkets.BlockGrabber;
import org.firstinspires.ftc.teamcode.Trinkets.TowerGrabber;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;

import java.util.Locale;
import java.util.function.Supplier;

import static java.lang.String.format;

public class BlockGrabAction extends TimeAction {
    private double position;

    private BlockGrabber grabber;

    public BlockGrabAction(Supplier<Long> msecTime, double position, SkystoneBot bot) {
        super(1000, msecTime);
        this.position = position;
        grabber = bot.getTowerBuilder().blockGrabber;
    }

    @Override
    public void start() {
        super.start();
        grabber.setPosition(this.position);
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
