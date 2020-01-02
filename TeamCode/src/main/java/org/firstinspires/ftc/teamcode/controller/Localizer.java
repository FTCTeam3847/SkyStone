package org.firstinspires.ftc.teamcode.controller;

public interface Localizer<T> {
    T getCurrent();
    T getLast();
    void calibrate(T t);
}
