package org.firstinspires.ftc.teamcode.drive.mecanum;

import org.firstinspires.ftc.teamcode.polar.PolarCoord;
import org.firstinspires.ftc.teamcode.polar.PolarUtil;

import java.util.Locale;
import java.util.Objects;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.Math.signum;
import static java.lang.String.format;
import static org.firstinspires.ftc.teamcode.polar.PolarCoord.polar;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.fromXY;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtractRadians;

public class MecanumPower {
    public static final MecanumPower ZERO = mecanumPower(0, 0, 0);
    public final PolarCoord strafe;
    public final double turn;

    public static MecanumPower mecanumPower(double radius, double theta, double turn) {
        return mecanumPower(polar(radius, theta), turn);
    }

    public static MecanumPower mecanumPower(PolarCoord strafe, double turn) {
        return new MecanumPower(strafe, turn);
    }

    public MecanumPower(PolarCoord strafe, double turn) {
        this.strafe = strafe;
        this.turn = turn;
    }

    public static MecanumPower fromGamepadXYTurn(double strafe_x, double strafe_y, double turn) {
        PolarCoord xypolar = fromXY(strafe_x, strafe_y);
        PolarCoord strafe = polar(xypolar.radius, subtractRadians(xypolar.theta, PI / 2));
        return new MecanumPower(strafe, turn);
    }

    @Override
    public String toString() {
        return format(Locale.US, "strafe=%s, turn=%.2f", strafe, turn);
    }

    public MecanumPower scale(double scale) {
        return mecanumPower(strafe.radius * scale, strafe.theta, turn * scale);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MecanumPower that = (MecanumPower) o;
        return Double.compare(that.turn, turn) == 0 &&
                Objects.equals(strafe, that.strafe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strafe, turn);
    }

    public MecanumPower add(MecanumPower mecanumPower) //adds two mecanumPowers
    {
        PolarCoord strafe = PolarUtil.add(this.strafe, mecanumPower.strafe);
        if(abs(strafe.radius) > 1) // [-1 to 1]
        {
            strafe = polar(signum(strafe.radius), strafe.theta);
        }

        double turn = this.turn + mecanumPower.turn;
        if(abs(turn) > 1) //[-1 to 1]
        {
            turn = signum(turn);
        }

        return mecanumPower(strafe, turn);
    }
}