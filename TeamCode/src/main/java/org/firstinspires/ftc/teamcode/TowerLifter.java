package org.firstinspires.ftc.teamcode;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.lang.String.format;
public class TowerLifter {

    //Consumer takes a variable and returns a void
    Consumer<Double> motorLeft;
    Consumer<Double> motorRight;
    Supplier<Double> motorLeftSupplier;
    Supplier<Double> motorRightSupplier;


    double constant = 1;
    double position;

    public TowerLifter(Consumer<Double> servoLeft,
                       Consumer<Double> servoRight,
                       Supplier<Double> servoLeftSupplier,
                       Supplier<Double> servoRightSupplier) {
        this.motorLeft = servoLeft;
        this.motorRight = servoRight;
        this.motorLeftSupplier = servoLeftSupplier;
        this.motorRightSupplier = servoRightSupplier;
    }

    //accept() takes a variable and returns a void
    public void setSpeed (double pos) {
        motorLeft.accept(pos * constant);
        motorRight.accept(pos * -constant);
        position = pos;
    }

    public void stop()
    {
        motorLeft.accept(0.0);
        motorRight.accept(0.0);
    }

    @Override
    public String toString() {
        return format(
                Locale.ENGLISH,
                "position: %.2f, left actual: %.2f, right actual: %.2f",
                position,
                motorLeftSupplier.get(),
                motorRightSupplier.get()
        );
    }
}
