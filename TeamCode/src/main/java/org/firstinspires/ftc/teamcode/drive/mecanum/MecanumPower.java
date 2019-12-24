package org.firstinspires.ftc.teamcode.drive.mecanum;

import org.firstinspires.ftc.teamcode.polar.PolarCoord;

public class MecanumPower {
    public static final MecanumPower ZERO = new MecanumPower(new PolarCoord(0, 0), 0);
    public final PolarCoord strafe;
    public final double turn;

    public MecanumPower(PolarCoord strafe, double turn) {
        this.strafe = strafe;
        this.turn = turn;
    }
}