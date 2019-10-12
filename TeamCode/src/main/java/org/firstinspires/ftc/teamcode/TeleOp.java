package org.firstinspires.ftc.teamcode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "DerpBot Test", group = "1")
public class TeleOp extends BaseOp {
    @Override
    public void init() {
        super.init();
    }

    @Override
    public void loop() {
//        geometricDrive(gamepad1.right_stick_x, -gamepad1.right_stick_y);
        mecanumDrive(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

        if (gamepad1.dpad_up) {
            moveStraight(1.0);
        } else if (gamepad1.dpad_down) {
            moveStraight(-1.0);
        } else if (gamepad1.dpad_right) {
            turn(-1.0);
        } else if (gamepad1.dpad_left) {
            turn(1.0);
        } else {
            moveStop();
        }
    }
}