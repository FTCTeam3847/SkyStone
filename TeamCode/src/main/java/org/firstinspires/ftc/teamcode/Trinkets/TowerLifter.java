package org.firstinspires.ftc.teamcode.Trinkets;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.lang.String.format;
public class TowerLifter {

    //Consumer takes a variable and returns a void
    Consumer<Double> leftPower;
    Consumer<Double> rightPower;
    Supplier<Integer> motorLeftSupplier;
    Supplier<Integer> motorRightSupplier;



    public TowerLifter(Consumer<Double> leftPower,
                       Consumer<Double> rightPower,
                       Supplier<Integer> motorLeftSupplier,
                       Supplier<Integer> motorRightSupplier) {

        this.leftPower = leftPower;
        this.rightPower = rightPower;
        this.motorLeftSupplier = motorLeftSupplier;
        this.motorRightSupplier = motorRightSupplier;
    }

    //accept() takes a variable and returns a void
    public void lift () {
        leftPower.accept(-.3);
        rightPower.accept(-.3);

    }

    public void down () {
        leftPower.accept(.3);
        rightPower.accept(.3);

    }

    public void stop()
    {
        leftPower.accept(0.0);
        leftPower.accept(0.0);
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
