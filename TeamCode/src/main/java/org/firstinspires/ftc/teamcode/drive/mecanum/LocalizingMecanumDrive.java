package org.firstinspires.ftc.teamcode.drive.mecanum;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.signum;

public class LocalizingMecanumDrive implements MecanumDrive {
    private final MecanumDrive mecanumDrive;
    private final MecanumLocalizer mecanumLocalizer;

    public LocalizingMecanumDrive(MecanumDrive mecanumDrive, MecanumLocalizer mecanumLocalizer) {
        this.mecanumDrive = mecanumDrive;
        this.mecanumLocalizer = mecanumLocalizer;
    }

    @Override
    public void setPower(MecanumPower mecanumPower) {
        mecanumPower = minimumLocalizable(mecanumPower);

        mecanumLocalizer.setPower(mecanumPower);
        mecanumDrive.setPower(mecanumPower);
    }

    private MecanumPower minimumLocalizable(MecanumPower mecanumPower) {
        double radius = mecanumPower.strafe.radius;
        double theta = mecanumPower.strafe.theta;
        double turn = mecanumPower.turn;

        radius = (radius < 0.05) ? 0.0d : max(0.1, radius);
        turn = (abs(turn) < 0.05) ? 0.0d : signum(turn) * max(0.1, abs(turn));

        return new MecanumPower(radius, theta, turn);
    }


}
