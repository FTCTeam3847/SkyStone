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
import org.firstinspires.ftc.teamcode.action.TowerBuilderAction;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.gamepad.PairedButtons;
import org.firstinspires.ftc.teamcode.gamepad.PushButton;
import org.firstinspires.ftc.teamcode.gamepad.ToggleButton;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.signum;

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

    private PushButton buttonRunScript = new PushButton(() -> gamepad2.x);
    private ToggleButton toggleSlowMode = new ToggleButton(() -> gamepad1.right_stick_button);

    private PairedButtons<Double> towerGrabberButtons = new PairedButtons<>(
            () -> gamepad1.left_bumper, 0.0d,
            () -> gamepad1.right_bumper, 1.0d
    );

    private PairedButtons<Double> towerLifterButtons = new PairedButtons<>(
            () -> gamepad1.b, 1.0d,
            () -> gamepad1.a, -1.0d,
            0.0d
    );

    private PairedButtons<Double> blockLifterButtons = new PairedButtons<>(
            () -> gamepad1.left_trigger != 0.0d, () -> (double) gamepad1.left_trigger,
            () -> gamepad1.right_trigger != 0.0d, () -> (double) -gamepad1.right_trigger,
            () -> 0.0d
    );

    private PairedButtons<Double> blockExtenderButtons = new PairedButtons<>(
            () -> gamepad1.dpad_up, 1.0d,
            () -> gamepad1.dpad_down, -1.0d,
            0.0d
    );

    SequentialAction script;

    public SequentialAction makeScript() {
        TowerBuilderAction script = new TowerBuilderAction(System::currentTimeMillis, bot)
                .releaseTower()
                .grabTower()
                .doubleLift(1.0)
                .extendBlock()
                .releaseBlock()
                .retractBlock()
                .doubleLift(0.0)
                .releaseTower()
                ;
        return script;
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
        boolean slowMode = toggleSlowMode.getCurrent();

        if (buttonRunScript.getCurrent()) {
            script = makeScript();
            script.start();
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

            MecanumPower mecanumPower = MecanumPower.fromGamepadXYTurn(
                    sensitivity(gamepad1.right_stick_x, SENSITIVITY),
                    sensitivity(-gamepad1.right_stick_y, SENSITIVITY),
                    sensitivity(gamepad1.left_stick_x, SENSITIVITY)
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