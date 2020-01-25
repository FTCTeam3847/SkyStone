package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;

import static org.firstinspires.ftc.teamcode.GameConstants.FACING_BLUE_WALL;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_FOUNDATION_BLUE_CENTER;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_FOUNDATION_RED_CENTER;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_RED_WALL;
import static org.firstinspires.ftc.teamcode.GameConstants.START_NEAR_BLUE_BUILD_WALL;
import static org.firstinspires.ftc.teamcode.GameConstants.START_NEAR_BLUE_FOUNDATION;
import static org.firstinspires.ftc.teamcode.GameConstants.START_NEAR_BLUE_SKYSTONES_BRIDGE;
import static org.firstinspires.ftc.teamcode.GameConstants.START_NEAR_BLUE_SKYSTONES_WALL;
import static org.firstinspires.ftc.teamcode.GameConstants.START_NEAR_RED_BUILD_WALL;
import static org.firstinspires.ftc.teamcode.GameConstants.START_NEAR_RED_FOUNDATION;
import static org.firstinspires.ftc.teamcode.GameConstants.START_NEAR_RED_SKYSTONES_BRIDGE;
import static org.firstinspires.ftc.teamcode.GameConstants.START_NEAR_RED_SKYSTONES_WALL;
import static org.firstinspires.ftc.teamcode.GameConstants.UNDER_BLUE_BRIDGE;
import static org.firstinspires.ftc.teamcode.GameConstants.UNDER_RED_BRIDGE;
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

//PARK DEPOT
    public SkystoneActions blueDepotParkOnly() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_SKYSTONES_WALL))
                .strafeTo(xy(-39, 52))
                .strafeTo(xy(0, 52))
                .strafeTo(xy(0, 54))
                ;
    }

    public SkystoneActions redDepotParkOnly() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_SKYSTONES_WALL))
                .strafeTo(xy(-39, -52))
                .strafeTo(xy(0, -52))
                .strafeTo(xy(0, -54))
                ;
    }

//PARK BUILD
    public SkystoneActions redBuildParkOnly() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_BUILD_WALL))
                .strafeTo(xy(39, -52))
                .strafeTo(xy(0, -52))
                .strafeTo(xy(0, -54))
                ;
    }

    public SkystoneActions blueBuildParkOnly() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_BUILD_WALL))
                .strafeTo(xy(39, 52))
                .strafeTo(xy(0, 52))
                .strafeTo(xy(0, 54))
                ;
    }


    //FOUNDATION ONLY
    public SkystoneActions blueFoundation() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_FOUNDATION))
                .run(() -> bot.getMecanumDrive().stop())
                .liftTower(.15)
                .pause(14_000)
                .strafeTo(FACING_FOUNDATION_BLUE_CENTER)
                .lowerTower()
                .strafeTo(START_NEAR_BLUE_FOUNDATION)
                .strafe(Math.PI, 250)
                .liftTower(.15)
                .strafeTo(fieldPosition(xy(15, 54), FACING_RED_WALL))
                .lowerTower()
                .strafeTo(UNDER_BLUE_BRIDGE);
    }

    public SkystoneActions redFoundation() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_FOUNDATION))
                .run(() -> bot.getMecanumDrive().stop())
                .liftTower(.15)
                .pause(14_000)
                .strafeTo(FACING_FOUNDATION_RED_CENTER)
                .lowerTower()
                .strafeTo(START_NEAR_RED_FOUNDATION)
                .strafe(Math.PI, 250)
                .liftTower(.15)
                .strafeTo(fieldPosition(xy(15, -54), FACING_BLUE_WALL))
                .lowerTower()
                .strafeTo(UNDER_RED_BRIDGE);
    }


//NO FOUNDATION
    public SkystoneActions redSideSkystoneInnerNoFoundation() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_SKYSTONES_BRIDGE))
                .run(() -> bot.getMecanumDrive().stop())
                ;
    }

    public SkystoneActions blueSideSkystoneInnerNoFoundation() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_SKYSTONES_BRIDGE))
                .run(() -> bot.getMecanumDrive().stop())
                ;
    }

    public SkystoneActions blueSideSkystoneOuterNoFoundation() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_SKYSTONES_WALL))
                .run(() -> bot.getMecanumDrive().stop())

                ;
    }

    public SkystoneActions redSideSkystoneOuterNoFoundation() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_SKYSTONES_WALL))
                .run(() -> bot.getMecanumDrive().stop())

                ;
    }


//OUTER
//    public SkystoneActions redSideSkystoneOuter() {
//        return emptyScript()
//                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_SKYSTONES_WALL))
//                .run(() -> bot.getMecanumDrive().stop())
//                .grabTower(0.65)
//                .strafeTo(xy(-62, -54))
//                .strafeTo(xy(-62, -15.5))
//                .grabTower()
//                .strafeTo(xy(-61, -50))
//                .turnTo(0)
//                .strafeNoStop(0, 3100, 0.9) //Time based
//                .liftTower(0.3)
//                .turnTo(Math.PI / 2)
//                .strafeTo(xy(50, -18))
//                .grabTower(0.15)
//
//                //MOVES FOUNDATION -----------------------------------------------------------------
//                .lowerTower()
//                .strafeTo(xy(50, -54))
//                .liftTower(0.2)
//                //----------------------------------------------------------------------------------
//                .strafeTo(xy(50, -54)) //Time based
//                .strafeNoStop(Math.PI /2, 1500) //Time based
//                .lowerTower()
//                .strafe( Math.PI /2, 1250) //Time based
//                ;
//    }//RED OUTER

    public SkystoneActions blueSideSkystoneOuter() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_SKYSTONES_WALL))
                .run(() -> bot.getMecanumDrive().stop())
                ;
    }//BLUE OUTER

