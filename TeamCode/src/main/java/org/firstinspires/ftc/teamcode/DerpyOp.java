package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.action.DriveTrainAction;
import org.firstinspires.ftc.teamcode.action.MoveAction;
import org.firstinspires.ftc.teamcode.action.SequentialAction;
import org.firstinspires.ftc.teamcode.action.TurnToAction;
import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.controller.HeadingLocalizer;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.gamepad.PushButton;
import org.firstinspires.ftc.teamcode.polar.CartesianCoord;
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

    PushButton dPadUp = new PushButton(() -> gamepad1.dpad_up);
    PushButton dPadDown = new PushButton(() -> gamepad1.dpad_down);
    PushButton dPadLeft = new PushButton(() -> gamepad1.dpad_left);
    PushButton dPadRight = new PushButton(() -> gamepad1.dpad_right);


    PushButton pushButtonLeftBumper = new PushButton(() -> gamepad1.left_bumper);


    MoveAction moveAction;
    TurnToAction turnToAction;
    SequentialAction script;


    DerpyBot bot;

    public SequentialAction makeScript() {
        DriveTrainAction script = new DriveTrainAction(System::currentTimeMillis, bot)
                .strafe(3 * PI / 2, 1000, 1);
        return script;
    }

    public SequentialAction makeScriptY() {
        DriveTrainAction scriptY = new DriveTrainAction(System::currentTimeMillis, bot)
                .moveTo(new PolarCoord(68, .75*PI))
                .pause(500)
                .moveTo(new PolarCoord(68, .25*PI));
        return scriptY;
    }

    public SequentialAction makeScriptA() {
        DriveTrainAction scriptA = new DriveTrainAction(System::currentTimeMillis, bot)
                .moveTo(new CartesianCoord(-48, 48))
                .moveTo(new CartesianCoord(48, 48))
                .moveTo(new CartesianCoord(48, 0))
                .moveTo(new CartesianCoord(-48, 0));
        return scriptA;
    }

    public SequentialAction makeScriptB() {
        DriveTrainAction scriptB = new DriveTrainAction(System::currentTimeMillis, bot)
                .moveTo(new PolarCoord(60, 0.21 * PI)) //rear blue
                .pause(500)
                .turnTo(0.0)
                .pause(500)
                .turnTo(PI / 2)
                .pause(500)
                .moveTo(new PolarCoord(60, 0.30 * PI)) //home blue 2
                .pause(500)
                .turnTo(PI / 2)
                .pause(500)
                .moveTo(new PolarCoord(60, 0.70 * PI)) //home blue 1
                .pause(500)
                .turnTo(PI / 2)
                .pause(500)
                .turnTo(PI)
                .pause(500)
                .moveTo(new PolarCoord(61, 0.8 * PI)) //front blue
                .pause(500)
                .turnTo(PI)
                .pause(500)
                .moveTo(new PolarCoord(50, PI)) //front red
                .pause(500)
                .turnTo(PI)
                .pause(500)
                .turnTo(3 * PI / 2)
                .pause(500)
                .moveTo(new PolarCoord(36, PI)) //home red 1
                .pause(500)
                .turnTo(3 * PI / 2)
                .pause(500)
                ;
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



        if (!script.isRunning()) {
            if (gamepad1.dpad_up) {
                bot.getMecanumDrive().setPower(new MecanumPower(new PolarCoord(.5, 0),0));
            }
            else if (gamepad1.dpad_down) {
                bot.getMecanumDrive().setPower(new MecanumPower(new PolarCoord(.5, PI),0));
            }
            else if (gamepad1.dpad_left) {
                bot.getMecanumDrive().setPower(new MecanumPower(new PolarCoord(.5, PI/2),0));
            }
            else if (gamepad1.dpad_right) {
                bot.getMecanumDrive().setPower(new MecanumPower(new PolarCoord(.5, 3*PI/2),0));
            }
            else {
                MecanumPower mecanumPower = MecanumPower.fromXYTurn(
                        sensitivity(gamepad1.right_stick_x, SENSITIVITY),
                        sensitivity(-gamepad1.right_stick_y, SENSITIVITY),
                        sensitivity(gamepad1.left_stick_x, SENSITIVITY)
                );
                bot.getMecanumDrive().setPower(mecanumPower);
            }

        }

        if (pushButtonX.getCurrent()) {
            script.stop();
            bot.getMecanumDrive().setPower(MecanumPower.ZERO);
        }

        if (pushButtonY.getCurrent()) {
            //bot.combinedLocalizer.calibrate(new FieldPosition(new PolarCoord(37, 1.11*PI),3*PI/2));

            script = makeScriptY();
            script.start();
        }

        if (pushButtonA.getCurrent()) {
            script = makeScriptA();
            script.start();
        }

        if (pushButtonB.getCurrent()) {
            //bot.combinedLocalizer.calibrate(new FieldPosition(new PolarCoord(68, 3*PI/4),PI));
            bot.headingLocalizer.calibrate(0.0);
            script = makeScriptB();
            script.start();
        }

        if (pushButtonLeftBumper.getCurrent()) {
            bot.combinedLocalizer.calibrate(FieldPosition.ORIGIN);
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