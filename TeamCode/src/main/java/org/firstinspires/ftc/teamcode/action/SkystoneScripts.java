package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;

import static java.lang.Math.PI;
import static org.firstinspires.ftc.teamcode.GameConstants.*;

import static org.firstinspires.ftc.teamcode.controller.FieldPosition.fieldPosition;
import static org.firstinspires.ftc.teamcode.polar.CartesianCoord.xy;

public class SkystoneScripts {

    SkystoneBot bot;

    public SkystoneScripts(SkystoneBot bot) {
        this.bot = bot;
    }

    public SkystoneActions emptyScript() {
        return new SkystoneActions(System::currentTimeMillis, bot);
    }

    public SkystoneActions blueSkystoneInner() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_BLUE_INNER)) //xy(-14.5, -63)
                .run(() -> bot.getMecanumDrive().stop())
                .grabTower(.9)//mostly closes grabber
                .strafeTo(xy(-14.5, 38)) //goes to inner most block
                .strafeTo(xy(-35, 38)) //goes to inner most block
                .pause()
                .detectSkystoneAction(fieldPosition(xy(-56, 38), FACING_RED_WALL))//FINDS SKYSTONE and stops (if the x position isn't big enough, it stops short, and the motors go weird) When this action completes, it calibrates to think its at the x position given [-60], so we need to read from vuforia right after
                .calibrateLeftRight()
                //.pause(1000)//reads from vuforia (INCREDIBLE IMPORTANT STEP, might have to increase pause longer if motion is incredibly eratic after skystone detection)
                .turnTo(FACING_RED_WALL)//faces skystones
                .grabTower(.65)//opens grabber

                //GET A BLOCK
                .strafeTo(() -> blueSkystoneLocations.get(bot.getInnerSkystone()).get(0)) //aligns horizontally (x) with detected block
                .strafeTo(() -> blueSkystoneLocations.get(bot.getInnerSkystone()).get(1)) //drives forwards
                .grabTower()//grabs block
                .strafeTo(() -> blueSkystoneLocations.get(bot.getInnerSkystone()).get(2)) //drives backwards
                .pause()

                //DRIVE ACROSS FIELD
                .turnTo(PI)//turns opposite build zone

                .strafeToNoStop(xy(15, 37.8))//long drive across field
                .strafeUntil(BACKWARD, () -> bot.getRangeBack().lessThan(15.25) || bot.getRangeBack().greaterThan(320))//long drive across field

                .liftTower(0.25)
                .pause()

                .turnTo(FACING_RED_WALL)//face foundation
                .pause(1000) //LOOK AT VUFORIA
                .turnTo(FACING_RED_WALL)//face foundation

                .strafeTo(xy(54, 37.8))//aligns with center of foundation
                //.run(() -> bot.getRangeRight().reset())
                //.strafeUntil(RIGHT, () -> bot.getRangeRight().between(14.25, 20))//aligns with center of foundation


                .calibrateLeftRight()
                .strafeTo(xy(54, 21)) //drive to foundation
                //FOUNDATION
                .grabTower(0.15)//let go of block

                .lowerTower()//lower tower to grab foundation

                //.strafeUntil(BACKWARD, () -> bot.getRangeBack().lessThan(4) || bot.getRangeBack().greaterThan( 300))//strafe to wall
                .strafeTo(xy(54, 62.5))

                .liftTower(0.6)//raise tower
                .strafeTo(xy(18, 62.5))//strafe away from foundation
                .lowerTower()//lower tower
                .turnTo(FACING_RED_WALL)
                .strafeTo(xy(18, 39))//drives toward inside

                //PARK
                .pause()

                .strafeTo(xy(-12, 39))
                //.strafeUntil(RIGHT, () -> bot.getRangeTop().greaterThan(3) && bot.getRangeTop().lessThan(24))
                ;
    }//BLUE INNER

    public SkystoneActions redSkystoneInner() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_RED_INNER)) //xy(-14.5, -63)
                .run(() -> bot.getMecanumDrive().stop())
                .grabTower(.9)//mostly closes grabber
                .strafeTo(xy(-14.5, -37)) //goes to inner most block
                .strafeTo(xy(-30, -37)) //goes to inner most block
                .pause()
                .detectSkystoneAction(fieldPosition(xy(-56, -37.3), FACING_BLUE_WALL))//FINDS SKYSTONE and stops (if the x position isn't big enough, it stops short, and the motors go weird) When this action completes, it calibrates to think its at the x position given [-60], so we need to read from vuforia right after
                .calibrateLeftRight()
                //.pause(1000)//reads from vuforia (INCREDIBLE IMPORTANT STEP, might have to increase pause longer if motion is incredibly eratic after skystone detection)
                .turnTo(FACING_BLUE_WALL)//faces skystones
                .grabTower(.65)//opens grabber

                //GET A BLOCK
                .strafeTo(() -> redSkystoneLocations.get(bot.getInnerSkystone()).get(0)) //aligns horizontally (x) with detected block
                .strafeTo(() -> redSkystoneLocations.get(bot.getInnerSkystone()).get(1)) //drives forwards
                .grabTower()//grabs block
                .strafeTo(() -> redSkystoneLocations.get(bot.getInnerSkystone()).get(2)) //drives backwards
                .pause()

                //DRIVE ACROSS FIELD
                .turnTo(PI)//turns opposite build zone

                .strafeToNoStop(xy(15, -37))//long drive across field
                .strafeUntil(BACKWARD, () -> bot.getRangeBack().lessThan(15.25) || bot.getRangeBack().greaterThan(320))//long drive across field

                .liftTower(0.25)
                .pause()

                .turnTo(FACING_BLUE_WALL)//face foundation
                .pause(1000) //LOOK AT VUFORIA
                .turnTo(FACING_BLUE_WALL)//face foundation

                .strafeTo(xy(57, -37))//aligns with center of foundation
                //.run(() -> bot.getRangeRight().reset())
                //.strafeUntil(RIGHT, () -> bot.getRangeRight().between(14.25, 20))//aligns with center of foundation


                .calibrateLeftRight()
                .strafeTo(xy(57, -21)) //drive to foundation
                //FOUNDATION
                .grabTower(0.15)//let go of block
