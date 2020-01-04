package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.Trinkets.BlockLifter;
import org.firstinspires.ftc.teamcode.Trinkets.TowerLifter;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;

import java.util.Locale;

import static java.lang.String.format;

public class DoubleLiftAction extends SequentialAction{

    private boolean started = false;
    private boolean isDone = false;
    private double targetPosition;

    private TowerLifter towerLifter;
    private BlockLifter blockLifter;
    private double startPosition;

    private int SCALE = 1;

    public DoubleLiftAction(double targetPosition, SkystoneBot bot) {
        this.targetPosition = targetPosition;
        this.towerLifter = bot.getTowerBuilder().towerLifter;
        this.blockLifter = bot.getTowerBuilder().blockLifter;
    }

    @Override
    public DoubleLiftAction start() {
        started = true;
        startPosition = towerLifter.getPosition();
        if (startPosition > targetPosition){
            SCALE = -1;
        }

        return this;
    }

    @Override
    public void loop() {
        if (started && !isDone && isComplete()) stop();

        if(towerLifter.getPosition() >= .9 && blockLifter.getPosition() >= .9)
        {
            stop();
            return;
        }

        towerLifter.setPower(1.0 * SCALE);

        if(towerLifter.getPosition() >= .5)
        {
            blockLifter.setPower(1.0 * SCALE);
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
