package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;

import java.util.function.Supplier;

public class TowerBuilderAction extends SequentialAction{
    private final Supplier<Long> msecTime;
    private final SkystoneBot bot;


    public TowerBuilderAction(Supplier<Long> msecTime, SkystoneBot bot) {
        this.msecTime = msecTime;
        this.bot = bot;
    }


    public TowerBuilderAction open(){
        addAction(new TowerGrabberAction(msecTime, 0.0, bot));
        return this;
    }

    public TowerBuilderAction close(){
        addAction(new TowerGrabberAction(msecTime,1.0, bot));
        return this;
    }

    public TowerBuilderAction pause(long time) {
        addAction(new PauseAction(time, msecTime, bot));
        return this;
    }

}