//                .strafeTo(xy(62, -37))//strafe away from foundation
//                .lowerTower()//lower tower
//                .turnTo(FACING_BLUE_WALL)
//                .pause()
//
//                //ADD THIS CHECKPOINT IF INACCURATE/EXTRA TIME (probably not)
//                //.strafeTo(xy(35, -37))
//                //.pause(1000)//LOOK AT VUFORIA
//
//                .strafeTo(xy(-35, -37))
//                .pause(600)//reads from vuforia
//
//                //DRIVE TO OTHER SKYSTONE
//                .strafeTo(() -> redSkystoneLocations.get(bot.getOuterSkystone()).get(2))//drives to outer skystone [back pos]
//                .grabTower(.65) //open grabber
//                .pause()
//                .strafeTo(() -> redSkystoneLocations.get(bot.getOuterSkystone()).get(0))//drives to outer skystone [front pos]
//                .pause()
//                .turnTo(FACING_BLUE_WALL) //realign
//                .strafeTo(() -> redSkystoneLocations.get(bot.getOuterSkystone()).get(1))//drives forwards [pick up]
//                .pause()
//                .grabTower()//grabs block
//                .strafeTo(() -> redSkystoneLocations.get(bot.getInnerSkystone()).get(2))//drives backwards [back pos]
//                .pause()
//
//                //DRIVE ACROSS FIELD
//                .turnTo(0.05)//turns toward build zone
//                .strafeToNoStop(xy(32.5, -37))//long drive across field
//                .pause()
//                .liftTower(0.3)
//                .turnTo(FACING_BLUE_WALL)//face foundation
//                .pause(600) //LOOK AT VUFORIA
//                .turnTo(FACING_BLUE_WALL)//face foundation
//                .strafeTo(xy(60, -37))//keeps going
//
//                .strafeTo(xy(56, -21)) //drive to foundation
//                //FOUNDATION
//                .grabTower(0.15)//let go of block
                .lowerTower()//lower tower to grab foundation

                //.strafeUntil(BACKWARD, () -> bot.getRangeBack().lessThan(4) || bot.getRangeBack().greaterThan( 300))//strafe to wall
                .strafeTo(xy(57, -62))

                .liftTower(0.6)//raise tower
                .strafeTo(xy(18, -62))//strafe away from foundation
                .lowerTower()//lower tower
                .turnTo(FACING_BLUE_WALL)
                .strafeTo(xy(18, -37))//drives toward inside

                //PARK
                .pause()

                .strafeTo(xy(-10    , -37))
                //.strafeUntil(LEFT, () -> bot.getRangeTop().greaterThan(3) && bot.getRangeTop().lessThan(24))
                ;
    }//RED INNER


    //BLOCK
    public SkystoneActions addBlockToTower() {
        return emptyScript()
                .releaseTower()
                .grabTower()
                .pause(250)
                .doubleLift(1.0)
                .extendBlock()
                .releaseBlock()
                .retractBlock()
                .doubleLift(0.30)
                .releaseTower()
                .doubleLift(0.1);
    }


    //PARK DEPOT
    public SkystoneActions blueDepotParkOuter() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_BLUE_OUTER))
                .strafeTo(xy(-39, 62))
                .strafeTo(xy(0, 62))
                .strafeTo(xy(0, 64))
                ;
    } //BLUE

    public SkystoneActions redDepotParkOuter() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_RED_OUTER))
                .strafeTo(xy(-39, -62))
                .strafeTo(xy(0, -62))
                .strafeTo(xy(0, -64))
                ;
    }

    public SkystoneActions blueDepotParkInner() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_BLUE_INNER)) //(-14.5, 63)
                .strafeTo(xy(-14.5, 62))
                .strafeTo(xy(0, 62))
                .strafeTo(xy(0, 63))
                ;
    } //BLUE

    public SkystoneActions redDepotParkInner() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_RED_INNER)) //(-14.5, -63)
                .strafeTo(xy(-14.5, -62))
                .strafeTo(xy(0, -62))
                .strafeTo(xy(0, -63))
                ;
    }

    //PARK BUILD
    public SkystoneActions redBuildParkOuter() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_BUILD_WALL))
                .strafeTo(xy(39, -62))
                .strafeTo(xy(0, -62))
                .strafeTo(xy(0, -64))
                ;
    }

    public SkystoneActions blueBuildParkOuter() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_BUILD_WALL))
                .strafeTo(xy(39, 62))
                .strafeTo(xy(0, 62))
                .strafeTo(xy(0, 64))
                ;
    } //BLUE

    public SkystoneActions redBuildParkInner() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_BUILD_WALL))
                //PARK
                .pause()
                .strafeTo(xy(39, -35))
                .pause()
                .strafeTo(xy(-4, -35))
                ;
    }

    public SkystoneActions blueBuildParkInner() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_BUILD_WALL))
                //PARK
                .pause()
                .strafeTo(xy(39, 35))
                .pause()
                .strafeTo(xy(-4, 35))
                ;
    } //BLUE


    //confirm mecanum accuracy
    public SkystoneActions moveAroundField()
    {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(fieldPosition(xy(-36,-48), FACING_BLUE_WALL))) //Right in front of sixth blue stone
                .run(() -> bot.getMecanumDrive().stop())
                .strafeTo(xy(-48, 48))
                .strafeTo(xy(48, 48))
                .strafeTo(xy(48, -48))
                .strafeTo(xy(-48, -48))
                ;
    }

}
