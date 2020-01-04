package org.firstinspires.ftc.teamcode.action;

import java.util.Locale;
import java.util.function.Supplier;

public abstract class TimeAction implements RoboAction {
    private long startTime;
    private boolean isDone = false;
    private boolean isStarted = false;
    private final long dur;
    private final Supplier<Long> msecTime;

    public TimeAction(long dur, Supplier<Long> msecTime) {
        this.dur = dur;
        this.msecTime = msecTime;
    }

    @Override
    public TimeAction start() {
        startTime = msecTime.get();
        isStarted = true;
        return this;
    }

    @Override
    public void loop() {
        if (runtime() >= dur) {
            stop();
        }
    }

    @Override
    public void stop() {
        isStarted = false;
        isDone = true;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public boolean isStarted() {
        return isStarted;
    }

    long runtime() {
        if (isDone) return dur;
        else if (!isStarted) return 0;
        else return msecTime.get() - startTime;
    }

    @Override
    public String toString() {
        return String.format(Locale.US,
                "%s, %.2f",
                getClass().getSimpleName(),
                runtime() / 1000.0d
        );
    }

}
