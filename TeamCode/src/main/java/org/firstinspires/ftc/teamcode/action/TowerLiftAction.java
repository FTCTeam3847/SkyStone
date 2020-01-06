package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.Trinkets.TowerLifter;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;

import java.util.Locale;

import static java.lang.String.format;

public class TowerLiftAction implements RoboAction {

    private boolean started = false;
    private boolean isDone = false;
    private double targetPosition;

    private TowerLifter lifter;
    private double startPosition;

    public TowerLiftAction(double targetPosition, SkystoneBot bot) {
        this.targetPosition = targetPosition;
        this.lifter = bot.getTowerBuilder().towerLifter;
    }

    @Override
    public TowerLiftAction start() {
        started = true;
        lifter.setPosition(targetPosition);
        return this;
    }

    @Override
    public void loop() {
        if (started && !isDone && isComplete()) stop();
    }

    @Override
    public void stop() {
        isDone = true;
        lifter.stop();
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
        return !lifter.isBusy();
    }

    @Override
    public String toString() {
        return format(Locale.US,
                "liftTowerTo{T:%.2f L:%s}",
                targetPosition,
                lifter
        );
    }

}
