package org.firstinspires.ftc.teamcode.drive.mecanum;

public class LocalizingMecanumDrive implements MecanumDrive {
    private final MecanumDrive mecanumDrive;
    private final MecanumLocalizer mecanumLocalizer;

    public LocalizingMecanumDrive(MecanumDrive mecanumDrive, MecanumLocalizer mecanumLocalizer) {
        this.mecanumDrive = mecanumDrive;
        this.mecanumLocalizer = mecanumLocalizer;
    }

    @Override
    public void setPower(MecanumPower mecanumPower) {
        mecanumLocalizer.setPower(mecanumPower);
        mecanumDrive.setPower(mecanumPower);
    }

}
