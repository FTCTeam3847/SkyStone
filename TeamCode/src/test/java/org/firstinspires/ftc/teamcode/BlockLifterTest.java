package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Trinkets.BlockLifter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockLifterTest {
    class FakeServo {
        double pos;

        public void setPosition (double pos) {
            this.pos = pos;
        }

        public double getPosition()
        {
            return pos;
        }
    }

    @Test
    void setPosition() {
        FakeServo servo = new FakeServo();

        BlockLifter bl = new BlockLifter(servo::setPosition, servo::getPosition);

        bl.setPosition(0.5);

        assertEquals(0.5, servo.pos);
    }
}