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

@TeleOp(name = "SkottOp", group = "1")
public class SkottOp extends OpMode {

    private TowerBuilder towerBuilder;
    private TowerLifter towerLifter;
    private BlockLifter blockLifter;
    private TowerGrabber towerGrabber;
    private BlockExtender blockExtender;

    {
        msStuckDetectInit = 10_000;
    }

    private PushButton buttonRunScript = new PushButton(() -> gamepad2.x);

    private PairedButtons<Double> towerGrabberButtons = new PairedButtons<>(
            () -> gamepad1.left_bumper, () -> 0.0d,
            () -> gamepad1.right_bumper, () -> 1.0d,
            () -> towerGrabber.getPosition()
    );

    private PairedButtons<Double> towerLifterButtons = new PairedButtons<>(
            () -> gamepad1.b, 1.0d,
            () -> gamepad1.a, -1.0d,
            0.0d
    );

    private PairedButtons<Double> blockLifterButtons = new PairedButtons<>(
            () -> gamepad1.left_trigger != 0.0f, () -> (double) gamepad1.left_trigger,
            () -> gamepad1.right_trigger != 0.0f, () -> (double) -gamepad1.right_trigger,
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

        //MecanumPower mecanumPower = new MecanumPower(new PolarCoord(0.5, 0), 0);
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

        if (buttonRunScript.getCurrent()) {
            script = makeScript();
            script.start();
        }

        if (!script.isRunning()) {
            towerGrabber.setPosition(towerGrabberButtons.getValue());
            towerLifter.setPower(towerLifterButtons.getValue());
            blockLifter.setPower(blockLifterButtons.getValue());
            blockExtender.setPower(blockExtenderButtons.getValue());

            MecanumPower mecanumPower = MecanumPower.fromXYTurn(
                    sensitivity(gamepad1.right_stick_x, SENSITIVITY),
                    sensitivity(-gamepad1.right_stick_y, SENSITIVITY),
                    sensitivity(gamepad1.left_stick_x, SENSITIVITY)
            );

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