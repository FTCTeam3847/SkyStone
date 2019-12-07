package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Trinkets.BlockExtender;
import org.firstinspires.ftc.teamcode.Trinkets.TowerLifter;
import org.firstinspires.ftc.teamcode.gamepad.ToggleButton;


@TeleOp(name = "TrinketOpMode", group = "1")
public class TrinketOpMode extends BaseOp {
    ToggleButton toggleButtonA = new ToggleButton(() -> gamepad1.a);
    ToggleButton toggleButtonB = new ToggleButton(() -> gamepad1.b);


    TowerLifter towerLifter;
    BlockExtender blockExtender;



    @Override
    public void init() {
        super.init();

        towerLifter = new TowerLifter(leftGrabberLifter::setPower, rightGrabberLifter::setPower, leftGrabberLifter::setTargetPosition, rightGrabberLifter::setTargetPosition, leftGrabberLifter::getCurrentPosition, rightGrabberLifter::getCurrentPosition);
        blockExtender = new BlockExtender(slider::setPosition, slider::getPosition);

        //leftGrabberLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //rightGrabberLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }


    @Override
    public void loop() {
        super.loop();

        if (toggleButtonA.getCurrent()) {
            towerLifter.lift();
        }

        if (toggleButtonB.getCurrent()) {
            towerLifter.down();
        }

        if (gamepad1.right_bumper) {
            towerLifter.stop();
        }




        telemetry.addData("left motor position", leftGrabberLifter.getCurrentPosition());
        telemetry.addData("right motor position", rightGrabberLifter.getCurrentPosition());


        telemetry.addData("blockExtender position", slider.getPosition());

        telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
