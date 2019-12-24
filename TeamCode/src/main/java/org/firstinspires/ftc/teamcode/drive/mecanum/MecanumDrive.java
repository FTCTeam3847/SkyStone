package org.firstinspires.ftc.teamcode.drive.mecanum;

import org.firstinspires.ftc.teamcode.polar.PolarCoord;

import static java.lang.Math.PI;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.fromXY;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtractRadians;

public interface MecanumDrive {
    void move(MecanumPower mecanumPower);

    default void move(double strafe_x, double strafe_y, double turn) {
        PolarCoord xypolar = fromXY(strafe_x, strafe_y);
        PolarCoord strafe = new PolarCoord(xypolar.radius, subtractRadians(xypolar.theta, PI / 2));
        move(new MecanumPower(strafe, turn));
    }
}
