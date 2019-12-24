package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Trinkets.TowerGrabber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TowerGrabberTest {
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
        FakeServo left = new FakeServo();
        FakeServo right = new FakeServo();

        TowerGrabber tg = new TowerGrabber(left::setPosition, right::setPosition, left::getPosition, right::getPosition);

        tg.setPosition(0.5);

        assertEquals(0.5, left.pos);
        assertEquals(-0.5, right.pos);
    }
}