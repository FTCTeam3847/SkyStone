package org.firstinspires.ftc.teamcode.bot;

import org.firstinspires.ftc.teamcode.Trinkets.TowerGrabber;
import org.firstinspires.ftc.teamcode.action.TowerBuilderAction;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumDrive;

public interface SkystoneBot extends Bot, MecanumDrive {
    double getFieldRelativeHeading();

    default TowerGrabber getTowerGrabber() {
        return TowerGrabber.NIL;
    }
}
