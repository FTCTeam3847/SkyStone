package org.firstinspires.ftc.teamcode.controller;

import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumLocalizer;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Math.PI;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

class HeadingLocalizerTest {
    private static long MS = 1_000_000L;
    @Test
    void derp() {
        AtomicLong nanoTime = new AtomicLong(0L);
        AtomicReference<Double> absolute = new AtomicReference<>(0.0d);

        HeadingLocalizer h = new HeadingLocalizer(absolute::get);
        MecanumLocalizer m = new MecanumLocalizer(nanoTime::get, h::getLast, 1.0d);

        assertThat(h.getCurrent(), isCloseTo(0.0d));
        assertThat(h.getLast(), isCloseTo(0.0d));

        assertThat(m.getLast().heading, isCloseTo(0.0d));
        assertThat(m.getCurrent().heading, isCloseTo(0.0d));

        absolute.set(PI);
        nanoTime.set(1500*MS);
        assertThat(h.getLast(), isCloseTo(0.0d));
        assertThat(h.getCurrent(), isCloseTo(PI));
        assertThat(h.getLast(), isCloseTo(PI));

        assertThat(m.getLast().heading, isCloseTo(0.0d));
        assertThat(m.getCurrent().heading, isCloseTo(PI));
        assertThat(m.getLast().heading, isCloseTo(PI));


        h.calibrate(0.0);
        m.calibrate(FieldPosition.ORIGIN);
        assertThat(h.getLast(), isCloseTo(0.0));
        assertThat(h.getCurrent(), isCloseTo(0.0));
        assertThat(m.getCurrent().heading, isCloseTo(0.0d));
    }

    private Matcher<Double> isCloseTo(double d) {
        return is(closeTo(d, 0.001));
    }

}