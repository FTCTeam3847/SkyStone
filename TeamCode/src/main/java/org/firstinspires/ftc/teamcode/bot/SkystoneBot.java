package org.firstinspires.ftc.teamcode.bot;

import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Trinkets.TowerBuilder;
import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.controller.Localizer;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumDrive;

import java.util.function.Supplier;

public interface SkystoneBot extends Bot {
    double getFieldRelativeHeading();

    Localizer<FieldPosition> getLocalizer();

    default MecanumDrive getMecanumDrive() {
        return MecanumDrive.NIL;
    }

    default TowerBuilder getTowerBuilder() {
        return TowerBuilder.NIL;
    }

    TFObjectDetector getTfod();
}
