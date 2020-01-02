package org.firstinspires.ftc.teamcode.controller;

public interface Controller<T, C> {
    void setTarget(T target);
    C getControl();
}