//INNER
    public SkystoneActions blueSideSkystoneInner() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_SKYSTONES_BRIDGE))
                .run(() -> bot.getMecanumDrive().stop())

                ;
    }//BLUE INNER
//
//    public SkystoneActions redSideSkystoneInner() {
//        return emptyScript()
//                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_SKYSTONES_BRIDGE))
//                .run(() -> bot.getMecanumDrive().stop())
//                .strafeTo(xy(-15, -30))
//                .strafeTo(xy(-37, -30))
//                .grabTower(0.65)
//                .strafeTo(xy(-37, -15.5))
//                .grabTower()
//                .strafeTo(xy(-37, -28))
//                .turnTo(0)
//                .strafeNoStop(0,2400,0.9)//long drive across field
//                .liftTower(0.3)
//                .turnTo(Math.PI / 2)
//                .strafeTo(xy(50, -14)) //drive to foundation
//
//                //Foundation
//                .grabTower(0.15)
//                .lowerTower()
//                .strafeTo(xy(50, -54))
//                .liftTower(0.2)
//
//                .strafeTo(xy(15, -54))
//                .strafeTo(xy(15, -30))
//                .lowerTower()
//                .strafeTo(xy(-5,-30))
//                ;
//    }//RED INNER


//SKYSTONE DETECTION TEST, this is actually outer starting spot, oops, commented out one above is old one
    public SkystoneActions redSideSkystoneOuter() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_SKYSTONES_WALL))
                .run(() -> bot.getMecanumDrive().stop())

                ;
    }

    public SkystoneActions redSideSkystoneInner() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_SKYSTONES_BRIDGE))
                .run(() -> bot.getMecanumDrive().stop())
                .grabTower(.9)//mostly closes grabber
                .strafeTo(xy(-14.5, -37)) //goes to inner most block
                .strafeTo(xy(-30, -37)) //goes to inner most block
                .pause()
                .detectSkystoneAction(fieldPosition(xy(-60, -37), FACING_BLUE_WALL))//FINDS SKYSTONE and stops (if the x position isn't big enough, it stops short, and the motors go weird) When this action completes, it calibrates to think its at the x position given [-60], so we need to read from vuforia right after
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
                .pause()
                .liftTower(0.3)
                .turnTo(FACING_BLUE_WALL)//face foundation
                .pause(1000) //LOOK AT VUFORIA
                .turnTo(FACING_BLUE_WALL)//face foundation
                .strafeTo(xy(60, -37))//keeps going

                .strafeTo(xy(62, -21)) //drive to foundation
                //FOUNDATION
                .grabTower(0.15)//open arms a tiny bit
                .lowerTower(.07)//lower tower to grab foundation
                .strafeTo(xy(62, -62))//strafe to wall
                .liftTower(0.2)//raise tower

                .strafeTo(xy(18, -62))//strafe away from foundation
                .lowerTower()//lower tower
                .turnTo(FACING_BLUE_WALL)
                .pause()

                .strafeTo(xy(18, -37))
                .pause()

                //PARK (TEMP)
                .strafeTo(xy(-8, -37))


                //GET ANOTHER SKYSTONE (time permitting)

                //THESE NUMBERS ARE ACCURATE TO REALITY, BUT CONSIDERING -8 PARKS UNDER THE BRIDGE, THEY MIGHT BE WRONG(MIGHT NOT GET INFRONT OF VUFORIA PROPERLY!!(next step)
                .strafeTo(xy(-35, -37)) //very important! position to read from vuforia before continuing with more precise actions
                .pause(1000)//reads from vuforia

                //DRIVE TO OTHER SKYSTONE
                .strafeTo(() -> redSkystoneLocations.get(bot.getOuterSkystone()).get(2))//drives to outer skystone [back pos]
                .grabTower(.65) //open grabber
                .pause()
                .strafeTo(() -> redSkystoneLocations.get(bot.getOuterSkystone()).get(0))//drives to outer skystone [front pos]
                .pause()
                .turnTo(FACING_BLUE_WALL) //realign
                .strafeTo(() -> redSkystoneLocations.get(bot.getOuterSkystone()).get(1))//drives forwards [pick up]
                .pause()
                .grabTower()//grabs block
                .strafeTo(() -> redSkystoneLocations.get(bot.getInnerSkystone()).get(2))//drives backwards [back pos]
                .pause()

                //PLACE SECOND SKYSTONE
                //assumes foundation's inner x is at +33.5 and its inner y is 42.5
                .turnTo(0)//facing build zone
                .strafeTo(xy(18, -37))//might have to add intermediate drive step
                .liftTower(.3)
                .strafeTo(xy(28.5, -52))//drive to new foundation spot
                .pause()
                .strafeTo(xy(36.5, -52))//drive 3 inches further onto foundation
                .grabTower(.3)//lets go of block
                .strafeTo(xy(28.5, -52))//drives back
                .strafeTo(xy(28.5, -37))//drives toward inside

                //PARK
                .lowerTower()//lower tower
                .pause()
                .strafeTo(xy(18, -37))
                .pause()
                .strafeTo(xy(-8, -37))
                ;
    }

//red do everything by ourselves START INNER!!
    public SkystoneActions redSideSkystoneOuter2() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_SKYSTONES_BRIDGE))//inner starting location
                .run(() -> bot.getMecanumDrive().stop())
                ;
    }

}
