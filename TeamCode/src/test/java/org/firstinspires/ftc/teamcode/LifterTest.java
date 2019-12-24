package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Trinkets.Lifter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LifterTest {
    class FakeDCMotor {
        double speed;

        public void setSpeed (double pos) {
            this.speed = pos;
        }

        public double getSpeed()
        {
            return speed;
        }
    }

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
    void setSpeed() {
        FakeServo servo = new FakeServo();
        FakeDCMotor left = new FakeDCMotor();
        FakeDCMotor right = new FakeDCMotor();

        Lifter l = new Lifter(servo::setPosition, left::setSpeed, right::setSpeed, servo::getPosition, left::getSpeed, right::getSpeed);

        l.setPositionSpeed(0.5, 0.5);

        assertEquals(0.5, servo.pos); //clockwise
        assertEquals(-0.5, left.speed); //counterclockwise
        assertEquals(0.5, right.speed); //clockwise
    }

}