package org.firstinspires.ftc.teamcode;

public interface SkystoneBot {

    void move(double x, double y, double turn);
    void move(DriveCommand driveCommand);
    void init();

}
