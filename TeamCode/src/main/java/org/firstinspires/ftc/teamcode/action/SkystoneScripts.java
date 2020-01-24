package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.polar.PolarUtil;

import static org.firstinspires.ftc.teamcode.GameConstants.FACING_BLUE_WALL;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_FOUNDATION_BLUE_CENTER;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_FOUNDATION_RED_CENTER;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_REAR_WALL;
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
                .strafeTo(xy(-15, -30))
                .strafeTo(xy(-36, -30))
                .grabTower(0.65)
                .strafeTo(xy(-36, -15.5))
                .grabTower()
                .strafeTo(xy(-36, -28))
                .turnTo(0)
                .strafeNoStop(0,2400,0.9)//long drive across field
                .liftTower(0.3)
                .turnTo(Math.PI / 2)

                //Foundation
                .strafeTo(xy(50, -16)) //drive to foundation
                .grabTower(0.15)

                .strafeTo(xy(50, -30))
                .strafeTo(xy(16, -30))
                .lowerTower()
                .strafeTo(xy(-5,-30))
                ;
    }

    public SkystoneActions blueSideSkystoneInnerNoFoundation() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_SKYSTONES_BRIDGE))
                .run(() -> bot.getMecanumDrive().stop())
                .strafeTo(xy(-14.5, 30))
                .strafeTo(xy(-36, 30))
                .grabTower(0.65)
                .strafeTo(xy(-36, 15.5))
                .grabTower()
                .strafeTo(xy(-36, 28))
                .turnTo(0)
                .strafeNoStop(0,2200,0.9)
                .liftTower(0.3)
                .turnTo(3 * Math.PI / 2)

                //Foundation
                .strafeTo(xy(50, 16)) //drive to foundation
                .grabTower(0.15) //let go of block

                .strafeTo(xy(50, 30))
                .strafeTo(xy(16, 30))
                .lowerTower()
                .strafeTo(xy(-5,30))
                ;
    }


    public SkystoneActions blueSideSkystoneOuterNoFoundation() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_SKYSTONES_WALL))
                .run(() -> bot.getMecanumDrive().stop())
                .grabTower(0.65)
                .strafeTo(xy(-62, 54))
                .strafeTo(xy(-62, 15.5))
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
                .strafe(3 * Math.PI /2, 1000) //Time based
                ;
    }

    public SkystoneActions redSideSkystoneOuterNoFoundation() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_SKYSTONES_WALL))
                .run(() -> bot.getMecanumDrive().stop())
                .grabTower(0.65)
                .strafeTo(xy(-62, -54))
                .strafeTo(xy(-62, -15.5))
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
                .strafe( Math.PI /2, 1250) //Time based
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
                .grabTower(0.65)
                .strafeTo(xy(-62, 54))
                .strafeTo(xy(-62, 15.5))
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
                .strafe(3 * Math.PI /2, 1350) //Time based
                ;
    }//BLUE OUTER

//INNER
    public SkystoneActions blueSideSkystoneInner() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_SKYSTONES_BRIDGE))
                .run(() -> bot.getMecanumDrive().stop())
                .strafeTo(xy(-14.5, 30))
                .strafeTo(xy(-36, 30))
                .grabTower(0.65)
                .strafeTo(xy(-36, 15.5))
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
                .strafeTo(xy(16.5, 55))
                .strafeTo(xy(16.5, 30))
                .lowerTower()
                .strafeTo(xy(-5,30))
                ;
    }//BLUE INNER

    public SkystoneActions redSideSkystoneInner() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_SKYSTONES_BRIDGE))
                .run(() -> bot.getMecanumDrive().stop())
                .strafeTo(xy(-15, -30))
                .strafeTo(xy(-37, -30))
                .grabTower(0.65)
                .strafeTo(xy(-37, -15.5))
                .grabTower()
                .strafeTo(xy(-37, -28))
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

                .strafeTo(xy(15, -54))
                .strafeTo(xy(15, -30))
                .lowerTower()
                .strafeTo(xy(-5,-30))
                ;
    }//RED INNER

    public SkystoneActions redSideTestScratch() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(fieldPosition(xy(-39, -54), FACING_REAR_WALL)))
                .run(() -> bot.getMecanumDrive().stop())
                .strafeToNoStop(xy(24, -52)) // past the bridge
                .liftTower(0.3)
                .strafeTo(xy(48, -52))
                .liftTower(0.0)
                ;
    }


