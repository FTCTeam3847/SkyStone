package org.firstinspires.ftc.teamcode.drive.mecanum;

public interface MecanumDrive {
    MecanumDrive NIL = power -> {};
    void setPower(MecanumPower mecanumPower);
    default void stop() {
        setPower(MecanumPower.ZERO);
    }
}
