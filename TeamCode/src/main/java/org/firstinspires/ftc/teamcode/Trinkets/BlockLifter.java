package org.firstinspires.ftc.teamcode.Trinkets;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.lang.String.format;

public class BlockLifter {
    //Consumer takes a variable and returns a void
    Consumer<Double> servoLeft;
    Consumer<Double> servoRight;
    Supplier<Double> servoSupplierLeft;
    Supplier<Double> servoSupplierRight;


    double constant = 1;
    double power;

    public BlockLifter(Consumer<Double> servoLeft,
                       Consumer<Double> servoRight,
                       Supplier<Double> servoSupplierLeft,
                       Supplier<Double> servoSupplierRight) {
        this.servoLeft = servoLeft;
        this.servoRight = servoRight;
        this.servoSupplierLeft = servoSupplierLeft;
        this.servoSupplierRight = servoSupplierRight;
    }

    //accept() takes a variable and returns a void
    public void setPower (double power) {
        servoLeft.accept(power * constant);
        servoRight.accept(power * constant);
        this.power = power;
    }

    @Override
    public String toString() {
        return format(
                Locale.ENGLISH,
                "position: %.2f, left actual: %.2f, right actual: %.2f",
                power,
                servoSupplierLeft.get(),
                servoSupplierRight.get()
        );
    }

}
