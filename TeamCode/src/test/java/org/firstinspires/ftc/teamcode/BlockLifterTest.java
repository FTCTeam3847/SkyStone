package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Trinkets.BlockLifter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        FakeServo servoLeft = new FakeServo();
        FakeServo servoRight = new FakeServo();

        BlockLifter bl = new BlockLifter(
                servoLeft::setPosition,
                servoRight::setPosition,
                servoLeft::getPosition,
                servoRight::getPosition
        );

        bl.setPower(0.5);

        assertEquals(0.5, servoLeft.pos);
    }
}