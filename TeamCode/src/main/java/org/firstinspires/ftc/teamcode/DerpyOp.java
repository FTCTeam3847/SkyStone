package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.action.DriveTrainAction;
import org.firstinspires.ftc.teamcode.action.SequentialAction;
import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.gamepad.PushButton;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.signum;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_BLUE_SKYSTONE_2;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_BLUE_WALL;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_FRONT_WALL;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_IMAGE_BLUE_WALL_FRONT;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_IMAGE_BLUE_WALL_REAR;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_IMAGE_FRONT_WALL_BLUE;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_IMAGE_FRONT_WALL_RED;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_IMAGE_REAR_WALL_BLUE;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_IMAGE_RED_WALL_FRONT;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_REAR_WALL;
import static org.firstinspires.ftc.teamcode.GameConstants.FACING_RED_WALL;
import static org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower.mecanumPower;
import static org.firstinspires.ftc.teamcode.polar.PolarCoord.polar;

@TeleOp(name = "DerpyOp", group = "1")
public class DerpyOp extends OpMode {
    {
        msStuckDetectInit = 10_000;
    }

    PushButton pushButtonX = new PushButton(() -> gamepad1.x);
    PushButton pushButtonY = new PushButton(() -> gamepad1.y);
    PushButton pushButtonA = new PushButton(() -> gamepad1.a);
    PushButton pushButtonB = new PushButton(() -> gamepad1.b);

    PushButton pushButtonLeftBumper = new PushButton(() -> gamepad1.left_bumper);

    SequentialAction script;


    DerpyBot bot;

    public SequentialAction makeScript() {
        return new DriveTrainAction(System::currentTimeMillis, bot);
    }

    public SequentialAction startBlueNearDepotBackward() {
        return new DriveTrainAction(System::currentTimeMillis, bot)
                .run(() -> bot.headingLocalizer.lockCalibration(FACING_RED_WALL))
                .strafeTo(polar(68, .75 * PI))
                .pause(500)
                .strafeTo(polar(68, .25 * PI));
    }

    public SequentialAction startBlueSideNearDepot() {
        return new DriveTrainAction(System::currentTimeMillis, bot)
                .run(() -> bot.headingLocalizer.lockCalibration(FACING_RED_WALL))
                .moveForward(500, 0.5d)
                .turnTo(9 * PI / 8)
                .strafeTo(FACING_IMAGE_FRONT_WALL_BLUE)
                .turnTo(FACING_IMAGE_FRONT_WALL_BLUE.heading)
                .turnTo(FACING_RED_WALL)
                .strafeTo(FACING_BLUE_SKYSTONE_2);
    }

    public SequentialAction circumnavigateBlueSide() {
        return new DriveTrainAction(System::currentTimeMillis, bot)
                .run(() -> bot.headingLocalizer.lockCalibration(0.0))
                .strafeTo(FACING_IMAGE_REAR_WALL_BLUE)
                .turnTo(FACING_REAR_WALL)
                .turnTo(FACING_BLUE_WALL)
                .strafeTo(FACING_IMAGE_BLUE_WALL_REAR)
                .turnTo(FACING_BLUE_WALL)
                .strafeTo(FACING_IMAGE_BLUE_WALL_FRONT)
                .turnTo(FACING_BLUE_WALL)
                .turnTo(FACING_FRONT_WALL)
                .strafeTo(FACING_IMAGE_FRONT_WALL_BLUE)
                .turnTo(FACING_FRONT_WALL)
                .strafeTo(FACING_IMAGE_FRONT_WALL_RED)
                .turnTo(FACING_FRONT_WALL)
                .turnTo(FACING_RED_WALL)
                .strafeTo(FACING_IMAGE_RED_WALL_FRONT)
                .turnTo(FACING_RED_WALL);
    }

    @Override
    public void init() {
        bot = new DerpyBot(hardwareMap, telemetry);
        bot.init();

        //MecanumPower mecanumPower = mecanumPower(0.5, 0), 0);
        //moveAction = new MoveAction(1, mecanumPower, System::nanoTime, bot);
        //turnToAction = new TurnToAction(0, bot);

        script = makeScript();

    }

    private static double sensitivity(double base, double exp) {
        return signum(base) * pow(abs(base), exp);
    }

    private static final int SENSITIVITY = 3;

    @Override
    public void init_loop() {
        super.init_loop();
        bot.init_loop();
    }

    @Override
    public void loop() {
        bot.loop();
        script.loop();


        if (!script.isRunning()) {
            if (gamepad1.dpad_up) {
                bot.getMecanumDrive().setPower(mecanumPower(.5, 0, 0));
            } else if (gamepad1.dpad_down) {
                bot.getMecanumDrive().setPower(mecanumPower(.5, PI, 0));
            } else if (gamepad1.dpad_left) {
                bot.getMecanumDrive().setPower(mecanumPower(.5, PI / 2, 0));
            } else if (gamepad1.dpad_right) {
                bot.getMecanumDrive().setPower(mecanumPower(.5, 3 * PI / 2, 0));
            } else {
                MecanumPower mecanumPower = MecanumPower.fromGamepadXYTurn(
                        sensitivity(gamepad1.right_stick_x, SENSITIVITY),
                        sensitivity(-gamepad1.right_stick_y, SENSITIVITY),
                        sensitivity(gamepad1.left_stick_x, SENSITIVITY)
                );
                bot.getMecanumDrive().setPower(mecanumPower);
            }

        }

        if (pushButtonX.getCurrent()) {
            script.stop();
            bot.getMecanumDrive().setPower(MecanumPower.ZERO);
        }

        if (pushButtonY.getCurrent() && !script.isRunning()) {
            script.stop();
            script = startBlueNearDepotBackward();
            script.start();
        }

        if (pushButtonA.getCurrent() && !script.isRunning()) {
            script.stop();
            script = startBlueSideNearDepot();
            script.start();
        }

        if (pushButtonB.getCurrent() && !script.isRunning()) {
            script = circumnavigateBlueSide();
            script.start();
        }

        if (pushButtonLeftBumper.getCurrent()) {
            bot.combinedLocalizer.calibrate(FieldPosition.ORIGIN);
        }


        telemetry.addData("script", script);
        telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
        bot.stop();
    }
}