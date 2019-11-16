package org.firstinspires.ftc.teamcode;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class PositionControllerTest {
    @Test
    void test1() {
        Supplier<LocationRotation> loc = ()->new LocationRotation(45,4,0);
        PositionController pos = new PositionController(loc);
        LocationRotation targetLocation = new LocationRotation(0,0,0);
        pos.setTargetLocation(targetLocation);
        PolarCoord coord = pos.loop();
        System.out.println(coord);

    }
}