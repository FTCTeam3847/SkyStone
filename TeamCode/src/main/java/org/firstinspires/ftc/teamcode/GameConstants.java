package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.controller.FieldPosition;

import static java.lang.Math.PI;
import static org.firstinspires.ftc.teamcode.controller.FieldPosition.fieldPosition;
import static org.firstinspires.ftc.teamcode.polar.CartesianCoord.xy;

public class GameConstants {
    public static String VUFORIA_KEY = "AbAFBSH/////AAABmUmB2GBj3E3Lp169iJ8EyJ4HnypcGXIQtM/SzDVCHvBFnAxejLKQdmXGBS2dU+ovBkYVgj4ynnHL5A3KsUcLID46dfcQ5StAswi8YmexAA3RRc1WjNedgQ40kGVJN4pzEDPwcERn+FBPNy7V7+YGPivTGjzGnItUVSCkoujmHg19qoL5QvAy3ZGBYwkJZX8cu6Q3dNDWYpPPE+NbR6RqbGz5P+3WyTCzZTHDctBiEfolVPO8WAJivG73fdV64LelP5sH3FioYdHSG2SmhwRXsZLvhWUxyA2Yxbcgqu6HXf7T6j9VJr3iSDukvi4QpC2lq8RReMjqlsC/pkGpnJeGwgh+8OuLXPf2qESs7bjFocRb\n";

    //Heading
    public static double FACING_REAR_WALL = 0;
    public static double FACING_BLUE_WALL = PI / 2;
    public static double FACING_FRONT_WALL = PI;
    public static double FACING_RED_WALL = 3 * PI / 2;

    //Image
    public static final FieldPosition FACING_IMAGE_REAR_WALL_BLUE = fieldPosition(xy(48, 36), FACING_REAR_WALL);
    public static final FieldPosition FACING_IMAGE_REAR_WALL_RED = fieldPosition(xy(48, -36), FACING_REAR_WALL);

    public static final FieldPosition FACING_IMAGE_RED_WALL_REAR = fieldPosition(xy(36, -48), FACING_RED_WALL);
    public static final FieldPosition FACING_IMAGE_RED_WALL_FRONT = fieldPosition(xy(-36, -48), FACING_RED_WALL);

    public static final FieldPosition FACING_IMAGE_FRONT_WALL_RED = fieldPosition(xy(-48, -36), FACING_FRONT_WALL);
    public static final FieldPosition FACING_IMAGE_FRONT_WALL_BLUE = fieldPosition(xy(-48, 36), FACING_FRONT_WALL);

    public static final FieldPosition FACING_IMAGE_BLUE_WALL_FRONT = fieldPosition(xy(-36, 48), FACING_BLUE_WALL);
    public static final FieldPosition FACING_IMAGE_BLUE_WALL_REAR = fieldPosition(xy(36, 48), FACING_BLUE_WALL);


    public static final FieldPosition STARTING_BLUE_NEAR_DEPOT = fieldPosition(67, 0.85*PI, FACING_RED_WALL);
    public static FieldPosition FACING_BLUE_SKYSTONE_2 = fieldPosition(80, 0.83 * PI, 3 * PI / 3);

    public static final FieldPosition UNDER_BLUE_BRIDGE = fieldPosition(xy(-4, 56), FACING_RED_WALL);
    public static final FieldPosition UNDER_BLUE_BRIDGE_CENTER = fieldPosition(xy(0, 48), FACING_RED_WALL);


    public static final FieldPosition FACING_FOUNDATION_BLUE_CENTER = fieldPosition(xy(50, 18), FACING_RED_WALL);
    public static final FieldPosition START_NEAR_BLUE_FOUNDATION = fieldPosition(xy(50, 54), FACING_RED_WALL);


    public static final FieldPosition START_NEAR_BLUE_SKYSTONES_WALL = fieldPosition(xy(-39, 54), FACING_RED_WALL);//BLUE OUTER
    public static final FieldPosition START_NEAR_BLUE_SKYSTONES_BRIDGE = fieldPosition(xy(-15, 54), FACING_RED_WALL);//BLUE INNER
    public static final FieldPosition NEAR_BLUE_SKYSTONES = fieldPosition(xy(-39, 20), FACING_RED_WALL);
    public static final FieldPosition MIDDLE_BLUE_SKYSTONES = fieldPosition(xy(-39, 30), FACING_RED_WALL);



    public static final FieldPosition START_NEAR_BLUE_BUILD_WALL = fieldPosition(xy(39, 54), FACING_RED_WALL);

    //RED

    public static final FieldPosition START_NEAR_RED_BUILD_WALL = fieldPosition(xy(39, -54), FACING_BLUE_WALL);


    public static final FieldPosition START_NEAR_RED_SKYSTONES_WALL = fieldPosition(xy(-39, -54), FACING_BLUE_WALL);//RED OUTER
    public static final FieldPosition START_NEAR_RED_SKYSTONES_BRIDGE = fieldPosition(xy(-15, -54), FACING_BLUE_WALL);//RED INNER
    public static final FieldPosition START_NEAR_RED_FOUNDATION = fieldPosition(xy(50, -54), FACING_BLUE_WALL);

    public static final FieldPosition FACING_FOUNDATION_RED_CENTER = fieldPosition(xy(50, -18), FACING_BLUE_WALL);

    public static final FieldPosition UNDER_RED_BRIDGE = fieldPosition(xy(-4, -56), FACING_BLUE_WALL);
    public static final FieldPosition UNDER_RED_BRIDGE_CENTER = fieldPosition(xy(0, -48), FACING_BLUE_WALL);



    //SKYSTONES
    public static final FieldPosition INNER_RED_SKYSTONE = fieldPosition(xy(-15, -30), FACING_BLUE_WALL); //6th block
    public static final FieldPosition INNER_BLUE_SKYSTONE = fieldPosition(xy(-15, 30), FACING_RED_WALL); //6th block

    public static final FieldPosition MIDDLE_RED_SKYSTONE = fieldPosition(xy(-40, -18), FACING_BLUE_WALL); //3rd block
    public static final FieldPosition MIDDLE_BLUE_SKYSTONE = fieldPosition(xy(-40, 18), FACING_RED_WALL); //3rd block

}
