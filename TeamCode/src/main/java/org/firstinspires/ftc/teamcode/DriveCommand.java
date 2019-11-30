package org.firstinspires.ftc.teamcode;

public class DriveCommand
{
    PolarCoord strafe;
    double turn;

    public DriveCommand(PolarCoord strafe, double turn) {
        this.strafe = strafe;
        this.turn = turn;
    }
}