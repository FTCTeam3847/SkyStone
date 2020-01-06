package org.firstinspires.ftc.teamcode.controller;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class RunToPositionControllerTest {
    @Test
    void blort() {
        AtomicReference<Double> getPower = new AtomicReference<>(0.0d);
        AtomicReference<Double> getPosition = new AtomicReference<>(0.0d);
        double gain = 4.0d;
        double minPower = 0.1d;
        double maxPower = 1.0d;
        double half = 0.5d;
        double tiny = 0.05d;

        RunToPositionController rtp = new RunToPositionController(getPower::get, getPosition::get, minPower, maxPower, gain);

        assertFalse(rtp.isBusy());

        rtp.setTarget(half);
        assertTrue(rtp.isBusy());

        rtp.stop();
        assertFalse(rtp.isBusy());

        rtp.setTarget(0.5);
        assertTrue(rtp.isBusy());
        assertThat(rtp.getControl(), isCloseTo(maxPower));

        rtp.setTarget(1.0d);
        assertTrue(rtp.isBusy());
        assertThat(rtp.getControl(), isCloseTo(maxPower));

        rtp.setTarget(2.0d);
        assertTrue(rtp.isBusy());
        assertThat(rtp.getControl(), isCloseTo(maxPower));

        rtp.setTarget(1/gain);
        assertTrue(rtp.isBusy());
        assertThat(rtp.getControl(), isCloseTo(maxPower));

        rtp.setTarget(half/gain);
        assertTrue(rtp.isBusy());
        assertThat(rtp.getControl(), isCloseTo(half));

        rtp.setTarget(minPower/gain);
        assertTrue(rtp.isBusy());
        assertThat(rtp.getControl(), isCloseTo(minPower));

        rtp.setTarget(tiny/gain);
        assertTrue(rtp.isBusy());
        assertThat(rtp.getControl(), isCloseTo(minPower));
    }

    private Matcher<Double> isCloseTo(double d) {
        return is(closeTo(d, 0.001));
    }
}