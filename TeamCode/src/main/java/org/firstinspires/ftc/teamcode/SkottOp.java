package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Trinkets.BlockExtender;
import org.firstinspires.ftc.teamcode.Trinkets.BlockGrabber;
import org.firstinspires.ftc.teamcode.Trinkets.BlockLifter;
import org.firstinspires.ftc.teamcode.Trinkets.TowerBuilder;
import org.firstinspires.ftc.teamcode.Trinkets.TowerGrabber;
import org.firstinspires.ftc.teamcode.Trinkets.TowerLifter;
import org.firstinspires.ftc.teamcode.action.SkystoneActions;
import org.firstinspires.ftc.teamcode.action.SequentialAction;
import org.firstinspires.ftc.teamcode.action.SkystoneScripts;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.gamepad.PairedButtons;
import org.firstinspires.ftc.teamcode.gamepad.PushButton;
import org.firstinspires.ftc.teamcode.gamepad.ToggleButton;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.signum;
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
import static org.firstinspires.ftc.teamcode.controller.FieldPosition.fieldPosition;

@TeleOp(name = "SkottOp", group = "1")
public class SkottOp extends OpMode {
    private SkystoneBot bot;
    private TowerBuilder towerBuilder;
    private TowerLifter towerLifter;
    private BlockLifter blockLifter;
    private TowerGrabber towerGrabber;
    private BlockExtender blockExtender;
    private BlockGrabber blockGrabber;

    {
        msStuckDetectInit = 10_000;
    }

    private PushButton buttonAddBlockToTower2y = new PushButton(() -> gamepad2.y);
    private PushButton buttonBlueSideSkystone2b = new PushButton(() -> gamepad2.b && !gamepad1.start && !gamepad2.start);
    private PushButton buttonBlueSideSkystone2a = new PushButton(() -> gamepad2.a && !gamepad1.start && !gamepad2.start);

    private PushButton buttonRedSideSkystone2L = new PushButton(() -> gamepad2.left_bumper && !gamepad1.start && !gamepad2.start);
    private PushButton buttonRedSideSkystone2R = new PushButton(() -> gamepad2.right_bumper && !gamepad1.start && !gamepad2.start);

    private PushButton buttonRedSideOuterNoFoundation2DpadL = new PushButton(() -> gamepad2.dpad_left && !gamepad1.start && !gamepad2.start);
    private PushButton buttonRedSideInnerNoFoundation2DpadR = new PushButton(() -> gamepad2.dpad_right && !gamepad1.start && !gamepad2.start);
    private PushButton buttonBlueSideOuterNoFoundation2DpadUp = new PushButton(() -> gamepad2.dpad_up && !gamepad1.start && !gamepad2.start);
    private PushButton buttonBlueSideInnerNoFoundation2DpadDown = new PushButton(() -> gamepad2.dpad_down && !gamepad1.start && !gamepad2.start);


    private PushButton addBlockToTowerBack = new PushButton(() -> gamepad1.back);

    private PushButton buttonStopScript = new PushButton(() -> gamepad2.x);


    private ToggleButton toggleSlowMode = new ToggleButton(() -> gamepad2.right_stick_button);

    private PairedButtons<Double> towerGrabberButtons = new PairedButtons<>(
            () -> gamepad1.left_bumper, 1.0d,
            () -> gamepad1.right_bumper, 0.0d
    );

    private PairedButtons<Double> towerLifterButtons = new PairedButtons<>(
            () -> gamepad1.b && !gamepad1.start && !gamepad2.start, 1.0d,
            () -> gamepad1.a && !gamepad1.start && !gamepad2.start, -1.0d,
            0.0d
    );

    private PairedButtons<Double> blockLifterButtons = new PairedButtons<>(
            () -> gamepad1.left_trigger != 0.0d, () -> (double) -gamepad1.left_trigger,
            () -> gamepad1.right_trigger != 0.0d, () -> (double) gamepad1.right_trigger,
            () -> 0.0d
    );

    private PairedButtons<Double> blockExtenderButtons = new PairedButtons<>(
            () -> gamepad1.dpad_up, 1.0d,
            () -> gamepad1.dpad_down, -1.0d,
            0.0d
    );

