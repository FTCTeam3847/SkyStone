package org.firstinspires.ftc.teamcode.action;

public interface RoboAction {
    long MSEC = 1_000;

    void start();

    void loop();

    void stop();

    boolean isDone();
}
