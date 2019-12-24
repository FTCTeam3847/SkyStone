package org.firstinspires.ftc.teamcode.Trinkets;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.lang.String.format;

public class Lifter {
    //Consumer takes a variable and returns a void
    Consumer<Double> servo;
    Supplier<Double> servoSupplier;
    Consumer<Double> motorLeft;
    Consumer<Double> motorRight;
    Supplier<Double> motorLeftSupplier;
    Supplier<Double> motorRightSupplier;

    double constant = 1;
    double position;
    double speed;

    public Lifter(Consumer<Double> servo,
                  Consumer<Double> motorLeft,
                  Consumer<Double> motorRight,
                  Supplier<Double> servoSupplier,
                  Supplier<Double> motorLeftSupplier,
                  Supplier<Double> motorRightSupplier) {
        this.servo = servo;
        this.motorLeft = motorLeft;
        this.motorRight = motorRight;

        this.servoSupplier = servoSupplier;
        this.motorLeftSupplier = motorLeftSupplier;
        this.motorRightSupplier = motorRightSupplier;
    }

    //accept() takes a variable and returns a void
    public void setPositionSpeed(double pos, double speed) {
        servo.accept(pos * constant);

        motorLeft.accept(speed * -constant); //counterclockwise
        motorRight.accept(speed * constant); //clockwise

        this.speed = speed;
        position = pos;
    }


    @Override
    public String toString() {
        return format(
                Locale.ENGLISH,
                "position: %.2f, speed: %.2f, servoLeft actual: %.2f, left motor actual: %.2f, right motor actual: %.2f",
                position,
                speed,
                servoSupplier.get(),
                motorLeftSupplier.get(),
                motorRightSupplier.get()
        );
    }
}
