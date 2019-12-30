package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.action.MoveAction;
import org.firstinspires.ftc.teamcode.action.SequentialAction;
import org.firstinspires.ftc.teamcode.action.SkystoneAction;
import org.firstinspires.ftc.teamcode.action.TurnToAction;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.gamepad.PushButton;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.signum;

@TeleOp(name = "DerpyOp", group = "1")
public class DerpyOp extends OpMode {

    PushButton pushButtonX = new PushButton(() -> gamepad1.x);

    MoveAction moveAction;
    TurnToAction turnToAction;
    SequentialAction script;

    SkystoneBot bot;

    public SequentialAction makeScript() {
        SkystoneAction script = new SkystoneAction(System::currentTimeMillis, bot)
                .strafe(PI / 2, 3000, .5)
                .pause(500)
                .strafe(3 * PI / 2, 3000, .5)
                .pause(500)
                .moveForward(3000, .5)
                .pause(500)
                .turn(PI/4)
                .pause(500)
                .moveBackwards(3000, .5);
        return script;
    }

    @Override
    public void init() {
        bot = new DerpyBot(hardwareMap, telemetry);
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

        if (pushButtonX.getCurrent()) {
            script = makeScript();
            script.start();
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
        telemetry.addData("current heading", "%.2f PI", bot.getFieldRelativeHeading() / PI);
        telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
        bot.stop();
    }
}