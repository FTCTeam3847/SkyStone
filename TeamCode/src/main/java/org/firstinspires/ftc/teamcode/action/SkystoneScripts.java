package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;

import static org.firstinspires.ftc.teamcode.GameConstants.FACING_BLUE_WALL;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_RED_WALL;
import static org.firstinspires.ftc.teamcode.GameConstants.START_BLUE_INNER;
import static org.firstinspires.ftc.teamcode.GameConstants.START_BLUE_OUTER;
import static org.firstinspires.ftc.teamcode.GameConstants.START_NEAR_BLUE_BUILD_WALL;
import static org.firstinspires.ftc.teamcode.GameConstants.START_NEAR_RED_BUILD_WALL;
import static org.firstinspires.ftc.teamcode.GameConstants.START_RED_INNER;
import static org.firstinspires.ftc.teamcode.GameConstants.START_RED_OUTER;
import static org.firstinspires.ftc.teamcode.GameConstants.blueSkystoneLocations;
import static org.firstinspires.ftc.teamcode.GameConstants.redSkystoneLocations;
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

//NORMAL SCRIPTS
    public SkystoneActions blueSkystoneOuter() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_BLUE_OUTER)) //(xy(-39, 63)
                .run(() -> bot.getMecanumDrive().stop())
                ;
    }//BLUE OUTER

    public SkystoneActions blueSkystoneInner() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_BLUE_INNER)) //xy(-14.5, 63)
                .run(() -> bot.getMecanumDrive().stop())
                .grabTower(.9)//mostly closes grabber
                .strafeTo(xy(-14.5, 40)) //goes to inner most block
                .strafeTo(xy(-35, 40)) //goes to inner most block, 5 more inches than on red side
                .pause()
                .detectSkystoneAction(fieldPosition(xy(-60, 40), FACING_RED_WALL))//FINDS SKYSTONE and stops (if the x position isn't big enough, it stops short, and the motors go weird) When this action completes, it calibrates to think its at the x position given [-60], so we need to read from vuforia right after
                .pause(2000)//reads from vuforia (INCREDIBLE IMPORTANT STEP, might have to increase pause longer if motion is incredibly eratic after skystone detection)
                .turnTo(FACING_RED_WALL)//faces skystones
                .grabTower(.65)//opens grabber

                //GET A BLOCK
                .strafeTo(() -> blueSkystoneLocations.get(bot.getInnerSkystone()).get(0)) //aligns horizontally (x) with detected block
                .pause()
                .strafeTo(() -> blueSkystoneLocations.get(bot.getInnerSkystone()).get(1)) //drives forwards
                .pause()
                .grabTower()//grabs block
                .strafeTo(() -> blueSkystoneLocations.get(bot.getInnerSkystone()).get(2)) //drives backwards
                .pause()

                //DRIVE ACROSS FIELD
                .turnTo(0.0)//turns toward build zone
                .strafeToNoStop(xy(48, 37))//long drive across field
                .pause()
                .liftTower(0.25)
                .turnTo(FACING_RED_WALL)//face foundation
                .pause(1000) //LOOK AT VUFORIA
                .turnTo(FACING_RED_WALL)//face foundation
                //.strafeTo(xy(60,  37))//keeps going

                .strafeTo(xy(46, 21)) //drive to foundation
                //FOUNDATION
                .grabTower(0.15)//let go of block
