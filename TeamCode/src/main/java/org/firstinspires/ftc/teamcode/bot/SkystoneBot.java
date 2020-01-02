package org.firstinspires.ftc.teamcode.bot;

import org.firstinspires.ftc.teamcode.Trinkets.TowerBuilder;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumDriveController;

public interface SkystoneBot extends Bot {
    double getFieldRelativeHeading();
    MecanumDriveController getMecanumDrive();

    default TowerBuilder getTowerBuilder() {
        return TowerBuilder.NIL;
    }
}
