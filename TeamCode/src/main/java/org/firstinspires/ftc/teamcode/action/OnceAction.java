package org.firstinspires.ftc.teamcode.action;

public class OnceAction implements RoboAction {
    private final Runnable runnable;

    private boolean isStarted = false;
    private boolean isDone = false;

    public OnceAction(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void start() {
        isStarted = true;
    }

    @Override
    public void loop() {
        runnable.run();
        stop();
    }

    @Override
    public void stop() {
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
}
