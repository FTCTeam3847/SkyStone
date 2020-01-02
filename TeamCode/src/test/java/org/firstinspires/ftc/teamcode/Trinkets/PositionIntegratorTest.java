package org.firstinspires.ftc.teamcode.Trinkets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionIntegratorTest {
    @Test
    void derp() {
        final double MAX_POSITION = 2200.0d;
        final PositionIntegrator position = new PositionIntegrator(System::nanoTime, 0.0d, MAX_POSITION);
        position.getPosition();
    }
}