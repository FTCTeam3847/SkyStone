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
    private double startPosition;

    public BlockExtendAction(double targetPosition, SkystoneBot bot) {
        this.targetPosition = targetPosition;
        this.extender = bot.getTowerBuilder().blockExtender;
    }

    @Override
    public void start() {
        started = true;
        startPosition = extender.getPosition();
        if (startPosition <= targetPosition)
            extender.setPower(1.0);
        else
            extender.setPower(-1.0);
    }

    @Override
    public void loop() {
        if (started && !isDone && isComplete()) stop();
    }

    @Override
    public void stop() {
        isDone = true;
        extender.setPower(0.0);
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
        double currentPosition = extender.getPosition();

        if (startPosition <= targetPosition)
            return currentPosition >= targetPosition;
        else
            return currentPosition <= targetPosition;
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
