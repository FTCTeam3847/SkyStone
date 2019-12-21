package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.gamepad.PushButton;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.signum;

@TeleOp(name = "MoverOp", group = "1")
public class MoverOp extends OpMode {
    PushButton pushButtonA = new PushButton(() -> gamepad1.a);

    MoveAction moveCommand;

    SkystoneBot bot;

    @Override
    public void init() {

        bot = new DerpyBot(hardwareMap);
        bot.init();

        DriveCommand command = new DriveCommand(new PolarCoord(0.5, 0),0);
        moveCommand = new MoveAction(1, command, System::nanoTime, bot);

    }

    private static double sensitivity(double base, double exp) {
        return signum(base) * pow(abs(base), exp);
    }

    private static final int SENSITIVITY = 3;

    @Override
    public void loop() {
        if (pushButtonA.getCurrent()) {
            DriveCommand command = new DriveCommand(new PolarCoord(0.5, 0),0);
            moveCommand = new MoveAction(1, command, System::nanoTime, bot);
            moveCommand.start();
        }

        moveCommand.loop();
//        bot.move(sensitivity(gamepad1.right_stick_x, SENSITIVITY),
//                sensitivity(-gamepad1.right_stick_y, SENSITIVITY),
//                sensitivity(gamepad1.left_stick_x, SENSITIVITY));

        telemetry.addData("Started", moveCommand.started());
        telemetry.addData("Is done", moveCommand.isDone());
        telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
    }
}