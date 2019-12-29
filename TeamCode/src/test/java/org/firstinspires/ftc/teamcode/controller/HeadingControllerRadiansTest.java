package org.firstinspires.ftc.teamcode.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeadingControllerRadiansTest {

    @Test
    void calcAngularError() {
        assertEquals(0, HeadingControllerRadians.calcAngularError(0,0,3.0));
    }

    @Test
    void calcAngularProportion()
    {

        assertEquals(0.0, HeadingControllerRadians.calcAngularProportion(0));
    }
}