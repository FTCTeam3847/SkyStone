package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Trinkets.BlockExtender;
import org.firstinspires.ftc.teamcode.Trinkets.BlockGrabber;
import org.firstinspires.ftc.teamcode.Trinkets.BlockLifter;
import org.firstinspires.ftc.teamcode.Trinkets.TowerGrabber;
import org.firstinspires.ftc.teamcode.Trinkets.TowerLifter;
import org.firstinspires.ftc.teamcode.gamepad.ToggleButton;


@TeleOp(name = "TrinketOpMode", group = "1")
public class TrinketOpMode extends BaseOp {
    ToggleButton toggleButtonA = new ToggleButton(() -> gamepad1.a);
    ToggleButton toggleButtonB = new ToggleButton(() -> gamepad1.b);


    BlockExtender blockExtender;
    BlockGrabber blockGrabber;
    BlockLifter blockLifter;
    TowerGrabber towerGrabber;
    TowerLifter towerLifter;



    @Override
    public void init() {
        super.init();

        towerLifter = new TowerLifter(leftTowerLifter::setPower, rightTowerLifter::setPower, leftTowerLifter::getCurrentPosition, rightTowerLifter::getCurrentPosition);
        blockExtender = new BlockExtender(extender::setPower, extender::getPower);
        blockGrabber = new BlockGrabber(grabber::setPosition, grabber::getPosition);
        blockLifter = new BlockLifter(leftBlockLifter::setPower, rightBlockLifter::setPower, leftBlockLifter::getPower, rightBlockLifter::getPower);
        towerGrabber = new TowerGrabber(leftTowerGrabber::setPosition, rightTowerGrabber::setPosition, leftTowerGrabber::getPosition, rightTowerGrabber::getPosition);

    }


    @Override
    public void loop() {
        super.loop();
        if (toggleButtonA.getCurrent()) {
            if (leftTowerLifter.getCurrentPosition() > -2700) {
                towerLifter.lift();
            } else {
                towerLifter.stop();
            }
        }
        else if (toggleButtonB.getCurrent()) {
            if (leftTowerLifter.getCurrentPosition() < -100) {
                towerLifter.down();
            } else {
                towerLifter.stop();
            }
        }
        else {
            towerLifter.stop();
        }
//
        if (gamepad1.left_bumper) {
            leftTowerLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightTowerLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftTowerLifter.setMode((DcMotor.RunMode.RUN_WITHOUT_ENCODER));
            rightTowerLifter.setMode((DcMotor.RunMode.RUN_WITHOUT_ENCODER));

        }
//
//
//
//
            telemetry.addData("left motor position", leftTowerLifter.getCurrentPosition());
            telemetry.addData("right motor position", rightTowerLifter.getCurrentPosition());
//
//
//            telemetry.addData("blockExtender position", extender.getPower());
//
            telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
