package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class MotorTestOpMode extends OpMode {

    private DcMotor motor1;


    @Override
    public void init() {
        motor1 = hardwareMap.get(DcMotor.class, "motor1");
        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor1.setTargetPosition(0);
    }

    @Override
    public void loop() {

        if (gamepad1.a) {
            motor1.setTargetPosition(motor1.getCurrentPosition() + 100);
            motor1.setPower(.3);

        }

        telemetry.addData("a", gamepad1.a);
        telemetry.addData("target", motor1.getTargetPosition());
        telemetry.addData("position", motor1.getCurrentPosition());
        telemetry.update();

    }
}
