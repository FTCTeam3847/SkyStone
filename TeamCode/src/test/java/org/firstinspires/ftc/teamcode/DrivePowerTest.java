package org.firstinspires.ftc.teamcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DrivePowerTest {
    public static DrivePower TURN_LEFT = new DrivePower(1.0d, 1.0d, -1.0d, -1.0d);

    @Test
    void combine() {
        assertEquals(DrivePower.combine(DrivePower.ZERO, TURN_LEFT), TURN_LEFT);
    }
}