package org.firstinspires.ftc.teamcode;

import java.util.function.Consumer;

public class TowerGrabber {
    //Consumer takes a variable and returns a void
    Consumer<Double> ServoA;
    Consumer<Double> ServoB;

    double constant = 1;

    public TowerGrabber(Consumer<Double> ServoA, Consumer<Double> ServoB) {
        this.ServoA = ServoA;
        this.ServoB = ServoB;
    }

    //accept() takes a variable and returns a void
    public void setPosition (double pos) {
        ServoA.accept(pos * constant);
        ServoB.accept(pos * -constant);
    }
}
