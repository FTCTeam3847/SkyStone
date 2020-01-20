package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.BaseOp;

import static org.firstinspires.ftc.teamcode.GameConstants.FACING_BLUE_WALL;
import static org.firstinspires.ftc.teamcode.controller.FieldPosition.fieldPosition;
import static org.firstinspires.ftc.teamcode.polar.CartesianCoord.xy;

@Autonomous
public class RedSideTestingScratch extends BaseOp {

    @Override
    public void start() {
        super.start();
        script = scripts.emptyScript()
                .run(() -> bot.getLocalizer().calibrate(fieldPosition(xy(-39, -54), FACING_BLUE_WALL)))
                .run(() -> bot.stop())
                .strafeTo(xy(-39, -24))
                .start()
        ;
    }
}
