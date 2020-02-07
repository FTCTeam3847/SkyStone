package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.BaseOp;

@Autonomous
public class BlueDepotParkOuter extends BaseOp {
    @Override
    public void start() {
        super.start();
        script = scripts.blueDepotParkOuter().start();
    }
}
