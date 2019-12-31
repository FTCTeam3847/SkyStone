package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.Trinkets.TowerBuilder;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;

import java.util.function.Supplier;

public class TowerBuilderAction extends SequentialAction {
    private final Supplier<Long> msecTime;
    private final SkystoneBot bot;


    public TowerBuilderAction(Supplier<Long> msecTime, SkystoneBot bot) {
        this.msecTime = msecTime;
        this.bot = bot;
    }

    public TowerBuilderAction releaseTower() {
        addAction(new TowerGrabAction(msecTime, 0.0, bot));
        pause();
        return this;
    }

    public TowerBuilderAction grabTower() {
        addAction(new TowerGrabAction(msecTime, 1.0, bot));
        pause();
        return this;
    }

    public TowerBuilderAction liftTower() {
        return liftTower(0.95d);
    }

    public TowerBuilderAction lowerTower() {
        return liftTower(0.0d);
    }

    public TowerBuilderAction lowerTower(double position) {
        return liftTower(position);
    }

    public TowerBuilderAction liftTower(double position) {
        addAction(new TowerLiftAction(position, bot));
        pause();
        return this;
    }

    public TowerBuilderAction liftBlock(double position) {
        addAction(new BlockLiftAction(position, bot));
        pause();
        return this;
    }

    public TowerBuilderAction liftBlock() {
        return liftBlock(0.8d);

    }

    public TowerBuilderAction lowerBlock() {
        return liftBlock(0.0d);
    }

    public TowerBuilderAction extendBlock(double position) {
        addAction(new BlockExtendAction(position, bot));
        pause();
        return this;
    }

    public TowerBuilderAction extendBlock() {
        return extendBlock(1.0);
    }

    public TowerBuilderAction retractBlock() {
        return extendBlock(0.0);
    }

    public TowerBuilderAction grabBlock() {
        addAction(new BlockGrabAction(msecTime, 1.0d, bot));
        return this;
    }

    public TowerBuilderAction releaseBlock() {
        addAction(new BlockGrabAction(msecTime, 0.0d, bot));
        return this;
    }

    public TowerBuilderAction pause(long time) {
        addAction(new PauseAction(time, msecTime, bot));
        return this;
    }
    public TowerBuilderAction pause() {
        pause(250);
        return this;
    }
}
