package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Math.min;
import static java.lang.Math.max;

@TeleOp(name = "ServoTest", group = "1")
public class ServoTest extends OpMode {

    Servo servo;
    
    @Override
    public void init() {
        servo = hardwareMap.servo.get("servo0");
        servo.setPosition(0.5);
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_down) {
            servo.setPosition(max(0.0, servo.getPosition() - 0.001));
        } else if (gamepad1.dpad_up) {
            servo.setPosition(min(1.0, servo.getPosition() + 0.001));
        }
    }
}
