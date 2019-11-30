package org.firstinspires.ftc.teamcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockExtenderTest {

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

        BlockExtender tg = new BlockExtender(servo::setPosition, servo::getPosition);

        tg.setPosition(0.5);

        assertEquals(0.5, servo.pos);
    }
}