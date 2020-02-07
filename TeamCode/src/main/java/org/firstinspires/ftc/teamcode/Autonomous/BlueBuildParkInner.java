package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.BaseOp;

@Autonomous
public class BlueBuildParkInner extends BaseOp {
    @Override
    public void start() {
        super.start();
        script = scripts.blueBuildParkInner().start();
    }
}
