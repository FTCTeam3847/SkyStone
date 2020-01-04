package org.firstinspires.ftc.teamcode;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

import org.firstinspires.ftc.teamcode.polar.PolarCoord;
import org.firstinspires.ftc.teamcode.polar.PolarUtil;
import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static org.firstinspires.ftc.teamcode.polar.PolarCoord.ORIGIN;
import static org.firstinspires.ftc.teamcode.polar.PolarCoord.polar;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.add;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtract;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtractRadians;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PolarUtilTest {
    @Property
    void relative_coords_between_two_points_radii_should_be_equal_and_theta_should_differ_by_PI(
            @ForAll("radius") double r1,
            @ForAll("theta") double t1,
            @ForAll("radius") double r2,
            @ForAll("theta") double t2
    ) {
        PolarCoord p1 = polar(r1, t1);
        PolarCoord p2 = polar(r2, t2);
        PolarCoord fromP1ToP2 = PolarUtil.subtract(p1, p2);
        PolarCoord fromP2ToP1 = PolarUtil.subtract(p2, p1);
        if (p1.equals(p2)) {
            assertThat(fromP1ToP2, is(equalTo(fromP2ToP1)));
            assertThat(
                    "Relative coord between equal points should be (0,0)",
                    fromP1ToP2,
                    is(equalTo(ORIGIN)));
        } else {
            assertThat(fromP1ToP2.radius, is(closeTo(fromP1ToP2.radius)));
            assertThat(
                    "Relative angles between two points should be PI radians (180 degrees) apart.",
                    subtractRadians(fromP1ToP2.theta, fromP2ToP1.theta),
                    is(closeTo(PI))
            );
        }
    }

    // FTC fields are 144 inches square, origin at the center,
    private static double MAX_RADIUS = sqrt(pow(72.0d, 2) + pow(72.0d, 2));

    @Provide()
    Arbitrary<Double> radius() {
        return Arbitraries.doubles().between(0.0d, MAX_RADIUS);
    }

    @Provide
    Arbitrary<Double> theta() {
        return Arbitraries.doubles().between(0.0d, 2.0d * PI);
    }

    public static org.hamcrest.Matcher<java.lang.Double> closeTo(double operand) {
        return org.hamcrest.number.IsCloseTo.closeTo(operand, 0.001);
    }


    PolarCoord NE = polar(1.0, 1*PI/4);
    PolarCoord NW = polar(1.0, 3*PI/4);
    PolarCoord SW = polar(1.0, 5*PI/4);
    PolarCoord SE = polar(1.0, 7*PI/4);

    @Test
    void testAdd() {
        PolarCoord a = add(ORIGIN, NE);
        assertEquals(NE, a);

        PolarCoord b = add(a, NW);
        assertThat(b.radius, is(closeTo(sqrt(2))));
        assertThat(b.theta, is(closeTo(PI/2)));

        PolarCoord c = add(b, SW);
        assertThat(c.radius, is(closeTo(1.0d)));
        assertThat(c.theta, is(closeTo(3*PI/4)));

        PolarCoord d = add(c, SE);
        assertThat(d.radius, is(closeTo(ORIGIN.radius)));
    }

    @Test
    void outAndBack() {
        PolarCoord a = add(ORIGIN, NE);
        PolarCoord b = add(a, NW);
        PolarCoord actual = subtract(b, a);
        assertThat(actual.radius, is(closeTo(1.0d)));
        assertThat(actual.theta, is(closeTo(SE.theta)));
    }
}