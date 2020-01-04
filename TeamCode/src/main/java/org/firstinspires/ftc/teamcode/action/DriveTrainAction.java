package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.polar.CartesianCoord;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;
import org.firstinspires.ftc.teamcode.polar.PolarUtil;

import java.util.function.Supplier;

import static org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower.mecanumPower;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.fromCartesian;

public class DriveTrainAction extends SequentialAction {
    private final Supplier<Long> msecTime;
    private final SkystoneBot bot;

    public DriveTrainAction(Supplier<Long> msecTime, SkystoneBot bot) {
        this.msecTime = msecTime;
        this.bot = bot;
    }

    public DriveTrainAction strafe(double direction, long time, double speed) {
        addAction(new MoveAction(time, msecTime, mecanumPower(speed, direction, 0), bot));
        pause();
        return this;
    }

    public DriveTrainAction pause(long msec) {
        addAction(new PauseAction(msec, msecTime, bot));
        return this;
    }

    public DriveTrainAction pause() {
        pause(250);
        return this;
    }

    public DriveTrainAction run(Runnable runnable) {
        addAction(new OnceAction(runnable));
        return this;
    }

    public DriveTrainAction moveForward(long msec, double speed) {
        addAction(new MoveAction(msec, msecTime, mecanumPower(speed, 0, 0), bot));
        pause();
        return this;
    }

    public DriveTrainAction moveBackwards(long msec, double speed) {
        addAction(new MoveAction(msec, msecTime, mecanumPower(speed, Math.PI, 0), bot));
        pause();
        return this;
    }

    public DriveTrainAction turnTo(double targetAngle) {
        addAction(new TurnToAction(targetAngle, bot));
        pause();
        return this;
    }

    public DriveTrainAction strafeTo(CartesianCoord destination) {
        strafeTo(fromCartesian(destination));
        return this;
    }

    public DriveTrainAction strafeTo(PolarCoord destination) {
        strafeTo(new FieldPosition(destination, 0));
        return this;
    }

    public DriveTrainAction strafeTo(FieldPosition fieldPosition) {
        addAction(new StrafeToAction(fieldPosition, bot));
        pause();
        return this;
    }

}
