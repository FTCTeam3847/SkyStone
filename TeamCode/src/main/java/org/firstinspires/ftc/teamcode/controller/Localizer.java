package org.firstinspires.ftc.teamcode.controller;

public interface Localizer<T> {
    T getCurrent();
    void calibrate(T t);
}
