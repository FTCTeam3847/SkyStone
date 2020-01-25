package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.controller.FieldPosition;

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


//FOUNDATION SIDE START
    public static final FieldPosition START_NEAR_BLUE_BUILD_WALL = fieldPosition(xy(39, 63), FACING_RED_WALL);
    public static final FieldPosition START_NEAR_RED_BUILD_WALL = fieldPosition(xy(39, -63), FACING_BLUE_WALL);




    //STARTING LOCATIONS
    public static final FieldPosition START_RED_OUTER = fieldPosition(xy(-39, -63), FACING_BLUE_WALL);//RED OUTER
    public static final FieldPosition START_RED_INNER = fieldPosition(xy(-14.5, -63), FACING_BLUE_WALL);//RED INNER

    public static final FieldPosition START_BLUE_OUTER = fieldPosition(xy(-39, 63), FACING_RED_WALL);//BLUE OUTER
    public static final FieldPosition START_BLUE_INNER = fieldPosition(xy(-14.5, 63), FACING_RED_WALL);//BLUE INNER

    //SKYSTONE LOCATIONS, first position is right infront of block, second is to pick it up, third is to return to inner alley
    public static final Map<Integer, List<FieldPosition>> redSkystoneLocations = new HashMap<Integer, List<FieldPosition>>(){{
        put(1, asList(fieldPosition(xy(-64, -37), FACING_BLUE_WALL), fieldPosition(xy(-64, -13), FACING_BLUE_WALL), fieldPosition(xy(-64, -40), FACING_BLUE_WALL)));
        put(2, asList(fieldPosition(xy(-56, -37), FACING_BLUE_WALL), fieldPosition(xy(-56, -13), FACING_BLUE_WALL), fieldPosition(xy(-56, -40), FACING_BLUE_WALL)));
        put(3, asList(fieldPosition(xy(-48, -37), FACING_BLUE_WALL), fieldPosition(xy(-48, -13), FACING_BLUE_WALL), fieldPosition(xy(-48, -40), FACING_BLUE_WALL)));
        put(4, asList(fieldPosition(xy(-40, -37), FACING_BLUE_WALL), fieldPosition(xy(-40, -13), FACING_BLUE_WALL), fieldPosition(xy(-40, -40), FACING_BLUE_WALL)));
        put(5, asList(fieldPosition(xy(-32, -37), FACING_BLUE_WALL), fieldPosition(xy(-32, -13), FACING_BLUE_WALL), fieldPosition(xy(-32, -40), FACING_BLUE_WALL)));
        put(6, asList(fieldPosition(xy(-25, -37), FACING_BLUE_WALL), fieldPosition(xy(-24, -13), FACING_BLUE_WALL), fieldPosition(xy(-24, -40), FACING_BLUE_WALL)));
    }};//block 6 has -25 for the first one but -24 for the rest, unwilling to change if its currently working

    public static final Map<Integer, List<FieldPosition>> blueSkystoneLocations = new HashMap<Integer, List<FieldPosition>>(){{
        put(1, asList(fieldPosition(xy(-64, 37), FACING_RED_WALL), fieldPosition(xy(-64, 13), FACING_RED_WALL), fieldPosition(xy(-64, 40), FACING_RED_WALL)));
        put(2, asList(fieldPosition(xy(-56, 37), FACING_RED_WALL), fieldPosition(xy(-56, 13), FACING_RED_WALL), fieldPosition(xy(-56, 40), FACING_RED_WALL)));
        put(3, asList(fieldPosition(xy(-48, 37), FACING_RED_WALL), fieldPosition(xy(-48, 13), FACING_RED_WALL), fieldPosition(xy(-48, 40), FACING_RED_WALL)));
        put(4, asList(fieldPosition(xy(-40, 37), FACING_RED_WALL), fieldPosition(xy(-40, 13), FACING_RED_WALL), fieldPosition(xy(-40, 40), FACING_RED_WALL)));
        put(5, asList(fieldPosition(xy(-32, 37), FACING_RED_WALL), fieldPosition(xy(-32, 13), FACING_RED_WALL), fieldPosition(xy(-32, 40), FACING_RED_WALL)));
        put(6, asList(fieldPosition(xy(-25, 37), FACING_RED_WALL), fieldPosition(xy(-24, 13), FACING_RED_WALL), fieldPosition(xy(-24, 40), FACING_RED_WALL)));
    }};
}
