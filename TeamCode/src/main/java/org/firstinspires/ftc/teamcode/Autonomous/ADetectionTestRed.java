package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.BaseOp;
import org.firstinspires.ftc.teamcode.DerpyBot;
import org.firstinspires.ftc.teamcode.action.SequentialAction;
import org.firstinspires.ftc.teamcode.action.SkystoneScripts;
import org.firstinspires.ftc.teamcode.bot.SkystoneBot;
import org.firstinspires.ftc.teamcode.drive.mecanum.MecanumPower;

@Autonomous
public class ADetectionTestRed extends BaseOp {

    private SkystoneBot bot;

    SequentialAction script;
    private SkystoneScripts scripts;


    @Override
    public void init() {
        bot = new DerpyBot(hardwareMap, telemetry, System::nanoTime); //DERPY BOT
        bot.init();
        scripts = new SkystoneScripts(bot);
    }

    @Override
    public void start() {
        script = scripts.detectRedSkystones().start();
    }


    @Override
    public void loop() {
        script.loop();
        bot.loop();
    }

    @Override
    public void stop() {
        script.stop();
        bot.stop();
        bot.getMecanumDrive().setPower(MecanumPower.ZERO);
    }

}
