package org.firstinspires.ftc.teamcode.Trinkets;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.qualcomm.robotcore.util.Range.clip;
import static java.lang.String.format;

public class BlockLifter {
    public static final BlockLifter NIL = new BlockLifter((x) -> {},(x) -> {},()->0.0d,()->0.0d);


    //Consumer takes a variable and returns a void
    Consumer<Double> leftPower;
    Consumer<Double> rightPower;
    Supplier<Double> leftPowerSupplier;
    Supplier<Double> rightPowerSupplier;

    double power;

    public BlockLifter(Consumer<Double> leftPower,
                       Consumer<Double> rightPower,
                       Supplier<Double> leftPowerSupplier,
                       Supplier<Double> rightPowerSupplier) {
        this.leftPower = leftPower;
        this.rightPower = rightPower;
        this.leftPowerSupplier = leftPowerSupplier;
        this.rightPowerSupplier = rightPowerSupplier;
    }

    //accept() takes a variable and returns a void
    public void setPower(double power) {
        power = clip(power, -1.0d, 1.0d);
        leftPower.accept(power);
        rightPower.accept(power);
        this.power = power;
    }

    @Override
    public String toString() {
        return format(
                Locale.US,
                "pwr:%.2f, L%.2f R%.2f",
                power,
                leftPowerSupplier.get(),
                rightPowerSupplier.get()
        );
    }

}
