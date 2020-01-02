package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.action.DriveTrainAction;
import org.firstinspires.ftc.teamcode.action.MoveAction;
import org.firstinspires.ftc.teamcode.action.SequentialAction;
import org.firstinspires.ftc.teamcode.action.TurnToAction;
import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.gamepad.PushButton;
import org.firstinspires.ftc.teamcode.polar.PolarCoord;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.signum;

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


    MoveAction moveAction;
    TurnToAction turnToAction;
    SequentialAction script;

    SequentialAction scriptY;
    SequentialAction scriptA;
    SequentialAction scriptB;


    DerpyBot bot;

    public SequentialAction makeScript() {
        DriveTrainAction script = new DriveTrainAction(System::currentTimeMillis, bot)
                .strafe(3 * PI / 2, 1000, 1);
        return script;
    }

    public SequentialAction makeScriptY() {
        DriveTrainAction scriptY = new DriveTrainAction(System::currentTimeMillis, bot)
                .strafe(0, 1000, 1);
        return scriptY;
    }

    public SequentialAction makeScriptA() {
        DriveTrainAction scriptA = new DriveTrainAction(System::currentTimeMillis, bot)
                .strafe(PI, 1000, 1);
        return scriptA;
    }

    public SequentialAction makeScriptB() {
        DriveTrainAction scriptB = new DriveTrainAction(System::currentTimeMillis, bot)
                .moveTo(new PolarCoord(60, 0.7 * PI))
                .pause(500)
                .moveTo(new PolarCoord(60, 0.3 * PI))
                ;
        return scriptB;
    }

    @Override
    public void init() {
        bot = new DerpyBot(hardwareMap, telemetry);
        bot.init();

        //MecanumPower mecanumPower = new MecanumPower(new PolarCoord(0.5, 0), 0);
        //moveAction = new MoveAction(1, mecanumPower, System::nanoTime, bot);
        //turnToAction = new TurnToAction(0, bot);

        script = makeScript();
        scriptY = makeScriptY();
        scriptA = makeScriptA();
        scriptB = makeScriptB();

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


//        MecanumPower mecanumPower = MecanumPower.fromXYTurn(
//                sensitivity(gamepad1.right_stick_x, SENSITIVITY),
//                sensitivity(-gamepad1.right_stick_y, SENSITIVITY),
//                sensitivity(gamepad1.left_stick_x, SENSITIVITY)
//        );
//        bot.getMecanumDrive().setPower(mecanumPower);
//
//        script.loop();
//        scriptY.loop();
//        scriptA.loop();
        scriptB.loop();
        telemetry.addData("script", scriptB);


        if (pushButtonX.getCurrent()) {
            script = makeScript();
            script.start();
        }

        if (pushButtonY.getCurrent()) {
            scriptY = makeScriptY();
            scriptY.start();
        }

        if (pushButtonA.getCurrent()) {
            scriptA = makeScriptA();
            scriptA.start();
        }

        if (pushButtonB.getCurrent()) {
            scriptB = makeScriptB();
            scriptB.start();
        }

        if (pushButtonLeftBumper.getCurrent()) {
            bot.combinedLocalizer.calibrate(FieldPosition.ORIGIN);
        }


//
//        telemetry.addData("script", script);
//        telemetry.addData("current heading", "%.2f PI", bot.getFieldRelativeHeading() / PI);
//        telemetry.addData("actualScript", script.toString());
        telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
        bot.stop();
    }
}