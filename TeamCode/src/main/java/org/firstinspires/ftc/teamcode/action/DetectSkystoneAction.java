package org.firstinspires.ftc.teamcode.action;



import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;
import java.util.function.Supplier;

public class DetectSkystoneAction extends TimeAction {

    private TFObjectDetector tfod;
    private boolean started = false;
    private boolean isDone = false;
    private boolean skystoneVisible = false;

    long dur;


    public DetectSkystoneAction(long dur, Supplier<Long> mSecTime, TFObjectDetector tfod) {
        super(dur, mSecTime);
        this.dur = dur;
        this.tfod = tfod;
    }

    @Override
    public DetectSkystoneAction start() {
        started = true;
        return this;
    }

    @Override
    public void loop() {
        super.loop();
        if (!isDone()) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                for (Recognition rec : updatedRecognitions) {
                    skystoneVisible = rec.getLabel().equals("skytone");
                }
            }
        }
    }

    public boolean visible() {
        return skystoneVisible;
    }

    @Override
    public void stop() {
        super.stop();
    }

}
