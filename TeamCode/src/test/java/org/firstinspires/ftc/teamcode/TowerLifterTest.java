package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Trinkets.TowerLifter;
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
        public int getPosition()
        {
            return 0;
        }
    }

    @Test
    void setSpeed() {
        FakeDCMotor left = new FakeDCMotor();
        FakeDCMotor right = new FakeDCMotor();

        TowerLifter tl = new TowerLifter(
                left::setSpeed,
                right::setSpeed,
                left::getPosition,
                right::getPosition
        );

        tl.lift(0.5 ,-0.5);

        assertEquals(0.5, left.speed);
        assertEquals(-0.5, right.speed);
    }
}