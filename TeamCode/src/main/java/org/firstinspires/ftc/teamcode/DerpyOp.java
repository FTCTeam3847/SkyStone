package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.action.MoveAction;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.gamepad.PushButton;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.signum;

@TeleOp(name = "DerpyOp", group = "1")
public class DerpyOp extends OpMode {
    PushButton pushButtonA = new PushButton(() -> gamepad1.a);

    MoveAction moveAction;

    SkystoneBot bot;

    @Override
    public void init() {
        bot = new DerpyBot(hardwareMap, telemetry);
        bot.init();

        MecanumPower mecanumPower = new MecanumPower(new PolarCoord(0.5, 0), 0);
        moveAction = new MoveAction(1, mecanumPower, System::nanoTime, bot);

    }

    private static double sensitivity(double base, double exp) {
        return signum(base) * pow(abs(base), exp);
    }

    private static final int SENSITIVITY = 3;

    @Override
    public void loop() {
        bot.loop();

        if (pushButtonA.getCurrent()) {
            MecanumPower command = new MecanumPower(new PolarCoord(0.5, 0), 0);
            moveAction = new MoveAction(1, command, System::nanoTime, bot);
            moveAction.start();
        }

        moveAction.loop();
//        bot.move(sensitivity(gamepad1.right_stick_x, SENSITIVITY),
//                sensitivity(-gamepad1.right_stick_y, SENSITIVITY),
//                sensitivity(gamepad1.left_stick_x, SENSITIVITY));

        telemetry.addData("Started", moveAction.started());
        telemetry.addData("Is done", moveAction.isDone());
        telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
    }
}