package org.firstinspires.ftc.teamcode.bot;

import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.Trinkets.TowerBuilder;
import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.controller.Localizer;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumDrive;

public interface SkystoneBot extends Bot {

    void setInnerSkystone(int innerSkystone); //sets both skystone locations

    void setOuterSkystone(int outerSkystone); //sets both skystone locations

    int getInnerSkystone(); //gets inner skystone location

    int getOuterSkystone(); //gets outer skystone location

    ColorSensor getColorSensor();

    double getFieldRelativeHeading();

    Localizer<FieldPosition> getLocalizer();

    default MecanumDrive getMecanumDrive() {
        return MecanumDrive.NIL;
    }

    default TowerBuilder getTowerBuilder() {
        return TowerBuilder.NIL;
    }

    double getAutonomousSpeed();

    boolean isInMotion();

    double getRangeLeft();

    double getRangeRight();
}
