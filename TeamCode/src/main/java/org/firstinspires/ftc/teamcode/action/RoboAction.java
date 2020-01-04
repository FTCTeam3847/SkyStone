package org.firstinspires.ftc.teamcode.action;

public interface RoboAction {
    long MSEC = 1_000;

    RoboAction start();

    void loop();

    void stop();

    boolean isDone();

    default boolean isRunning() {
        return isStarted() && !isDone();
    }

    boolean isStarted();
}
