package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Trinkets.BlockExtender;
import org.firstinspires.ftc.teamcode.Trinkets.BlockGrabber;
import org.firstinspires.ftc.teamcode.Trinkets.BlockLifter;
import org.firstinspires.ftc.teamcode.Trinkets.TowerBuilder;
import org.firstinspires.ftc.teamcode.Trinkets.TowerGrabber;
import org.firstinspires.ftc.teamcode.Trinkets.TowerLifter;
import org.firstinspires.ftc.teamcode.action.SequentialAction;
import org.firstinspires.ftc.teamcode.action.SkystoneActions;
import org.firstinspires.ftc.teamcode.action.SkystoneScripts;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.gamepad.PairedButtons;
import org.firstinspires.ftc.teamcode.gamepad.PushButton;
import org.firstinspires.ftc.teamcode.gamepad.ToggleButton;
import org.firstinspires.ftc.teamcode.Trinkets.CapstoneLifter;

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
import static org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower.fromGamepadXYTurn;

@TeleOp(name = "SkottOp", group = "1")
public class SkottOp extends OpMode {
    private SkystoneBot bot;
    private TowerBuilder towerBuilder;
    private TowerLifter towerLifter;
    private BlockLifter blockLifter;
    private TowerGrabber towerGrabber;
    private BlockExtender blockExtender;
    private BlockGrabber blockGrabber;
    private CapstoneLifter capstoneLifter;

    {
        msStuckDetectInit = 12_000;
    }

    private PushButton buttonAddBlockToTower2y = new PushButton(() -> gamepad2.y);

    private PushButton buttonRaiseTower3 = new PushButton(() -> gamepad2.b);

    private PushButton addBlockToTowerBack = new PushButton(() -> gamepad1.back);

    private PushButton buttonStopScript = new PushButton(
            () -> gamepad1.dpad_left || gamepad1.dpad_right || gamepad1.dpad_up || gamepad1.dpad_down
    );


    private ToggleButton toggleSlowMode = new ToggleButton(() -> gamepad2.right_stick_button || gamepad1.right_stick_button);

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
            () -> gamepad1.x, () -> -1.0d,
            () -> gamepad1.y, () -> 1.0d,
            () -> 0.0d
    );

    private PairedButtons<Double> blockExtenderButtons = new PairedButtons<>(
            () -> gamepad1.dpad_up, 1.0d,
            () -> gamepad1.dpad_down, -1.0d,
            0.0d
    );
    private PairedButtons<Double> capstoneLifterButtons = new PairedButtons<>(
            () -> gamepad1.dpad_left, 1.0d,
            () -> gamepad1.dpad_right, -1.0d,
            0.0d
    );

    SequentialAction script;
    private SkystoneScripts scripts;

    protected long loopEndMs;

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
        capstoneLifter = towerBuilder.capstoneLifter;

        scripts = new SkystoneScripts(bot);
        script = scripts.emptyScript();

        loopEndMs = System.currentTimeMillis();
    }

    private static double sensitivity(double base, double exp) {
        return signum(base) * pow(abs(base), exp);
    }

    private static final int SENSITIVITY = 2;

    @Override
    public void init_loop() {
        super.init_loop();
        bot.init_loop();
        updateTelemetry();
    }

    @Override
    public void loop() {
        bot.loop();
        script.loop();
        boolean slowMode = toggleSlowMode.getCurrent();


        if (buttonStopScript.getCurrent()) {
            script.stop();
            bot.getMecanumDrive().stop();
        }

        if (buttonAddBlockToTower2y.getCurrent()) {
            script.stop();
            script = scripts.addBlockToTower().start();
        }

        if (buttonRaiseTower3.getCurrent()) {
            towerLifter.setPosition(.3);
        }

        if (!script.isRunning()) {
            towerGrabberButtons.apply(blockGrabber::setPosition);
            towerLifterButtons.apply(towerLifter::setPower);
            blockLifterButtons.apply(blockLifter::setPower);
            blockExtenderButtons.apply(blockExtender::setPower);
            capstoneLifterButtons.apply(capstoneLifter::setPower);

            double tehSpeeds = 0.1;

            if (gamepad1.right_trigger > 0) {
                towerGrabber.setPosition(towerGrabber.getPosition() + tehSpeeds);
            }

            if (gamepad1.left_trigger > 0) {
                towerGrabber.setPosition(towerGrabber.getPosition() - tehSpeeds);
            }

            if (addBlockToTowerBack.getCurrent()) {
                script.stop();
                script = scripts.addBlockToTower().start();
            }

            MecanumPower mecanumPower;

            if (gamepad2.left_trigger > 0) {
                mecanumPower = fromGamepadXYTurn(-gamepad2.left_trigger, 0, 0).scale(0.2);
            } else if (gamepad2.right_trigger > 0) {
                mecanumPower = fromGamepadXYTurn(gamepad2.right_trigger, 0, 0).scale(0.2);
            } else {
                // Prash Drive
                mecanumPower = fromGamepadXYTurn(
                        sensitivity(gamepad2.left_stick_x, SENSITIVITY),
                        sensitivity(-gamepad2.left_stick_y, SENSITIVITY),
                        sensitivity(gamepad2.right_stick_x, SENSITIVITY)
                );
            }

            if (mecanumPower.strafe.radius < 0.0001 && abs(mecanumPower.turn) < 0.0001) {
                // Charlie Drive
                mecanumPower = fromGamepadXYTurn(
                        sensitivity(gamepad1.right_stick_x, SENSITIVITY),
                        sensitivity(-gamepad1.right_stick_y, SENSITIVITY),
                        sensitivity(gamepad1.left_stick_x * 0.85, SENSITIVITY)
                );
            }

            mecanumPower = slowMode ? mecanumPower.scale(0.5) : mecanumPower;

            bot.getMecanumDrive().setPower(mecanumPower);
        }

        telemetry.addData("slowMode", slowMode);
        telemetry.addData("tower", towerBuilder);
        telemetry.addData("script", script);
        updateTelemetry();
    }

    private void updateTelemetry() {
        long now = System.currentTimeMillis();
        telemetry.addData("loopMs", now - loopEndMs);
        loopEndMs = System.currentTimeMillis();
        telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
        bot.stop();
    }
}