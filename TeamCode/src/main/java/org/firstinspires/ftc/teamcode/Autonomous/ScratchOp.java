package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.gamepad.PairedButtons;
import org.firstinspires.ftc.teamcode.gamepad.PushButton;
import org.firstinspires.ftc.teamcode.gamepad.ToggleButton;
import org.firstinspires.ftc.teamcode.wpilib.LinearFilter;
import org.firstinspires.ftc.teamcode.wpilib.MedianFilter;

import static com.qualcomm.robotcore.util.Range.clip;
import static java.lang.Integer.max;
import static org.firstinspires.ftc.teamcode.controller.FieldPosition.fieldPosition;

@Autonomous
public class ScratchOp extends OpMode {
    private long loopEndMs;
    LinearFilter loopMsAvg;
    int sampleSize = 5;
    private MedianFilter loopMsMedian;
    ToggleButton measureDistance;
    private DistanceSensor rangeSensor;
    private double distanceRaw;
    private LinearFilter distanceAvg;
    private MedianFilter distanceMedian;
    private int telemetryTxMs = 250;
    PairedButtons<Integer> telemetryMsButtons;
    private PairedButtons<Integer> sampleSizeButtons;

    @Override
    public void init() {
        rangeSensor = hardwareMap.get(DistanceSensor.class, "distance1");
        distanceRaw = rangeSensor.getDistance(DistanceUnit.INCH);

        loopEndMs = System.currentTimeMillis();
        measureDistance = new ToggleButton(() -> gamepad1.right_bumper && !gamepad1.start);

        telemetryMsButtons = new PairedButtons<>(
                () -> new PushButton(() -> gamepad1.b && !gamepad1.start).getCurrent(), 10,
                () -> new PushButton(() -> gamepad1.a && !gamepad1.start).getCurrent(), -10
        );

        sampleSizeButtons = new PairedButtons<>(
                () -> new PushButton(() -> gamepad1.x && !gamepad1.start).getCurrent(), 1,
                () -> new PushButton(() -> gamepad1.y && !gamepad1.start).getCurrent(), -1
        );

        reinit();
    }

    private void reinit() {
        loopMsAvg = LinearFilter.movingAverage(sampleSize);
        loopMsMedian = new MedianFilter(sampleSize);
        distanceAvg = LinearFilter.movingAverage(sampleSize);
        distanceMedian = new MedianFilter(sampleSize);
    }

    private void incrTelemetryTxMs(int delta) {
        telemetryTxMs = clip(telemetryTxMs + delta, 10, 1_000);
        telemetry.setMsTransmissionInterval(telemetryTxMs);
    }

    private void incrSampleSize(int delta) {
        sampleSize = max(0, sampleSize + delta);
        reinit();
    }

    @Override
    public void init_loop() {
        super.init_loop();
        common_loop();
    }

    @Override
    public void loop() {
        common_loop();
    }

    public void common_loop() {
        boolean measureDistance = this.measureDistance.getCurrent();

        telemetryMsButtons.apply(this::incrTelemetryTxMs);
        sampleSizeButtons.apply(this::incrSampleSize);

        telemetry.addLine("cfg")
                .addData("telemMs", telemetryTxMs)
                .addData("samples", sampleSize)
                .addData("measuring", measureDistance)
        ;


        double distAvg;
        double distMedian;
        if (measureDistance) {
            distanceRaw = rangeSensor.getDistance(DistanceUnit.INCH);
        }
        distAvg = distanceAvg.calculate(distanceRaw);
        distMedian = distanceMedian.calculate(distanceRaw);

        telemetry.addLine("distance")
                .addData("raw", "%.2f", distanceRaw)
                .addData("avg", "%.2f", distAvg)
                .addData("median", "%.2f", distMedian)
        ;


        long now = System.currentTimeMillis();
        double loopMs = now - loopEndMs;
        double loopAvg = loopMsAvg.calculate(loopMs);
        double loopMean = loopMsMedian.calculate(loopMs);
        telemetry.addData("measureDistance", "" + measureDistance);

        telemetry.addLine("loopMs")
                .addData("raw", "%.2f", loopMs)
                .addData("avg", "%.2f", loopAvg)
                .addData("median", "%.2f", loopMean)
        ;

        loopEndMs = now;

        telemetry.update();
    }

}
