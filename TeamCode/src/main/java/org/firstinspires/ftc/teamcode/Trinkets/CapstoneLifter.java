package org.firstinspires.ftc.teamcode.Trinkets;

import com.qualcomm.robotcore.hardware.CRServo;

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

    double power;

    public CapstoneLifter(Consumer<Double> CRServo, Supplier<Double> servoSupplier) {
        this.servo = CRServo;
        this.servoSupplier = servoSupplier;
    }

    //accept() takes a variable and returns a void
    public void setPower(double power) {
        power = clip(power, -1.0, 1.0);
        servo.accept(power);
        this.power = power;
    }

    public double getPower() {
        return power;
    }

    @Override
    public String toString() {
        return format(
                Locale.ENGLISH,
                "powr:%.2f,act:%.2f",
                power,
                servoSupplier.get()
        );
    }
}
