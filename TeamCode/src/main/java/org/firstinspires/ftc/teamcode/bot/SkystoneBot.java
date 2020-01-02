package org.firstinspires.ftc.teamcode.bot;

import org.firstinspires.ftc.teamcode.Trinkets.TowerBuilder;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumDrive;

public interface SkystoneBot extends Bot {
    double getFieldRelativeHeading();

    default MecanumDrive getMecanumDrive() {
        return MecanumDrive.NIL;
    }

    default TowerBuilder getTowerBuilder() {
        return TowerBuilder.NIL;
    }
}
