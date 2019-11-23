package org.firstinspires.ftc.teamcode;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

class PositionControllerTest {
    @Test
    void test1() {
        Supplier<FieldPosition> loc = ()->new FieldPosition(45,4,0, "");
        PositionController pos = new PositionController(loc);
        FieldPosition targetLocation = new FieldPosition(0,0,0, "");
        pos.setTargetLocation(targetLocation);
        PolarCoord coord = pos.loop();
        System.out.println(coord);

    }
}