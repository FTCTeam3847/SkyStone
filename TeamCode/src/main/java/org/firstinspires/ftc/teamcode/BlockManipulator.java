package org.firstinspires.ftc.teamcode;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.lang.String.format;

public class BlockManipulator {
    //Consumer takes a variable and returns a void
    Consumer<Double> servoGrabber;
    Consumer<Double> servoExtender;
    Supplier<Double> servoGrabberSupplier;
    Supplier<Double> servoExtenderSupplier;


    double constant = 1;
    double position;

    public BlockManipulator(Consumer<Double> servoGrabber,
                        Consumer<Double> servoExtender,
                        Supplier<Double> servoGrabberSupplier,
                        Supplier<Double> servoExtenderSupplier) {
        this.servoGrabber = servoGrabber;
        this.servoExtender = servoExtender;
        this.servoGrabberSupplier = servoGrabberSupplier;
        this.servoExtenderSupplier = servoExtenderSupplier;
    }

    //accept() takes a variable and returns a void
    public void setPosition (double pos) {
        servoGrabber.accept(pos * -constant); //counterclockwise
        servoExtender.accept(pos * constant); //clockwise
        position = pos;
    }

    @Override
    public String toString() {
        return format(
                Locale.ENGLISH,
                "position: %.2f, grabber actual: %.2f, extender actual: %.2f",
                position,
                servoGrabberSupplier.get(),
                servoExtenderSupplier.get()
        );
    }
}
