package org.firstinspires.ftc.teamcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TowerLifterTest {

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

    @Test
    void setSpeed() {
        FakeDCMotor left = new FakeDCMotor();
        FakeDCMotor right = new FakeDCMotor();

        TowerLifter tl = new TowerLifter(left::setSpeed, right::setSpeed, left::getSpeed, right::getSpeed);

        tl.setSpeed(0.5);

        assertEquals(0.5, left.speed);
        assertEquals(-0.5, right.speed);
    }
}