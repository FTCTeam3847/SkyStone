package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.Trinkets.TowerLifter;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;

import java.util.Locale;
import java.util.function.Supplier;

import static java.lang.String.format;

public class TowerDrive extends TimeAction {

    private boolean started = false;
    private boolean isDone = false;

    private TowerLifter towerLifter;
    private SkystoneBot bot;
    private MecanumPower mecanumPower;

    private double startPosition;
    double targetPosition;

    boolean up;

    public TowerDrive(long dur, Supplier<Long> msecTime, MecanumPower mecanumPower, double position, SkystoneBot bot) {
        super(dur, msecTime);
        this.bot = bot;
        this.targetPosition = position;
        this.mecanumPower = mecanumPower;
        towerLifter = bot.getTowerBuilder().towerLifter;
    }


    @Override
    public TowerDrive start() {
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
            if (towerLifter.getPosition() >= targetPosition) {
                towerLifter.stop();
                return;
            }
            towerLifter.setPower(1.0);
        } else { //down
            if (towerLifter.getPosition() <= targetPosition) {
                towerLifter.stop();
                return;
            }
            towerLifter.setPower(-1.0);
        }

        if (runtime() >= dur) {
            bot.getMecanumDrive().stop();
        } else {
            bot.getMecanumDrive().setPower(mecanumPower);
        }
    }

    @Override
    public void stop() {
        isDone = true;
        towerLifter.setPower(0.0);
        bot.getMecanumDrive().stop();
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
        double currentPosition = towerLifter.getPosition();

        if (up)
            if (currentPosition >= targetPosition && runtime() >= dur) {
                isDone = true;
                return true;
            } else//down
            {
                if (currentPosition <= targetPosition && runtime() >= dur) {
                    isDone = true;
                    return true;
                }
            }
        return false;

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
