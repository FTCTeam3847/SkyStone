package org.firstinspires.ftc.teamcode.action;

public interface RoboAction {
    long MSEC = 1_000;

    void start();

    void loop();

    void stop();

    default boolean isDone(){return isDone();}

    default boolean isRunning() {
        return isStarted() && !isDone();
    }

    boolean isStarted();
}
