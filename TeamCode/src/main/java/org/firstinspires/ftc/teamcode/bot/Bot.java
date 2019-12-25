package org.firstinspires.ftc.teamcode.bot;

public interface Bot {
    default void init(){}
    default void init_loop(){}
    default void start(){}
    default void loop(){}
    default void stop(){}
}
