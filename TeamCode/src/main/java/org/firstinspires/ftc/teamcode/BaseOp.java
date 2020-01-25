package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.action.SequentialAction;
import org.firstinspires.ftc.teamcode.action.SkystoneScripts;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;


public abstract class BaseOp extends OpMode {
    {
        msStuckDetectInit = 12_000;
    }

    protected SkystoneBot bot;
    protected SequentialAction script;
    protected SkystoneScripts scripts;
    protected long loopEndMs;

    @Override
    public void init() {
        bot = new SkottBot(hardwareMap, telemetry);
        bot.init();
        scripts = new SkystoneScripts(bot);
        script = scripts.emptyScript();
        loopEndMs = System.currentTimeMillis();
    }

    @Override
    public void init_loop() {
        super.init_loop();
        bot.init_loop();
        loopEndMs = System.currentTimeMillis();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        if (gamepad1.x || gamepad2.x) {
            script.stop();
            bot.getMecanumDrive().stop();
            bot.getTowerBuilder().stop();
        } else {
            script.loop();
        }
        bot.loop();
        script.loop();
        telemetry.addData("script", script);
        long now = System.currentTimeMillis();
        telemetry.addData("loopMs", now - loopEndMs);
        loopEndMs = System.currentTimeMillis();
    }

    @Override
    public void stop() {
        bot.stop();
        super.stop();
    }
}
