package org.firstinspires.ftc.teamcode.drive.mecanum;

import org.firstinspires.ftc.teamcode.polar.PolarCoord;

public interface MecanumDrive {
    void move(MecanumPower mecanumPower);

    default void turn(double turnPower)
    {
        move(new MecanumPower(PolarCoord.ORIGIN, turnPower));
    }

    default void strafe(PolarCoord strafePower)
    {
        move(new MecanumPower(strafePower, 0.0));
    }
}
