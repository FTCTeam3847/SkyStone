package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.polar.CartesianCoord;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;

import java.util.function.Supplier;

import static org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower.mecanumPower;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.fromCartesian;

public class SkystoneActions extends SequentialAction {
    private final Supplier<Long> msecTime;
    private final SkystoneBot bot;


    public SkystoneActions(Supplier<Long> msecTime, SkystoneBot bot) {
        this.msecTime = msecTime;
        this.bot = bot;
    }

    @Override
    public SkystoneActions start() {
        super.start();
        return this;
    }

    public SkystoneActions towerDrive(long dur, double direction, double speed, double position)
    {
        addAction(new TowerDrive(dur, msecTime, mecanumPower(speed, direction, 0), position, bot));
        return this;
    }

    public SkystoneActions releaseTower() {
        addAction(new TowerGrabAction(msecTime, 0.25, bot));
        return this;
    }

    public SkystoneActions grabTower() {
        addAction(new TowerGrabAction(msecTime, 1.0, bot));
        return this;
    }

    //openness of 1 is fully closed, 0 fully open
    public SkystoneActions grabTower(double openness) {
        addAction(new TowerGrabAction(msecTime, openness, bot));
        return this;
    }

    public SkystoneActions liftTower() {
        return liftTower(0.95d);
    }

    public SkystoneActions lowerTower() {
        return liftTower(0.025d);
    }

    public SkystoneActions lowerTower(double position) {
        return liftTower(position);
    }

    public SkystoneActions liftTower(double position) {
        addAction(new TowerLiftAction(position, bot));
        pause();
        return this;
    }

    public SkystoneActions liftBlock(double position) {
        addAction(new BlockLiftAction(position, bot));
        pause();
        return this;
    }

    public SkystoneActions liftBlock() {
        return liftBlock(0.8d);

    }

    public SkystoneActions lowerBlock() {
        return liftBlock(0.0d);
    }

    public SkystoneActions extendBlock(double position) {
        addAction(new BlockExtendAction(position, bot));
        pause();
        return this;
    }

    public SkystoneActions extendBlock() {
        return extendBlock(1.0);
    }

    public SkystoneActions retractBlock() {
        return extendBlock(0.0);
    }

    public SkystoneActions grabBlock() {
        addAction(new BlockGrabAction(msecTime, 1.0d, bot));
        return this;
    }

    public SkystoneActions releaseBlock() {
        addAction(new BlockGrabAction(msecTime, 0.0d, bot));
        return this;
    }

    public SkystoneActions strafe(double direction, long time) {
        addAction(new MoveAction(time, msecTime, mecanumPower(bot.getAutonomousSpeed(), direction, 0), bot));

        return this;
    }

    //relative to bot
    public SkystoneActions strafeNoStop(double direction, long time) {
        addAction(new MoveNoStopAction(time, msecTime, mecanumPower(bot.getAutonomousSpeed(), direction, 0), bot));
        return this;
    }

    public SkystoneActions strafeNoStop(double direction, long time, double speed) {
        addAction(new MoveNoStopAction(time, msecTime, mecanumPower(speed, direction, 0), bot));
        return this;
    }

    public SkystoneActions pause(long msec) {
        addAction(new PauseAction(msec, msecTime, bot));
        return this;
    }

    public SkystoneActions pause() {
        pause(100);
        return this;
    }

    public SkystoneActions run(Runnable runnable) {
        addAction(new OnceAction(runnable));
        return this;
    }

    public SkystoneActions moveForward(long msec, double speed) {
        addAction(new MoveAction(msec, msecTime, mecanumPower(speed, 0, 0), bot));
        pause();
        return this;
    }

    public SkystoneActions moveBackwards(long msec, double speed) {
        addAction(new MoveAction(msec, msecTime, mecanumPower(speed, Math.PI, 0), bot));
        pause();
        return this;
    }

    public SkystoneActions turnTo(double targetAngle) {
        addAction(new TurnToAction(targetAngle, bot));

        return this;
    }

    public SkystoneActions turnToLocate() {
        addAction(new TurnToLocateAction(bot));
        pause();
        return this;
    }

    public SkystoneActions strafeTo(CartesianCoord destination) {
        strafeTo(fromCartesian(destination));
        return this;
    }

    public SkystoneActions strafeTo(PolarCoord destination) {
        strafeTo(new FieldPosition(destination, 0));
        return this;
    }

    public SkystoneActions strafeTo(FieldPosition fieldPosition) {
        addAction(new StrafeToAction(fieldPosition, bot));
        return this;
    }

    public SkystoneActions strafeTo(Supplier<FieldPosition> fieldPosition) {
        addAction(new StrafeToAction(fieldPosition, bot));
        return this;
    }

    public SkystoneActions strafeToNoStop(CartesianCoord destination) {
        return strafeToNoStop(fromCartesian(destination));
    }

    private SkystoneActions strafeToNoStop(PolarCoord destination) {
        return strafeToNoStop(new FieldPosition(destination, 0));
    }

    public SkystoneActions strafeToNoStop(FieldPosition fieldPosition) {
        addAction(new StrafeToNoStopAction2(fieldPosition, bot));
        return this;
    }

    public SkystoneActions doubleLift(double targetPosition)
    {
        addAction(new DoubleLiftAction(targetPosition, bot));
        return this;
    }

    public SkystoneActions detectSkystoneAction(FieldPosition targetFieldPosition)
    {
        addAction(new DetectSkystoneActionMoveTo(targetFieldPosition, bot));
        return this;
    }

    public SkystoneActions goToInnerSkystone(boolean redTeam)
    {
        addAction(new GoToInnerSkyStone(redTeam, bot));
        return this;
    }


    public SkystoneActions calibrateLeftRight()
    {
        addAction(new CalibrateLeftRight(bot));
        return this;
    }

    public SkystoneActions strafeUntil(double direction, Supplier<Boolean> thereYet)
    {
        addAction(new StrafeUntil(thereYet, direction, bot));
        return this;
    }

}
