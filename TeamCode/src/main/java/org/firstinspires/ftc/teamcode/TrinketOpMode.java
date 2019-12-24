// left  0.30 up, 0.14 down
// right 0.30 up, 0.11 down

// leftPos  Start 0.60, End 0.15
// rightPos Start 0.25, End 0.70

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Trinkets.BlockExtender;
import org.firstinspires.ftc.teamcode.Trinkets.BlockGrabber;
import org.firstinspires.ftc.teamcode.Trinkets.BlockLifter;
import org.firstinspires.ftc.teamcode.Trinkets.TowerGrabber;
import org.firstinspires.ftc.teamcode.Trinkets.TowerLifter;
import org.firstinspires.ftc.teamcode.gamepad.PushButton;
import org.firstinspires.ftc.teamcode.gamepad.ToggleButton;


@TeleOp(name = "TrinketOpMode", group = "1")
public class TrinketOpMode extends BaseOp {
    ToggleButton toggleRunMode = new ToggleButton(() -> gamepad1.right_stick_button);
    PushButton resetEncoder = new PushButton(() -> gamepad1.left_stick_button);

    PushButton leftUpFaster = new PushButton(() -> gamepad1.left_stick_y < 0);
    PushButton leftUpSlower = new PushButton(() -> gamepad1.left_stick_y > 0);
    PushButton leftDownFaster = new PushButton(() -> gamepad1.left_stick_x > 0);
    PushButton leftDownSlower = new PushButton(() -> gamepad1.left_stick_x < 0);

    PushButton rightUpFaster = new PushButton(() -> gamepad1.right_stick_y < 0);
    PushButton rightUpSlower = new PushButton(() -> gamepad1.right_stick_y > 0);
    PushButton rightDownFaster = new PushButton(() -> gamepad1.right_stick_x > 0);
    PushButton rightDownSlower = new PushButton(() -> gamepad1.right_stick_x < 0);

    PushButton leftPosUp = new PushButton(() -> gamepad1.dpad_right);
    PushButton leftPosDown = new PushButton(() -> gamepad1.dpad_left);

    PushButton rightPosUp = new PushButton(() -> gamepad1.dpad_up);
    PushButton rightPosDown = new PushButton(() -> gamepad1.dpad_down);

    BlockExtender blockExtender;
    BlockGrabber blockGrabber;
    BlockLifter blockLifter;
    TowerGrabber towerGrabber;
    TowerLifter towerLifter;


    double speedLeftUp = 0.30;
    double speedLeftDown = 0.14;
    double speedRightUp = 0.30;
    double speedRightDown = 0.11;

    double leftPosStart = 0.60;
    double leftPosEnd = 0.15;
    double rightPosStart = 0.25;
    double rightPosEnd = 0.70;

    double blockGrabberOpen = 0.8;
    double blockGrabberClosed = 0.5;

    @Override
    public void init() {
        telemetry.addLine("Initializing...");
        telemetry.update();


        super.init();

        towerLifter =
                new TowerLifter(
                        leftTowerLifter::setPower,
                        rightTowerLifter::setPower,
                        leftTowerLifter::getCurrentPosition,
                        rightTowerLifter::getCurrentPosition
                );
        blockExtender =
                new BlockExtender(
                        extender::setPower,
                        extender::getPower
                );
        blockGrabber =
                new BlockGrabber(
                        grabber::setPosition,
                        grabber::getPosition
                );
        blockLifter =
                new BlockLifter(
                        leftBlockLifter::setPower,
                        rightBlockLifter::setPower,
                        leftBlockLifter::getPower,
                        rightBlockLifter::getPower
                );
        towerGrabber =
                new TowerGrabber(
                        leftTowerGrabber::setPosition,
                        rightTowerGrabber::setPosition,
                        leftTowerGrabber::getPosition,
                        rightTowerGrabber::getPosition
                );

        leftTowerLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightTowerLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftTowerLifter.setMode((DcMotor.RunMode.RUN_USING_ENCODER));
        rightTowerLifter.setMode((DcMotor.RunMode.RUN_USING_ENCODER));

    }

    @Override
    public void init_loop() {
        super.init_loop();
        telemetry.addLine("Initialized.");
        telemetry.update();
    }

    @Override
    public void loop() {
        super.loop();

        if (gamepad1.left_bumper) {
            leftBlockLifter.setPower(0.5);
            rightBlockLifter.setPower(-0.5);
        } else if (gamepad1.right_bumper) {
            leftBlockLifter.setPower(-0.5);
            rightBlockLifter.setPower(0.5);
        } else {
            leftBlockLifter.setPower(0);
            rightBlockLifter.setPower(0);
        }

        if (gamepad1.left_stick_button) {
            grabber.setPosition(blockGrabberOpen);
        } else if (gamepad1.right_stick_button) {
            grabber.setPosition(blockGrabberClosed);
        }

        if (gamepad1.x) {
            leftTowerGrabber.setPosition(leftTowerGrabber.getPosition() + 0.01);
            rightTowerGrabber.setPosition(rightTowerGrabber.getPosition() - 0.01);
        }

        if (gamepad1.y) {
            leftTowerGrabber.setPosition(leftTowerGrabber.getPosition() - 0.01);
            rightTowerGrabber.setPosition(rightTowerGrabber.getPosition() + 0.01);
        }

        if (resetEncoder.getCurrent()) {
            leftTowerLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightTowerLifter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        if (toggleRunMode.getCurrent()) {
            leftTowerLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightTowerLifter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            telemetry.addLine("speed mode, with encoder");
        } else {
            leftTowerLifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightTowerLifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telemetry.addLine("power mode, without encoder");
        }

        if (gamepad1.dpad_up) {
            extender.setPower(-0.8);
        } else if (gamepad1.dpad_down) {
            extender.setPower(0.8);
        } else {
            extender.setPower(0);
        }

        if (gamepad1.a) {
            towerLifter.lift(speedLeftUp, speedRightUp);
        } else if (gamepad1.b) {
            towerLifter.down(speedLeftDown, speedRightDown);
        } else {
            towerLifter.stop();
        }

        telemetry.addData("left motor position", leftTowerLifter.getCurrentPosition());
        telemetry.addData("right motor position", rightTowerLifter.getCurrentPosition());
        telemetry.addData("towerGrabberPos", "left:[%.2f, %.2f] right:[%.2f, %.2f]", leftPosStart, leftPosEnd, rightPosStart, rightPosEnd);

        telemetry.addData("towerSpeed", "left:[%.2f, %.2f] right:[%.2f, %.2f]", speedLeftUp, speedLeftDown, speedRightUp, speedRightDown);
        telemetry.addData("block lifter", "left: %.2f right: %.2f", leftBlockLifter.getPower(), rightBlockLifter.getPower());

        telemetry.addData("blockLifter", blockLifter.toString());
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
