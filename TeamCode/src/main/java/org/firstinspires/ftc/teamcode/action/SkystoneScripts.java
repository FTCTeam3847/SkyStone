package org.firstinspires.ftc.teamcode.action;

import org.firstinspires.ftc.teamcode.bot.SkystoneBot;

import static org.firstinspires.ftc.teamcode.GameConstants.FACING_FOUNDATION_BLUE_CENTER;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_RED_WALL;
import static org.firstinspires.ftc.teamcode.GameConstants.MIDDLE_BLUE_SKYSTONES;
import static org.firstinspires.ftc.teamcode.GameConstants.START_NEAR_BLUE_FOUNDATION;
import static org.firstinspires.ftc.teamcode.GameConstants.START_NEAR_BLUE_SKYSTONES;
import static org.firstinspires.ftc.teamcode.GameConstants.UNDER_BLUE_BRIDGE;
import static org.firstinspires.ftc.teamcode.GameConstants.UNDER_BLUE_BRIDGE_CENTER;
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
                .liftTower(.15)
                .pause(14_000)
                .strafeTo(FACING_FOUNDATION_BLUE_CENTER)
                .lowerTower()
                .strafeTo(START_NEAR_BLUE_FOUNDATION)
                .strafe(Math.PI, 250, .6)
                .liftTower(.15)
                .strafeTo(fieldPosition(xy(15, 54), FACING_RED_WALL))
                .lowerTower()
                .strafeTo(UNDER_BLUE_BRIDGE);
    }


    public SkystoneActions blueSideSkystoneParallelAttempt() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_SKYSTONES))
                .grabTower(.65)
                //.strafeTo(NEAR_BLUE_SKYSTONES)
                .strafe(0, 2400, .6)
                .grabTower()
                .strafeTo(MIDDLE_BLUE_SKYSTONES)
                .strafeTo(UNDER_BLUE_BRIDGE_CENTER)

                .strafe(Math.PI / 2, 750, .6)
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

    public SkystoneActions blueSideSkystone() {
        return emptyScript()
                .run(() -> bot.getLocalizer().calibrate(START_NEAR_BLUE_SKYSTONES))
                .grabTower(.65)
                //.strafeTo(NEAR_BLUE_SKYSTONES)
                .strafe(0, 2400, .6)
                .grabTower()
                .strafeTo(MIDDLE_BLUE_SKYSTONES)
                .strafeTo(UNDER_BLUE_BRIDGE_CENTER)

                .strafe(Math.PI / 2, 2500, .6)
                .liftTower(.25)

                .strafeTo(FACING_FOUNDATION_BLUE_CENTER)
                .grabTower(.15)
                .lowerTower()
                .strafeTo(START_NEAR_BLUE_FOUNDATION)

                .strafe(Math.PI, 500, .6)
                .liftTower(.2)

                .strafeTo(fieldPosition(xy(15, 54), FACING_RED_WALL))
                .lowerTower()
                .strafeTo(UNDER_BLUE_BRIDGE)
                ;
    }

    public SkystoneActions addBlockToTower() {
        return emptyScript()
                .releaseTower()
                .grabTower()
                .doubleLift(1.0)
                .extendBlock()
                .releaseBlock()
                .retractBlock()
                .doubleLift(0.35)
                .releaseTower()
                .doubleLift(0.1);
    }

}
