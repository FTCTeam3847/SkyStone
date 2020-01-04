package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.Trinkets.BlockLifter;
import org.firstinspires.ftc.teamcode.Trinkets.TowerLifter;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;

import java.util.Locale;

import static java.lang.String.format;

public class DoubleLiftAction extends SequentialAction {

    private boolean started = false;
    private boolean isDone = false;

    private TowerLifter towerLifter;
    private BlockLifter blockLifter;
    private double startPosition;
    double targetPosition;

    private boolean up;

    public DoubleLiftAction(double targetPosition, SkystoneBot bot) {
        this.targetPosition = targetPosition;
        this.towerLifter = bot.getTowerBuilder().towerLifter;
        this.blockLifter = bot.getTowerBuilder().blockLifter;
    }

    @Override
    public DoubleLiftAction start() {
        started = true;
        startPosition = towerLifter.getPosition();

        if (targetPosition > startPosition) {
            up = true;
        } else {
            up = false;
        }
        return this;
    }

    @Override
    public void loop() {
        if (started && !isDone && isComplete()) {
            stop();
            return;
        }

        if (up) { //up
            if (towerLifter.getPosition() >= 1.0 && blockLifter.getPosition() >= 1.0) {
                stop();
                return;
            }
            if (towerLifter.getPosition() >= 1.0) {
                towerLifter.setPower(0.0);
            } else {
                towerLifter.setPower(1.0);
            }

            if (blockLifter.getPosition() >= 1.0) {
                blockLifter.setPower(0.0);
            } else if (towerLifter.getPosition() >= .3) {
                blockLifter.setPower(1.0);
            } else {
                blockLifter.setPower(0.0);
            }
        } else { //down
            if (towerLifter.getPosition() <= .0 && blockLifter.getPosition() <= 0) {
                stop();
                return;
            }
            if (towerLifter.getPosition() <= 0) {
                towerLifter.setPower(0.0);
            } else if (blockLifter.getPosition() <= .7) {
                towerLifter.setPower(-1.0);
            } else {
                towerLifter.setPower(-1.0);
            }

            if (blockLifter.getPosition() <= 0) {
                blockLifter.setPower(0.0);
            } else {
                blockLifter.setPower(-1.0);
            }
        }
    }

    @Override
    public void stop() {
        isDone = true;
        towerLifter.setPower(0.0);
        blockLifter.setPower(0.0);
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
        double currentPosition = blockLifter.getPosition();

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
                towerLifter
        );
    }

}
