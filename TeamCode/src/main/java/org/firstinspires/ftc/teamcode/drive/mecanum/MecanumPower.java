package org.firstinspires.ftc.teamcode.drive.mecanum;

import org.firstinspires.ftc.teamcode.polar.PolarCoord;

import java.util.Locale;

import static java.lang.Math.PI;
import static java.lang.String.format;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.fromXY;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtractRadians;

public class MecanumPower {
    public static final MecanumPower ZERO = new MecanumPower(new PolarCoord(0, 0), 0);
    public final PolarCoord strafe;
    public final double turn;

    public MecanumPower(PolarCoord strafe, double turn) {
        this.strafe = strafe;
        this.turn = turn;
    }

    public static MecanumPower fromXYTurn(double strafe_x, double strafe_y, double turn) {
        PolarCoord xypolar = fromXY(strafe_x, strafe_y);
        PolarCoord strafe = new PolarCoord(xypolar.radius, subtractRadians(xypolar.theta, PI / 2));
        return new MecanumPower(strafe, turn);
    }

    public static MecanumPower fwdleft(double power) {
        return new MecanumPower(new PolarCoord(power, 1*PI/4), 0);
    }

    public static MecanumPower bkwdleft(double power) {
        return new MecanumPower(new PolarCoord(power, 3*PI/4), 0);
    }

    public static MecanumPower bkwdright(double power) {
        return new MecanumPower(new PolarCoord(power, 5*PI/4), 0);
    }

    public static MecanumPower fwdright(double power) {
        return new MecanumPower(new PolarCoord(power, 7*PI/4), 0);
    }

    public static MecanumPower left(double power) {
        return fromXYTurn(-power, 0.0, 0.0);
    }
        public static MecanumPower right(double power) {
        return fromXYTurn(power, 0.0, 0.0);
    }

    public static MecanumPower fwd(double power) {
        return fromXYTurn(0.0, power, 0.0);
    }
        public static MecanumPower backwd(double power) {
        return fromXYTurn(0.0, -power, 0.0);
    }


    @Override
    public String toString() {
        return format(Locale.US, "strafe=%s, turn=%.2f", strafe, turn);
    }
}