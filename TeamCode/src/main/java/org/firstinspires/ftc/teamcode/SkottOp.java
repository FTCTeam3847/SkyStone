package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.action.MoveAction;
import org.firstinspires.ftc.teamcode.action.SequentialAction;
import org.firstinspires.ftc.teamcode.action.TowerBuilderAction;
import org.firstinspires.ftc.teamcode.action.TurnToAction;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.gamepad.PushButton;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.signum;

@TeleOp(name = "SkottOp", group = "1")
public class SkottOp extends OpMode {
    {
        msStuckDetectInit = 10_000;
    }

    PushButton buttonRunScript = new PushButton(() -> gamepad2.x);

    MoveAction moveAction;
    TurnToAction turnToAction;
    SequentialAction script;

    SkystoneBot bot;

    public SequentialAction makeScript() {
        TowerBuilderAction script = new TowerBuilderAction(System::currentTimeMillis, bot)
                .grabBlock(1.0)
                .pause(500)
                .open()
                .pause(500)
                .close()
                .pause(500)
                .liftTower(1.0)
                .pause(500)
                .liftBlock(0.8)
                .pause(500)
                .extendBlock(1.0)
                .pause(500)
                .grabBlock(0.0)
                .pause(500)
                .extendBlock(0.0)
                .pause(500)
                .liftBlock(0.0)
                .pause(500)
                .liftTower(0.0)
                .pause(500)
                ;
        return script;
    }

    @Override
    public void init() {
        bot = new SkottBot(hardwareMap, telemetry);
        bot.init();

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
            if (gamepad1.b) {
                bot.getTowerBuilder().lifter.setPower(1.0d);
            } else if (gamepad1.a) {
                bot.getTowerBuilder().lifter.setPower(-1.0d);
            } else {
                bot.getTowerBuilder().lifter.setPower(0.0d);
            }

            if (gamepad1.left_trigger != 0.0) {
                bot.getTowerBuilder().blockLifter.setPower(-gamepad1.left_trigger);
            } else if (gamepad1.right_trigger != 0.0) {
                bot.getTowerBuilder().blockLifter.setPower(gamepad1.right_trigger);
            } else {
                bot.getTowerBuilder().blockLifter.setPower(0);
            }

            if (gamepad1.left_bumper) {
                bot.getTowerBuilder().grabber.setPosition(0.0);
            } else if (gamepad1.right_bumper) {
                bot.getTowerBuilder().grabber.setPosition(1.0);
            }

            if (gamepad1.dpad_up) {
                bot.getTowerBuilder().blockExtender.setPower(1.0);
            } else if (gamepad1.dpad_down) {
                bot.getTowerBuilder().blockExtender.setPower(-1.0);
            } else {
                bot.getTowerBuilder().blockExtender.setPower(0);
            }
        }

//        MecanumPower mecanumPower = MecanumPower.fromXYTurn(
//                sensitivity(gamepad1.right_stick_x, SENSITIVITY),
//                sensitivity(-gamepad1.right_stick_y, SENSITIVITY),
//                sensitivity(gamepad1.left_stick_x, SENSITIVITY)
//        );
//
//        bot.move(mecanumPower);
//
        telemetry.addData("script", script);
        telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
        bot.stop();
    }
}