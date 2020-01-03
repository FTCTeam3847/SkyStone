package org.firstinspires.ftc.teamcode.Trinkets;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.qualcomm.robotcore.util.Range.clip;
import static com.qualcomm.robotcore.util.Range.scale;
import static java.lang.String.format;

public class CapstoneLifter {
    private static final Consumer<Double> NOOP = unused -> {
    };
    private static final Supplier<Double> NOOPS = () -> 0.0d;
    public static CapstoneLifter NIL = new CapstoneLifter(NOOP,NOOPS);
    //Consumer takes a variable and returns a void
    Consumer<Double> servo;
    Supplier<Double> servoSupplier;

    double position;

    double lifterDown = 0.0;
    double lifterUp = 1.0;

    public CapstoneLifter(Consumer<Double> servo, Supplier<Double> servoSupplier) {
        this.servo = servo;
        this.servoSupplier = servoSupplier;
    }

    //accept() takes a variable and returns a void
    public void setPosition(double pos) {
        pos = clip(pos, 0.0, 1.0);
        servo.accept(scale(pos,0.0,1.0, lifterDown, lifterUp));
        position = pos;
    }

    public double getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return format(
                Locale.ENGLISH,
                "pos:%.2f,act:%.2f",
                position,
                servoSupplier.get()
        );
    }
}
