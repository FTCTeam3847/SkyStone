package org.firstinspires.ftc.teamcode;

import org.junit.jupiter.api.Test;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

import static java.lang.Math.PI;
import static org.firstinspires.ftc.teamcode.PolarUtil.subtract;

class PolarUtilTest {
    @Property(seed="1825301")
    boolean subtract_reverse_should_have_same_radius(
            @ForAll("radius") double r1,
            @ForAll("theta") double t1,
            @ForAll("radius") double r2,
            @ForAll("theta") double t2
    ) {
        PolarCoord a = new PolarCoord(r1, t1);
        PolarCoord b = new PolarCoord(r2, t2);
        PolarCoord aMinusB = subtract(a, b);
        PolarCoord bMinusA = subtract(b, a);
        if (a.equals(b)) {
            return equalRound2(aMinusB.radius, 0.0d);
        } else {
            return equalRound2(aMinusB.radius, bMinusA.radius);
        }
    }

    @Property(seed="1825301")
    boolean subtract_reverse_should_have_theta_sum_PI(
            @ForAll("radius") double r1,
            @ForAll("theta") double t1,
            @ForAll("radius") double r2,
            @ForAll("theta") double t2
    ) {
        PolarCoord a = new PolarCoord(r1, t1);
        PolarCoord b = new PolarCoord(r2, t2);
        PolarCoord aMinusB = subtract(a, b);
        PolarCoord bMinusA = subtract(b, a);

        if (a.equals(b)) {
            return equalRound2(aMinusB.theta, 0.0d);
        } else {
            return equalRound2(aMinusB.theta + bMinusA.theta, PI);
        }
    }


    @Property(seed="1825301")
    boolean subtract_itself_should_equal_zero(
            @ForAll("radius") double r1,
            @ForAll("theta") double t1

    ) {
        PolarCoord a = new PolarCoord(r1, t1);
        PolarCoord aMinusA = subtract(a, a);
        return equalRound2(aMinusA.theta, 0.0d);
    }

    boolean equalRound2(double d1, double d2) {
        return round2(d1) == round2(d2);
    }

    double round2(double d) {
        return round(d * 100.0d) / 100.0d;
    }

    @Provide
    Arbitrary<Double> theta() {
        return Arbitraries.doubles().between(0.0d, 2.0* PI);
    }

    static double MAX_RADIUS = sqrt(pow(72, 2) + pow(72, 2)); // field is 144 inches square

    @Provide
    Arbitrary<Double> radius() {
        return Arbitraries.doubles().between(0.0d, MAX_RADIUS);
    }

//    @Test
//    void oldStuff() {
//        PolarCoord p1 = new PolarCoord(1, ((9.0d/8.0d) * PI));
//        PolarCoord p2 = new PolarCoord(1, ((11.0d/8.0d) * PI));
//
//        PolarCoord p3 = subtract(p1, p2);
//
//        System.out.println(p3);
//
//
//        PolarCoord p4 = new PolarCoord(1, 0);
//        PolarCoord p5 = new PolarCoord(1, PI/2);
//
//        PolarCoord p6 = subtract(p4, p5);
//
//        System.out.println(p6);
//
//
//        PolarCoord p7 = new PolarCoord(1, ((9.0d/8.0d) * PI));
//        PolarCoord p8 = new PolarCoord(1, ((1.0d/4.0d) * PI));
//
//        PolarCoord p9 = subtract(p7, p8);
//
//        System.out.println(p9);
//
//
//        PolarCoord p10 = new PolarCoord(1, ((9.0d/8.0d) * PI));
//        PolarCoord p11 = new PolarCoord(1, ((1.0d/4.0d) * PI));
//
//        PolarCoord p12 = subtract(p11, p10);
//
//        System.out.println(p12);
//        double t = p12.theta+p9.theta;
//        System.out.println("theta:" + t);
//    }
}