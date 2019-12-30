package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;

import java.util.function.Supplier;

public class SkystoneAction extends SequentialAction {
    private final Supplier<Long> msecTime;
    private final SkystoneBot bot;

    public SkystoneAction(Supplier<Long> msecTime, SkystoneBot bot) {
        this.msecTime = msecTime;
        this.bot = bot;
    }

    public SkystoneAction strafe(double direction, long time, double speed) {
        addAction(new MoveAction(time, msecTime, new MecanumPower(new PolarCoord(speed, direction), 0), bot));
        return this;
    }

    public SkystoneAction pause(long time) {
        addAction(new PauseAction(time, msecTime, bot));
        return this;
    }

    public SkystoneAction moveForward(long time, double speed) {
        addAction(new MoveAction(time, msecTime, new MecanumPower(new PolarCoord(speed, 0), 0), bot));
        return this;
    }

    public SkystoneAction moveBackwards(long time, double speed) {
        addAction(new MoveAction(time, msecTime, new MecanumPower(new PolarCoord(speed, Math.PI), 0), bot));
        return this;
    }

    public SkystoneAction turn(double targetAngle) {
        addAction(new TurnToAction(targetAngle, bot));
        return this;
    }

}
