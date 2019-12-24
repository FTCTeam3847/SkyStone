package org.firstinspires.ftc.teamcode.controller;

public interface Controller<S, T, C> extends Sensor<S> {
    void setTarget(T target);

    C getControl();
}