//                .strafeTo(xy(62, 37))//strafe away from foundation
//                .lowerTower()//lower tower
//                .turnTo(FACING_RED_WALL)
//                .pause()
//
//                //ADD THIS CHECKPOINT IF INACCURATE/EXTRA TIME (probably not)
//                //.strafeTo(xy(35, -37))
//                //.pause(1000)//LOOK AT VUFORIA
//
//                .strafeTo(xy(-35, 37))
//                .pause(1000)//reads from vuforia
//
//                //DRIVE TO OTHER SKYSTONE
//                .strafeTo(() -> blueSkystoneLocations.get(bot.getOuterSkystone()).get(2))//drives to outer skystone [back pos]
//                .grabTower(.65) //open grabber
//                .pause()
//                .strafeTo(() -> blueSkystoneLocations.get(bot.getOuterSkystone()).get(0))//drives to outer skystone [front pos]
//                .pause()
//                .turnTo(FACING_RED_WALL) //realign
//                .strafeTo(() -> blueSkystoneLocations.get(bot.getOuterSkystone()).get(1))//drives forwards [pick up]
//                .pause()
//                .grabTower()//grabs block
//                .strafeTo(() -> blueSkystoneLocations.get(bot.getInnerSkystone()).get(2))//drives backwards [back pos]
//                .pause()
//
//                //DRIVE ACROSS FIELD
//                .turnTo(0.05)//turns toward build zone
//                .strafeToNoStop(xy(32.5, 37))//long drive across field
//                .pause()
//                .liftTower(0.3)
//                .turnTo(FACING_RED_WALL)//face foundation
//                .pause(1000) //LOOK AT VUFORIA
//                .turnTo(FACING_RED_WALL)//face foundation
//                .strafeTo(xy(60, 37))//keeps going
//
//                .strafeTo(xy(62, 21)) //drive to foundation
//                //FOUNDATION
//                .grabTower(0.15)//let go of block
                .lowerTower(.07)//lower tower to grab foundation
                .strafeTo(xy(62, 62))//strafe to wall
                .liftTower(0.2)//raise tower
                .strafeTo(xy(18, 62))//strafe away from foundation
                .lowerTower()//lower tower
                .turnTo(FACING_RED_WALL)
                .pause()
                .strafeTo(xy(22, 52))//drives back
                .strafeTo(xy(22, 37))//drives toward inside

                //PARK
                .lowerTower()//lower tower
                .pause()
                .strafeTo(xy(18, 37))//drives forwards
                .pause()
                .strafeTo(xy(-8, 37))//parks near center
                ;
    }//BLUE INNER

    //pathing problems, our teammate always gets in the way i think... probably dont need outer scripts anyways, we have yet to encounter a match where both robots are moving stones during autonomous
    public SkystoneActions redSkystoneOuter() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_RED_OUTER)) //(xy(-39, -63)
                .run(() -> bot.getMecanumDrive().stop())
                ;
    }//RED OUTER

    public SkystoneActions redSkystoneInner() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_RED_INNER)) //xy(-14.5, -63)
                .run(() -> bot.getMecanumDrive().stop())
                .grabTower(.9)//mostly closes grabber
                .strafeTo(xy(-14.5, -38)) //goes to inner most block
                .strafeTo(xy(-30, -38)) //goes to inner most block
                .pause()
                .detectSkystoneAction(fieldPosition(xy(-60, -38), FACING_BLUE_WALL))//FINDS SKYSTONE and stops (if the x position isn't big enough, it stops short, and the motors go weird) When this action completes, it calibrates to think its at the x position given [-60], so we need to read from vuforia right after
                .pause(1000)//reads from vuforia (INCREDIBLE IMPORTANT STEP, might have to increase pause longer if motion is incredibly eratic after skystone detection)
                .turnTo(FACING_BLUE_WALL)//faces skystones
                .grabTower(.65)//opens grabber

                //GET A BLOCK
                .strafeTo(() -> redSkystoneLocations.get(bot.getInnerSkystone()).get(0)) //aligns horizontally (x) with detected block
                .pause()
                .strafeTo(() -> redSkystoneLocations.get(bot.getInnerSkystone()).get(1)) //drives forwards
                .pause()
                .grabTower()//grabs block
                .strafeTo(() -> redSkystoneLocations.get(bot.getInnerSkystone()).get(2)) //drives backwards
                .pause()

                //DRIVE ACROSS FIELD
                .turnTo(0.05)//turns toward build zone
                .strafeToNoStop(xy(32.5, -37))//long drive across field
                .liftTower(0.25)
                .pause()

                .turnTo(FACING_BLUE_WALL)//face foundation
                .pause(1000) //LOOK AT VUFORIA
                .turnTo(FACING_BLUE_WALL)//face foundation
                //.strafeTo(xy(60, -37))//keeps going

                .strafeTo(xy(52, -21)) //drive to foundation
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
                .lowerTower(.07)//lower tower to grab foundation
                .strafeTo(xy(62, -62))//strafe to wall
                .liftTower(0.2)//raise tower
                .strafeTo(xy(18, -62))//strafe away from foundation
                .lowerTower()//lower tower
                .turnTo(FACING_BLUE_WALL)
                .pause()
                .strafeTo(xy(22, -52))//drives back
                .strafeTo(xy(22, -37))//drives toward inside

                //PARK
                .lowerTower(0.01)//lower tower
                .pause()
                .strafeTo(xy(18, -37))
                .pause()
                .strafeTo(xy(-8, -37))
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
    public SkystoneActions blueDepotParkOnly() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_BLUE_OUTER))
                .strafeTo(xy(-39, 62))
                .strafeTo(xy(0, 62))
                .strafeTo(xy(0, 64))
                ;
    }

    public SkystoneActions redDepotParkOnly() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_RED_OUTER))
                .strafeTo(xy(-39, -62))
                .strafeTo(xy(0, -62))
                .strafeTo(xy(0, -64))
                ;
    }

    //PARK BUILD
    public SkystoneActions redBuildParkOnly() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_BUILD_WALL))
                .strafeTo(xy(39, -62))
                .strafeTo(xy(0, -62))
                .strafeTo(xy(0, -64))
                ;
    }

    public SkystoneActions blueBuildParkOnly() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_BUILD_WALL))
                .strafeTo(xy(39, 62))
                .strafeTo(xy(0, 62))
                .strafeTo(xy(0, 64))
                ;
    }


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
