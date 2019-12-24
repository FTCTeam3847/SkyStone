package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Trinkets.BlockManipulator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockManipulatorTest {
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
        FakeServo grabber = new FakeServo();
        FakeServo extender = new FakeServo();

        BlockManipulator bm = new BlockManipulator(grabber::setPosition, extender::setPosition, grabber::getPosition, extender::getPosition);

        bm.setPosition(0.5);

        assertEquals(-0.5, grabber.pos); //counterclockwise
        assertEquals(0.5, extender.pos); //clockwise
    }
}