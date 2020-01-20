package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.action.SequentialAction;
import org.firstinspires.ftc.teamcode.action.SkystoneScripts;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.drive.DrivePower;


public abstract class BaseOp extends OpMode {
    {
        msStuckDetectInit = 10_000;
    }

    protected SkystoneBot bot;
    protected SequentialAction script;
    protected SkystoneScripts scripts;

    @Override
    public void init() {
        bot = new SkottBot(hardwareMap, telemetry);
        bot.init();
        scripts = new SkystoneScripts(bot);
        script = scripts.emptyScript();
    }

    @Override
    public void init_loop() {
        super.init_loop();
        bot.init_loop();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        if (gamepad1.x || gamepad2.x) {
            script.stop();
            script = scripts.emptyScript();
        } else {
            script.loop();
        }
        bot.loop();
        script.loop();
    }

    @Override
    public void stop() {
        bot.stop();
        super.stop();
    }
}
