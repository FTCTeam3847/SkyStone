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
    public void start() {
        started = true;
        startPosition = lifter.getPosition();
        if (startPosition <= targetPosition)
            lifter.setPower(1.0);
        else
            lifter.setPower(-1.0);
    }

    @Override
    public void loop() {
        if (started && !isDone && isComplete()) stop();
    }

    @Override
    public void stop() {
        isDone = true;
        lifter.setPower(0.0);
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
        double currentPosition = lifter.getPosition();

        if (startPosition <= targetPosition)
            return currentPosition >= targetPosition;
        else
            return currentPosition <= targetPosition;
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