    SequentialAction script;
    private SkystoneScripts scripts;


    public SequentialAction circumnavigateBlueSide() {
        return new SkystoneActions(System::currentTimeMillis, bot)
//                .run(() -> bot.headingLocalizer.lockCalibration(0.0))
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
        bot = new SkottBot(hardwareMap, telemetry);
        bot.init();
        towerBuilder = bot.getTowerBuilder();
        towerLifter = towerBuilder.towerLifter;
        blockLifter = towerBuilder.blockLifter;
        towerGrabber = towerBuilder.towerGrabber;
        blockExtender = towerBuilder.blockExtender;
        blockGrabber = towerBuilder.blockGrabber;

        scripts = new SkystoneScripts(bot);
        script = scripts.emptyScript();
    }

    private static double sensitivity(double base, double exp) {
        return signum(base) * pow(abs(base), exp);
    }

    private static final int SENSITIVITY = 1;

    @Override
    public void init_loop() {
        super.init_loop();
        bot.init_loop();
    }

    @Override
    public void loop() {
        bot.loop();
        script.loop();
        boolean slowMode = toggleSlowMode.getCurrent();


        if(buttonStopScript.getCurrent())
        {
            script.stop();
            bot.getMecanumDrive().setPower(MecanumPower.ZERO);
        }

        if (buttonAddBlockToTower2y.getCurrent()) {
            script.stop();
            script = scripts.addBlockToTower().start();
        }

        if (buttonBlueSideSkystone2b.getCurrent()) {
            script.stop();
            script = scripts.blueSideSkystoneOuter().start();
        }

        if (buttonBlueSideSkystone2a.getCurrent()) {
            script.stop();
            script = scripts.blueSideSkystoneInner().start();
        }


        if (buttonRedSideSkystone2L.getCurrent()) {
            script.stop();
            script = scripts.redSideSkystoneOuter().start();
        }

        if (buttonRedSideSkystone2R.getCurrent()) {
            script.stop();
            script = scripts.redSideSkystoneInner().start();
        }

        if (buttonRedSideOuterNoFoundation2DpadL.getCurrent()) {
            script.stop();
            script = scripts.redSideSkystoneOuterNoFoundation().start();
        }

        if (buttonRedSideInnerNoFoundation2DpadR.getCurrent()) {
            script.stop();
            script = scripts.redSideSkystoneInnerNoFoundation().start();
        }

        if (buttonBlueSideInnerNoFoundation2DpadDown.getCurrent()) {
            script.stop();
            script = scripts.blueSideSkystoneInnerNoFoundation().start();
        }

        if (buttonBlueSideOuterNoFoundation2DpadUp.getCurrent()) {
            script.stop();
            script = scripts.blueSideSkystoneInnerNoFoundation().start();
        }


        if (!script.isRunning()) {
            towerGrabberButtons.apply(blockGrabber::setPosition);
            towerLifterButtons.apply(towerLifter::setPower);
            blockLifterButtons.apply(blockLifter::setPower);
            blockExtenderButtons.apply(blockExtender::setPower);

            double tehSpeeds = 0.05;

            if (gamepad1.y) {
                towerGrabber.setPosition(towerGrabber.getPosition() + tehSpeeds);
            }

            if (gamepad1.x) {
                towerGrabber.setPosition(towerGrabber.getPosition() - tehSpeeds);
            }

            if (addBlockToTowerBack.getCurrent()) {
                script.stop();
                script = scripts.addBlockToTower().start();
            }

            //Changed to gamepad 2 temporarily
            MecanumPower mecanumPower = MecanumPower.fromGamepadXYTurn(
                    sensitivity(gamepad2.left_stick_x, SENSITIVITY),
                    sensitivity(-gamepad2.left_stick_y, SENSITIVITY),
                    sensitivity(gamepad2.right_stick_x, SENSITIVITY)
            );

            mecanumPower = slowMode ? mecanumPower.scale(0.5) : mecanumPower;

            bot.getMecanumDrive().setPower(mecanumPower);
        }

        telemetry.addData("slowMode", slowMode);
        telemetry.addData("tower", towerBuilder);
        telemetry.addData("script", script);
        telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
        bot.stop();
    }
}