package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.controller.FieldPosition;

import static java.lang.Math.PI;
import static org.firstinspires.ftc.teamcode.controller.FieldPosition.fieldPosition;
import static org.firstinspires.ftc.teamcode.polar.CartesianCoord.xy;

public class GameConstants {
    public static String VUFORIA_KEY = "AbAFBSH/////AAABmUmB2GBj3E3Lp169iJ8EyJ4HnypcGXIQtM/SzDVCHvBFnAxejLKQdmXGBS2dU+ovBkYVgj4ynnHL5A3KsUcLID46dfcQ5StAswi8YmexAA3RRc1WjNedgQ40kGVJN4pzEDPwcERn+FBPNy7V7+YGPivTGjzGnItUVSCkoujmHg19qoL5QvAy3ZGBYwkJZX8cu6Q3dNDWYpPPE+NbR6RqbGz5P+3WyTCzZTHDctBiEfolVPO8WAJivG73fdV64LelP5sH3FioYdHSG2SmhwRXsZLvhWUxyA2Yxbcgqu6HXf7T6j9VJr3iSDukvi4QpC2lq8RReMjqlsC/pkGpnJeGwgh+8OuLXPf2qESs7bjFocRb\n";

    public static double FACING_REAR_WALL = 0;
    public static double FACING_BLUE_WALL = PI / 2;
    public static double FACING_FRONT_WALL = PI;
    public static double FACING_RED_WALL = 3 * PI / 2;

    public static final FieldPosition FACING_IMAGE_REAR_WALL_BLUE = fieldPosition(60, 0.21 * PI, FACING_REAR_WALL);
    public static final FieldPosition FACING_IMAGE_BLUE_WALL_REAR = fieldPosition(60, 0.30 * PI, FACING_BLUE_WALL);
    public static final FieldPosition FACING_IMAGE_BLUE_WALL_FRONT = fieldPosition(60, 0.70 * PI, FACING_BLUE_WALL);
    public static final FieldPosition FACING_IMAGE_FRONT_WALL_BLUE = fieldPosition(61, 0.80 * PI, FACING_FRONT_WALL);
    public static final FieldPosition FACING_IMAGE_FRONT_WALL_RED = fieldPosition(xy(-54, -36), FACING_FRONT_WALL);
    public static final FieldPosition FACING_IMAGE_RED_WALL_FRONT = fieldPosition(xy(-36, -48), FACING_RED_WALL);

    public static final FieldPosition STARTING_BLUE_NEAR_DEPOT = fieldPosition(67, 0.85*PI, FACING_RED_WALL);



    public static FieldPosition FACING_BLUE_SKYSTONE_2 = fieldPosition(80, 0.83 * PI, 3 * PI / 3);
}
