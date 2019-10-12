package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

public class AcceleratingDcMotor extends DelegatingDcMotor {
    final double MAX_POWER_DELTA = 0.1;
    final double MAX_POWER_TIME_MS = 10000;
    public AcceleratingDcMotor(DcMotor delegate) {
        super(delegate);
        lastNanos = System.nanoTime();
        desiredPower = super.getPower();

    }

    private double desiredPower;
    double lastNanos;

    @Override
    public void setPower(double power) {
        desiredPower = power;
        update();
    }

    @Override
    public double getPower() {
        return desiredPower;
    }

    public void update() {
        double currentPower = super.getPower();
        double currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastNanos) / 1000.0D;
        double candidateDeltaPower = (MAX_POWER_DELTA * deltaTime)/MAX_POWER_TIME_MS;
        double actualDeltaPower = minabs(candidateDeltaPower, MAX_POWER_DELTA);
        lastNanos = currentTime;
        double actualPower = minabs(desiredPower, currentPower + actualDeltaPower);
        super.setPower(actualPower);
    }

    public static double minabs(double a, double b) {
        return (abs(a) < abs(b)) ? a : b;
    }
}