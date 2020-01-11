package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;

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


//BLUE
    public SkystoneActions startBlueDepotParkOnly() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(fieldPosition(xy(-39, 60), FACING_RED_WALL)))
                .moveForward(250, 0.3d)
                .strafeTo(xy(4, 60))
                .strafeTo(xy(4, 62))
                ;
    }


    public SkystoneActions blueFoundation() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_FOUNDATION))
                .run(() -> bot.stop())
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
                .run(() -> bot.stop())
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



    public SkystoneActions redSideSkystoneInnerNoFoundation() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_SKYSTONES_BRIDGE))
                .run(() -> bot.stop())
                .strafeTo(xy(-15, -30))
                .strafeTo(xy(-36, -30))
                .grabTower(0.65)
                .strafeTo(xy(-36, -17))
                .grabTower()
                .strafeTo(xy(-36, -28))
                .turnTo(0)
                .strafeNoStop(0,2300,0.9)//long drive across field
                .liftTower(0.3)
                .turnTo(Math.PI / 2)

                //Foundation
                .strafeTo(xy(50, -16)) //drive to foundation
                .grabTower(0.15)

                .strafeTo(xy(50, -24))
                .strafeTo(xy(16, -24))
                .lowerTower()
                .strafeTo(xy(-5,-24))
                ;
    }

    public SkystoneActions blueSideSkystoneInnerNoFoundation() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_SKYSTONES_BRIDGE))
                .run(() -> bot.stop())
                .strafeTo(xy(-14.5, 30))
                .strafeTo(xy(-36, 30))
                .grabTower(0.65)
                .strafeTo(xy(-36, 17))
                .grabTower()
                .strafeTo(xy(-36, 28))
                .turnTo(0)
                .strafeNoStop(0,2300,0.9)
                .liftTower(0.3)
                .turnTo(3 * Math.PI / 2)

                //Foundation
                .strafeTo(xy(50, 16)) //drive to foundation
                .grabTower(0.15) //let go of block

                .strafeTo(xy(50, 24))
                .strafeTo(xy(16, 24))
                .lowerTower()
                .strafeTo(xy(-5,24))
                ;
    }


    public SkystoneActions blueSideSkystoneOuterNoFoundation() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_SKYSTONES_WALL))
                .run(() -> bot.stop())
                .grabTower(0.65)
                .strafeTo(xy(-62, 54))
                .strafeTo(xy(-62, 18))
                .grabTower()
                .strafeTo(xy(-61, 50))
                .turnTo(0)
                .strafeNoStop(0, 3100, 0.9) //Time based
                .liftTower(0.3)
                .turnTo(3 * Math.PI / 2)
                .strafeTo(xy(50, 16))
                .grabTower(0.15)

                .strafeTo(xy(50, -54)) //Time based
                .strafeNoStop(3 * Math.PI /2, 1500) //Time based
                .lowerTower()
                .strafeNoStop(3 * Math.PI /2, 1000) //Time based
                ;
    }

    public SkystoneActions redSideSkystoneOuterNoFoundation() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_SKYSTONES_WALL))
                .run(() -> bot.stop())
                .grabTower(0.65)
                .strafeTo(xy(-62, -54))
                .strafeTo(xy(-62, -18))
                .grabTower()
                .strafeTo(xy(-61, -50))
                .turnTo(0)
                .strafeNoStop(0, 3100, 0.9) //Time based
                .liftTower(0.3)
                .turnTo(Math.PI / 2)
                .strafeTo(xy(50, -16))
                .grabTower(0.15)


                .strafeNoStop(Math.PI /2, 1500) //Time based
                .lowerTower()
                .strafeNoStop( Math.PI /2, 1250) //Time based
                ;
    }

    public SkystoneActions redSideSkystoneOuter() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_SKYSTONES_WALL))
                .run(() -> bot.stop())
                .grabTower(0.65)
                .strafeTo(xy(-62, -54))
                .strafeTo(xy(-62, -16))
                .grabTower()
                .strafeTo(xy(-61, -50))
                .turnTo(0)
                .strafeNoStop(0, 3100, 0.9) //Time based
                .liftTower(0.3)
                .turnTo(Math.PI / 2)
                .strafeTo(xy(50, -18))
                .grabTower(0.15)

                //MOVES FOUNDATION -----------------------------------------------------------------
                .lowerTower()
                .strafeTo(xy(50, -54))
                .liftTower(0.2)
                //----------------------------------------------------------------------------------
                .strafeTo(xy(50, -54)) //Time based
                .strafeNoStop(Math.PI /2, 1500) //Time based
                .lowerTower()
                .strafe( Math.PI /2, 1250) //Time based
                ;
    }//RED OUTER

    public SkystoneActions blueSideSkystoneOuter() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_SKYSTONES_WALL))
                .run(() -> bot.stop())
                .grabTower(0.65)
                .strafeTo(xy(-62, 54))
                .strafeTo(xy(-62, 18))
                .grabTower()
                .strafeTo(xy(-61, 50))
                .turnTo(0)
                .strafeNoStop(0, 3100, 0.9) //Time based
                .liftTower(0.3)
                .turnTo(3 * Math.PI / 2)
                .strafeTo(xy(50, 16))
                .grabTower(0.15)
                //MOVES FOUNDATION -----------------------------------------------------------------
                .lowerTower()
                .strafeTo(xy(50, 56))
                .liftTower(0.2)
                //----------------------------------------------------------------------------------
                .strafeNoStop(3 * Math.PI /2, 1500) //Time based
                .lowerTower()
                .strafeNoStop(3 * Math.PI /2, 1000) //Time based
                ;
    }//BLUE OUTER


    public SkystoneActions blueSideSkystoneInner() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_SKYSTONES_BRIDGE))
                .run(() -> bot.stop())
                .strafeTo(xy(-14.5, 30))
                .strafeTo(xy(-36, 30))
                .grabTower(0.65)
                .strafeTo(xy(-36, 17))
                .grabTower()
                .strafeTo(xy(-36, 28))
                .turnTo(0)
                .strafeNoStop(0,2200,0.9)
                .liftTower(0.3)
                .turnTo(3 * Math.PI / 2)
                //Foundation
                .strafeTo(xy(50, 16)) //drive to foundation
                .grabTower(0.15) //let go of block
                .lowerTower()
                .strafeTo(xy(50, 55))
                .liftTower(0.2)
                .strafeTo(xy(18, 55))
                .strafeTo(xy(18, 30))
                .lowerTower()
                .strafeTo(xy(-5,30))
                ;
    }//BLUE INNER

    public SkystoneActions redSideSkystoneInner() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_SKYSTONES_BRIDGE))
                .run(() -> bot.stop())
                .strafeTo(xy(-15, -30))
                .strafeTo(xy(-36, -30))
                .grabTower(0.65)
                .strafeTo(xy(-36, -17.5))
                .grabTower()
                .strafeTo(xy(-36, -28))
                .turnTo(0)
                .strafeNoStop(0,2400,0.9)//long drive across field
                .liftTower(0.3)
                .turnTo(Math.PI / 2)
                .strafeTo(xy(50, -14)) //drive to foundation

                //Foundation
                .grabTower(0.15)
                .lowerTower()
                .strafeTo(xy(50, -54))
                .liftTower(0.2)

                .strafeTo(xy(16, -54))
                .strafeTo(xy(16, -30))
                .lowerTower()
                .strafeTo(xy(-5,-30))
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



