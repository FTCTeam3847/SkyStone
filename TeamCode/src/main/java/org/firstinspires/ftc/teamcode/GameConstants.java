package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.controller.FieldPosition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.PI;
import static java.util.Arrays.asList;
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


    public static final Map<Integer, List<FieldPosition>> redSkystoneLocations = new HashMap<Integer, List<FieldPosition>>(){{
        put(1, asList(fieldPosition(xy(-64, -30), FACING_BLUE_WALL), fieldPosition(xy(-64, -13), FACING_BLUE_WALL), fieldPosition(xy(-64, -34), FACING_BLUE_WALL)));
        put(2, asList(fieldPosition(xy(-56, -30), FACING_BLUE_WALL), fieldPosition(xy(-56, -13), FACING_BLUE_WALL), fieldPosition(xy(-56, -34), FACING_BLUE_WALL)));
        put(3, asList(fieldPosition(xy(-48, -30), FACING_BLUE_WALL), fieldPosition(xy(-48, -13), FACING_BLUE_WALL), fieldPosition(xy(-48, -34), FACING_BLUE_WALL)));
        put(4, asList(fieldPosition(xy(-40, -30), FACING_BLUE_WALL), fieldPosition(xy(-40, -13), FACING_BLUE_WALL), fieldPosition(xy(-40, -34), FACING_BLUE_WALL)));
        put(5, asList(fieldPosition(xy(-32, -30), FACING_BLUE_WALL), fieldPosition(xy(-32, -13), FACING_BLUE_WALL), fieldPosition(xy(-32, -34), FACING_BLUE_WALL)));
        put(6, asList(fieldPosition(xy(-25, -30), FACING_BLUE_WALL), fieldPosition(xy(-24, -13), FACING_BLUE_WALL), fieldPosition(xy(-24, -34), FACING_BLUE_WALL)));
    }};

    public static final Map<Integer, List<FieldPosition>> blueSkystoneLocations = new HashMap<Integer, List<FieldPosition>>(){{
        put(1, asList(fieldPosition(xy(-64, 30), FACING_RED_WALL), fieldPosition(xy(-64, 13), FACING_RED_WALL), fieldPosition(xy(-64, 34), FACING_RED_WALL)));
        put(2, asList(fieldPosition(xy(-56, 30), FACING_RED_WALL), fieldPosition(xy(-56, 13), FACING_RED_WALL), fieldPosition(xy(-56, 34), FACING_RED_WALL)));
        put(3, asList(fieldPosition(xy(-48, 30), FACING_RED_WALL), fieldPosition(xy(-48, 13), FACING_RED_WALL), fieldPosition(xy(-48, 34), FACING_RED_WALL)));
        put(4, asList(fieldPosition(xy(-40, 30), FACING_RED_WALL), fieldPosition(xy(-40, 13), FACING_RED_WALL), fieldPosition(xy(-40, 34), FACING_RED_WALL)));
        put(5, asList(fieldPosition(xy(-32, 30), FACING_RED_WALL), fieldPosition(xy(-32, 13), FACING_RED_WALL), fieldPosition(xy(-32, 34), FACING_RED_WALL)));
        put(6, asList(fieldPosition(xy(-24, 30), FACING_RED_WALL), fieldPosition(xy(-24, 13), FACING_RED_WALL), fieldPosition(xy(-24, 34), FACING_RED_WALL)));
    }};


}
