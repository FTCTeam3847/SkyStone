package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Trinkets.BlockExtender;
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

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.signum;
import static org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower.*;

@TeleOp(name = "SkottOp", group = "1")
public class SkottOp extends OpMode {

    private TowerBuilder towerBuilder;
    private TowerLifter towerLifter;
    private BlockLifter blockLifter;
    private TowerGrabber towerGrabber;
    private BlockExtender blockExtender;

    private double testSpeed = 0.5d;
    private long testStart = 0L;
    private long testDuration = 0L;

    {
        msStuckDetectInit = 10_000;
    }

    private PushButton buttonRunScript = new PushButton(() -> gamepad2.back);
    private PushButton increment = new PushButton(() -> gamepad2.right_bumper);
    private PushButton decrement = new PushButton(() -> gamepad2.left_bumper);

    private PairedButtons<Double> testSpeedButtons = new PairedButtons<>(
            decrement::getCurrent, () -> -0.02d,
            increment::getCurrent, () -> 0.02d
    );

    private PushButton testStarted = new PushButton(() -> gamepad2.a || gamepad2.b || gamepad2.x || gamepad2.y || gamepad2.dpad_up || gamepad2.dpad_down || gamepad2.dpad_left || gamepad2.dpad_left);

    private PairedButtons<Double> towerGrabberButtons = new PairedButtons<>(
            () -> gamepad1.left_bumper, () -> 0.0d,
            () -> gamepad1.right_bumper, () -> 1.0d
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

    SkystoneBot bot;

    public SequentialAction makeScript() {
        TowerBuilderAction script = new TowerBuilderAction(System::currentTimeMillis, bot)
                .releaseTower()
                .grabTower()
                .liftTower()
                .liftBlock()
                .extendBlock()
                .releaseBlock()
                .retractBlock()
                .lowerBlock()
                .lowerTower(0.5)
                .releaseTower();
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
        telemetry.addData("testSpeed", "%.2f", testSpeed);

        bot.loop();
        script.loop();

        if (buttonRunScript.getCurrent()) {
            script = makeScript();
            script.start();
        }

        if (!script.isRunning()) {
            towerGrabberButtons.apply(towerGrabber::setPosition);
            towerLifterButtons.apply(towerLifter::setPower);
            blockLifterButtons.apply(blockLifter::setPower);
            blockExtenderButtons.apply(blockExtender::setPower);
            testSpeedButtons.apply(delta -> testSpeed += delta);

            MecanumPower mecanumPower = ZERO;

            if (testStarted.getCurrent()) {
                testStart = System.currentTimeMillis();
            }

            if (gamepad2.dpad_up) {
                mecanumPower = fwd(testSpeed);
            } else if (gamepad2.dpad_down) {
                mecanumPower = backwd(testSpeed);
            } else if (gamepad2.dpad_left) {
                mecanumPower = left(testSpeed);
            } else if (gamepad2.dpad_right) {
                mecanumPower = right(testSpeed);
            } else if (gamepad2.x) {
                mecanumPower = fwdleft(testSpeed);
            } else if (gamepad2.a) {
                mecanumPower = bkwdleft(testSpeed);
            } else if (gamepad2.b) {
                mecanumPower = bkwdright(testSpeed);
            } else if (gamepad2.y) {
                mecanumPower = fwdright(testSpeed);
            } else {
                mecanumPower = fromXYTurn(
                        sensitivity(gamepad1.right_stick_x, SENSITIVITY),
                        sensitivity(-gamepad1.right_stick_y, SENSITIVITY),
                        sensitivity(gamepad1.left_stick_x, SENSITIVITY)
                );
            }
            bot.move(mecanumPower);
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