//never tested, bad
    public SkystoneActions redSideSkystoneOuter2() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(fieldPosition(xy(-39, -54), FACING_BLUE_WALL)))
                .run(() -> bot.stop())
                .grabTower(0.65)
                .strafeTo(xy(-62, -54))
                .strafeTo(xy(-62, -18))
                .grabTower()
                .strafeTo(xy(-61, -50))
                .turnTo(0)
                .strafeNoStop(0, 3100, 0.9) //Time based
                .liftTower(0.3)
                .turnTo(Math.PI / 2)
                .strafeTo(xy(50, -16))
                .grabTower(0.15)
                //MOVES FOUNDATION -----------------------------------------------------------------
                .lowerTower()
                .strafeTo(xy(50, -54))
                .liftTower(0.2)
                //----------------------------------------------------------------------------------
                .strafeNoStop(Math.PI /2, 1500) //Time based
                .lowerTower()
                .strafeNoStop( Math.PI /2, 750) //Time based
                ;
    }
//doesn't work
    public SkystoneActions blueSideSkystoneParallelAttempt() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_SKYSTONES_WALL))
                .run(() -> bot.stop())
                .grabTower(.65)
                //.strafeTo(NEAR_BLUE_SKYSTONES)
                .strafe(0, 2400)
                .grabTower()
                .strafeTo(MIDDLE_BLUE_SKYSTONES)
                .strafeTo(UNDER_BLUE_BRIDGE_CENTER)

                .strafe(Math.PI / 2, 750)
                .towerDrive(1000, Math.PI / 2, .6, .25)

                .strafeTo(FACING_FOUNDATION_BLUE_CENTER)
                .grabTower(.15)
                .lowerTower()
                .strafeTo(START_NEAR_BLUE_FOUNDATION)

                .towerDrive(500, Math.PI, .6, .2)


                .strafeTo(fieldPosition(xy(15, 54), FACING_RED_WALL))
                .lowerTower()
                .strafeTo(UNDER_BLUE_BRIDGE)
                ;
    }

}
