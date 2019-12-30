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


        return this;
    }

    public TowerBuilderAction close(){


        return this;
    }
}
