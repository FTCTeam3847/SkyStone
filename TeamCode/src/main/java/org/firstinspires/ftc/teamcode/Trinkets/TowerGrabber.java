package org.firstinspires.ftc.teamcode.Trinkets;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.lang.String.format;

public class TowerGrabber {
    //Consumer takes a variable and returns a void
    Consumer<Double> servoLeft;
    Consumer<Double> servoRight;
    Supplier<Double> servoLeftSupplier;
    Supplier<Double> servoRightSupplier;


    double constant = 1;
    double position;

    public TowerGrabber(Consumer<Double> servoLeft,
                        Consumer<Double> servoRight,
                        Supplier<Double> servoLeftSupplier,
                        Supplier<Double> servoRightSupplier) {
        this.servoLeft = servoLeft;
        this.servoRight = servoRight;
        this.servoLeftSupplier = servoLeftSupplier;
        this.servoRightSupplier = servoRightSupplier;
    }

    //accept() takes a variable and returns a void
    public void setPosition (double pos) {
        servoLeft.accept(pos * constant); //clockwise
        servoRight.accept(pos * -constant); //counterclockwise
        position = pos;
    }

    @Override
    public String toString() {
        return format(
                Locale.ENGLISH,
                "position: %.2f, left actual: %.2f, right actual: %.2f",
                position,
                servoLeftSupplier.get(),
                servoRightSupplier.get()
        );
    }

}
