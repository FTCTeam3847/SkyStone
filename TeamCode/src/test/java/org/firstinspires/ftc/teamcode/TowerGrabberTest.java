package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Trinkets.TowerGrabber;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
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

        assertThat(left.pos, is(closeTo(0.375, 0.001)));
        assertThat(right.pos, is(closeTo(0.475, 0.001)));

//        assertEquals(0.15, left.pos);
//        assertEquals(0.70, right.pos);
    }
}