package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Trinkets.BlockGrabber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockGrabberTest {
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

        BlockGrabber bg = new BlockGrabber(servo::setPosition, servo::getPosition);

        bg.setPosition(0.5);

        assertEquals(0.5, servo.pos);
    }
}