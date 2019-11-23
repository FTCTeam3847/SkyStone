package org.firstinspires.ftc.teamcode;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static org.firstinspires.ftc.teamcode.PolarUtil.subtractRadians;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class PolarUtilTest {
    @Property
    void relative_coords_between_two_points_radii_should_be_equal_and_theta_should_differ_by_PI(
            @ForAll("radius") double r1,
            @ForAll("theta") double t1,
            @ForAll("radius") double r2,
            @ForAll("theta") double t2
    ) {
        PolarCoord p1 = new PolarCoord(r1, t1);
        PolarCoord p2 = new PolarCoord(r2, t2);
        PolarCoord fromP1ToP2 = PolarUtil.fromTo(p1, p2);
        PolarCoord fromP2ToP1 = PolarUtil.fromTo(p2, p1);
        if (p1.equals(p2)) {
            assertThat(fromP1ToP2, is(equalTo(fromP2ToP1)));
            assertThat(
                    "Relative coord between equal points should be (0,0)",
                    fromP1ToP2,
                    is(equalTo(PolarUtil.ORIGIN)));
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
        return org.hamcrest.number.IsCloseTo.closeTo(operand, 0.0001);
    }
}