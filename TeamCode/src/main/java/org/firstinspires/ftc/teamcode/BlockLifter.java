package org.firstinspires.ftc.teamcode;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.lang.String.format;

public class BlockLifter {
    //Consumer takes a variable and returns a void
    Consumer<Double> servo;
    Supplier<Double> servoSupplier;


    double constant = 1;
    double position;

    public BlockLifter(Consumer<Double> servo, Supplier<Double> servoSupplier) {
        this.servo = servo;
        this.servoSupplier = servoSupplier;
    }

    //accept() takes a variable and returns a void
    public void setPosition (double pos) {
        servo.accept(pos * constant);
        position = pos;
    }

    @Override
    public String toString() {
        return format(
                Locale.ENGLISH,
                "position: %.2f, actual: %.2f",
                position,
                servoSupplier.get()
        );
    }

}
