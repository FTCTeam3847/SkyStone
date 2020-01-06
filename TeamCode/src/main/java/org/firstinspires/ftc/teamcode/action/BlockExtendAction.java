package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.Trinkets.BlockExtender;
import org.firstinspires.ftc.teamcode.Trinkets.BlockLifter;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;

import java.util.Locale;

import static java.lang.String.format;

public class BlockExtendAction implements RoboAction {

    private boolean started = false;
    private boolean isDone = false;
    private double targetPosition;

    private BlockExtender extender;

    public BlockExtendAction(double targetPosition, SkystoneBot bot) {
        this.targetPosition = targetPosition;
        this.extender = bot.getTowerBuilder().blockExtender;
    }

    @Override
    public BlockExtendAction start() {
        started = true;
        extender.setPosition(targetPosition);
        return this;
    }

    @Override
    public void loop() {
        if (started && !isDone && isComplete()) stop();
    }

    @Override
    public void stop() {
        isDone = true;
        extender.stop();
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    private boolean isComplete() {
        return !extender.isBusy();
    }

    @Override
    public String toString() {
        return format(Locale.US,
                "extendBlockTo{T:%.2f X:%s}",
                targetPosition,
                extender
        );
    }

}