//SKYSTONE DETECTION TEST, this is actually outer starting spot, oops, commented out one above is old one
    public SkystoneActions redSideSkystoneOuter() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_SKYSTONES_WALL))
                .run(() -> bot.getMecanumDrive().stop())
                .grabTower(.9)

                //.strafeTo(xy(-35, -44))//looks at vuforia
                //.pause(1500)
                //.turnTo(FACING_BLUE_WALL)
                //.pause()
                .strafeTo(xy(-22, -28)) //goes to inner most block
                .pause()
                .detectSkystoneAction(fieldPosition(xy(-40, -28), FACING_BLUE_WALL))//finds skystone and stops
                .pause()
                .grabTower(.65)

                //GET A BLOCK
                .strafeTo(() -> redSkystoneLocations.get(bot.getInnerSkystone()).get(0))
                .pause()
                .strafeTo(() -> redSkystoneLocations.get(bot.getInnerSkystone()).get(1))//drives forwards
                .pause()
                .grabTower()//grabs block
                .strafeTo(() -> redSkystoneLocations.get(bot.getInnerSkystone()).get(2))//drives backwards
                .pause()

                //.strafeTo(xy(-35, -44))//looks at vuforia
                //.pause(1000)

                .turnTo(.05)//turns toward build zone
                .strafeToNoStop(xy(35, -46))//long drive across field
                .strafeTo(xy(40, -46))//keeps going
                .liftTower(0.3)

                .turnTo(FACING_BLUE_WALL)//face foundation
                .strafeTo(xy(50, -14)) //drive to foundation

                //Foundation
                .grabTower(0.15)//open arms a tiny bit
                .lowerTower()//lower tower
                .strafeTo(xy(50, -48))//strafe to wall
                .liftTower(0.2)//raise tower
                .strafeTo(xy(15, -48))//strafe away from foundation
                .lowerTower()//lower tower

                //GET ANOTHER BLOCK
                .strafeTo(() -> redSkystoneLocations.get(bot.getOuterSkystone()).get(0))//drives to outer skystone
                .turnTo(FACING_BLUE_WALL)
                .grabTower(.65)
                .strafeTo(() -> redSkystoneLocations.get(bot.getOuterSkystone()).get(1))//drives forwards
                .grabTower()//grabs block
                .strafeTo(() -> redSkystoneLocations.get(bot.getInnerSkystone()).get(2))//drives backwards

                .turnTo(0)//facing build zone
                .strafeTo(xy(36, -25))//drive to new foundation spot
                .liftTower(.3)

                .strafeTo(xy(36, -36))//drive to new foundation spot
                .grabTower(.3)
                .strafeTo(xy(36, -25))//drive to new foundation spot
                .lowerTower()


                .strafeTo(xy(-8,-54))//park near wall
                ;
    }

//red do everything by ourselves START INNER!!
    public SkystoneActions redSideSkystoneOuter2() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_RED_SKYSTONES_BRIDGE))//inner starting location
                .run(() -> bot.getMecanumDrive().stop())
                .grabTower(.9)
                .strafeTo(xy(-20, -27)) //goes to inner most block
                .pause()
                .detectSkystoneAction(fieldPosition(xy(-40, -27), FACING_BLUE_WALL))//finds skystone and stops
                .pause()
                .grabTower(.65)

                //GET A BLOCK
                .strafeTo(() -> redSkystoneLocations.get(bot.getInnerSkystone()).get(0))
                .strafeTo(() -> redSkystoneLocations.get(bot.getInnerSkystone()).get(1))//drives forwards
                .grabTower()//grabs block
                .strafeTo(() -> redSkystoneLocations.get(bot.getInnerSkystone()).get(3))//drives backwards

                .strafeToNoStop(xy(30, -32))//long drive across field
                .strafeTo(xy(45, -32))//keeps going
                .liftTower(0.3)

                .strafeTo(xy(50, -14)) //drive to foundation

                //Foundation
                .grabTower(0.15)//open arms a tiny bit
                .lowerTower()//lower tower
                .strafeTo(xy(50, -54))//strafe to wall
                .liftTower(0.2)//raise tower
                .strafeTo(xy(15, -54))//strafe away from foundation
                .lowerTower()//lower tower

                //GET ANOTHER BLOCK
                .strafeTo(() -> redSkystoneLocations.get(bot.getOuterSkystone()).get(0))//drives to outer skystone
                .grabTower(.65)
                .strafeTo(() -> redSkystoneLocations.get(bot.getOuterSkystone()).get(1))//drives forwards
                .grabTower()//grabs block
                .strafeTo(() -> redSkystoneLocations.get(bot.getInnerSkystone()).get(3))//drives backwards

                .turnTo(0)//facing build zone
                .strafeTo(xy(36, -36))//drive to new foundation spot


                .strafeTo(xy(-8,-54))//park near wall
                ;
    }
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


//SKYSTONE DETECTION
    public SkystoneActions detectRedSkystones()
    {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(fieldPosition(xy(-20,-30), FACING_BLUE_WALL))) //Right in front of sixth red stone
                .run(() -> bot.getMecanumDrive().stop())
                .detectSkystoneAction(fieldPosition(xy(-40, -30), FACING_BLUE_WALL))//Right in front of third red stone
                ;
    }

    public SkystoneActions detectBlueSkystones()
    {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(fieldPosition(xy(-20,30), FACING_RED_WALL))) //Right in front of sixth blue stone
                .run(() -> bot.getMecanumDrive().stop())
                .detectSkystoneAction(fieldPosition(xy(-40, 30), FACING_RED_WALL))//Right in front of third blue stone
                ;
    }



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
