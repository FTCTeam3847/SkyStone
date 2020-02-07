package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.action.SequentialAction;
import org.firstinspires.ftc.teamcode.action.SkystoneActions;
import org.firstinspires.ftc.teamcode.action.SkystoneScripts;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;
import org.firstinspires.ftc.teamcode.gamepad.OptionsButton;
import org.firstinspires.ftc.teamcode.gamepad.PushButton;

import static java.lang.Math.PI;
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
import static org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower.mecanumPower;

@TeleOp(name = "DerpyOp", group = "1")
public class DerpyOp extends OpMode {
    {
        msStuckDetectInit = 12_000;
    }

    PushButton pushButtonX = new PushButton(() -> gamepad1.x && !gamepad1.start);
    PushButton pushButtonY = new PushButton(() -> gamepad1.y && !gamepad1.start);
    PushButton pushButtonA = new PushButton(() -> gamepad1.a && !gamepad1.start);
    PushButton pushButtonB = new PushButton(() -> gamepad1.b && !gamepad1.start);

    OptionsButton<Double> headingResetBtn = new OptionsButton<>(
            () -> gamepad1.right_stick_button,
            FACING_REAR_WALL, FACING_BLUE_WALL, FACING_FRONT_WALL, FACING_RED_WALL
    );
    PushButton pushButtonLeftStick = new PushButton(() -> gamepad1.left_stick_button);

    PushButton pushButtonLeftBumper = new PushButton(() -> gamepad1.left_bumper);

    SequentialAction script;
    SkystoneScripts scripts;


    DerpyBot bot;

    public SkystoneActions newScript() {
        if (null != script) script.stop();
        return new SkystoneActions(System::currentTimeMillis, bot);
    }



    public SequentialAction strafeFrontWallToRed() {
        return new SkystoneActions(System::currentTimeMillis, bot)
                .turnTo(FACING_FRONT_WALL)
                .strafeTo(FACING_IMAGE_FRONT_WALL_BLUE)
                .turnTo(FACING_FRONT_WALL)
                .strafeTo(FACING_IMAGE_FRONT_WALL_RED)
                .turnTo(FACING_FRONT_WALL)
                .turnTo(FACING_RED_WALL)
                .strafeTo(FACING_IMAGE_RED_WALL_FRONT)
                .turnTo(FACING_RED_WALL)
                ;
    }

    public SequentialAction circumnavigateBlueSide() {
        return new SkystoneActions(System::currentTimeMillis, bot)
//                .run(() -> bot.headingLocalizer.lockCalibration(0.0))
                .strafeTo(FACING_IMAGE_REAR_WALL_BLUE)
                .turnTo(FACING_REAR_WALL)
                .turnTo(FACING_BLUE_WALL)
                .strafeTo(FACING_IMAGE_BLUE_WALL_REAR)
                .turnTo(FACING_BLUE_WALL)
                .strafeTo(FACING_IMAGE_BLUE_WALL_FRONT)
                .turnTo(FACING_BLUE_WALL)
                .turnTo(FACING_FRONT_WALL)
                .strafeTo(FACING_IMAGE_FRONT_WALL_BLUE)
                .turnTo(FACING_FRONT_WALL)
                .strafeTo(FACING_IMAGE_FRONT_WALL_RED)
                .turnTo(FACING_FRONT_WALL)
                .turnTo(FACING_RED_WALL)
                .strafeTo(FACING_IMAGE_RED_WALL_FRONT)
                .turnTo(FACING_RED_WALL);
    }

    @Override
    public void init() {
        bot = new DerpyBot(hardwareMap, telemetry, System::nanoTime);
        bot.init();

        //MecanumPower mecanumPower = mecanumPower(0.5, 0), 0);
        //moveAction = new MoveAction(1, mecanumPower, System::nanoTime, bot);
        //turnToAction = new TurnToAction(0, bot);

        script = newScript();
        scripts = new SkystoneScripts(bot);

    }

    private static double sensitivity(double base, double exp) {
        return signum(base) * pow(abs(base), exp);
    }

    private static final int SENSITIVITY = 3;

    @Override
    public void init_loop() {
        super.init_loop();
        bot.init_loop();
//        headingResetBtn.apply(bot.headingLocalizer::lockCalibration);
    }

    @Override
    public void loop() {
        bot.loop();
        script.loop();

//        headingResetBtn.apply(bot.headingLocalizer::lockCalibration);

        if (!script.isRunning()) {
            if (gamepad1.dpad_up) {
                bot.getMecanumDrive().setPower(mecanumPower(.5, 0, 0));
            } else if (gamepad1.dpad_down) {
                bot.getMecanumDrive().setPower(mecanumPower(.5, PI, 0));
            } else if (gamepad1.dpad_left) {
                bot.getMecanumDrive().setPower(mecanumPower(.5, PI / 2, 0));
            } else if (gamepad1.dpad_right) {
                bot.getMecanumDrive().setPower(mecanumPower(.5, 3 * PI / 2, 0));
            } else {
                MecanumPower mecanumPower = MecanumPower.fromGamepadXYTurn(
                        sensitivity(gamepad1.right_stick_x, SENSITIVITY),
                        sensitivity(-gamepad1.right_stick_y, SENSITIVITY),
                        sensitivity(gamepad1.left_stick_x, SENSITIVITY)
                );
                bot.getMecanumDrive().setPower(mecanumPower);
            }

        }

        if (pushButtonX.getCurrent()) {
            script.stop();
            bot.getMecanumDrive().stop();
        }

        if (pushButtonLeftStick.getCurrent() && !script.isRunning()) {
            script = newScript().turnTo(0).start();
        }

        if (pushButtonY.getCurrent() && !script.isRunning()) {
            script = circumnavigateBlueSide().start();
        }

        if (pushButtonA.getCurrent() && !script.isRunning()) {
            script = strafeFrontWallToRed().start();
        }

        if (pushButtonB.getCurrent() && !script.isRunning()) {
            script = scripts.blueDepotParkOuter().start();
        }

        if (pushButtonLeftBumper.getCurrent()) {
            script = newScript().turnToLocate().start();
        }

        boolean skystone = false;
        double red = bot.sensorColor.red();
        double blue = bot.sensorColor.blue();
        double alpha = bot.sensorColor.alpha();
        double green = bot.sensorColor.green();
        double tolerence = 0.25;
        double distance = bot.sensorDistance.getDistance(DistanceUnit.CM);
        I2cAddr i2cAddress = bot.sensorColor.getI2cAddress();


        if (blue/red > 0.5) {
            skystone = true;
        }

//        if ((red/alpha) - (blue/alpha) > tolerence) {
////            skystone = false
////        } else {
////            skystone = true;
//        }

        telemetry.addData("Color 1:", "r: %d g: %d b: %d a: %d", bot.sensorColor.red(), bot.sensorColor.green(), bot.sensorColor.blue(), bot.sensorColor.alpha());
        telemetry.addData("script", script);
        telemetry.addData("distance", distance);
        telemetry.addData("Skystone found", skystone);
        telemetry.update();
    }

    @Override
    public void stop() {
        super.stop();
        bot.stop();
    }
}