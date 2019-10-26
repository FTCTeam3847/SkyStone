package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "ServoTest", group = "1")
public class ServoTest extends BaseOp {
    @Override
    public void loop() {
        super.loop();
        lift();
    }
}
