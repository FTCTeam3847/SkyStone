package org.firstinspires.ftc.teamcode;

import java.util.function.Supplier;

public class MoveAction implements RoboAction {
    private double startTime;
    private boolean isDone = false;
    private DriveCommand command;
    private double dur;
    private Supplier<Long> timer;
    private boolean started = false;

    private long SECOND = 1_000_000_000;

    private SkystoneBot bot;

    public MoveAction(double dur, DriveCommand command, Supplier<Long> timer, SkystoneBot bot) {
        this.command = command;
        this.dur = dur * SECOND;
        this.timer = timer;
        this.bot = bot;
    }

    public void start() {
        startTime = timer.get();
        started = true;
    }

    public void loop() {
        if (started) {
            double currentTime = timer.get();
            if (currentTime < startTime + (dur)) {
                bot.move(command);
            } else {
                stop();
                started = false;
                bot.move(new DriveCommand(new PolarCoord(0, 0), 0));
            }
        } else {
            bot.move(new DriveCommand(new PolarCoord(0, 0), 0));
        }
    }

    public void stop() {
        isDone = true;
    }

    public boolean isDone() {
        return isDone;
    }

    public boolean started() {
        return started;
    }

}
