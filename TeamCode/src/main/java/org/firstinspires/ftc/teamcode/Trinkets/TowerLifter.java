package org.firstinspires.ftc.teamcode.Trinkets;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.lang.String.format;
public class TowerLifter {

    //Consumer takes a variable and returns a void
    Consumer<Double> leftPower;
    Consumer<Double> rightPower;
    Consumer<Integer> motorLeft;
    Consumer<Integer> motorRight;
    Supplier<Integer> motorLeftSupplier;
    Supplier<Integer> motorRightSupplier;



    public TowerLifter(Consumer<Double> leftPower,
                       Consumer<Double> rightPower,
                       Consumer<Integer> motorLeft,
                       Consumer<Integer> motorRight,
                       Supplier<Integer> motorLeftSupplier,
                       Supplier<Integer> motorRightSupplier) {

        this.leftPower = leftPower;
        this.rightPower = rightPower;
        this.motorLeft = motorLeft;
        this.motorRight = motorRight;
        this.motorLeftSupplier = motorLeftSupplier;
        this.motorRightSupplier = motorRightSupplier;
    }

    //accept() takes a variable and returns a void
    public void lift () {
        motorLeft.accept(0);
        motorRight.accept(0);

        leftPower.accept(.3);
        rightPower.accept(.3);

    }

    public void down () {
        motorLeft.accept(2800);
        motorRight.accept(2800);

        leftPower.accept(.3);
        rightPower.accept(.3);

    }

    public void stop()
    {
        leftPower.accept(0.0);
        leftPower.accept(0.0);

        motorLeft.accept(motorLeftSupplier.get());
        motorRight.accept(motorRightSupplier.get());
    }

    @Override
    public String toString() {
        return format(
                Locale.ENGLISH,
                "left actual: %.2f, right actual: %.2f",
                motorLeftSupplier.get(),
                motorRightSupplier.get()
        );
    }
}
