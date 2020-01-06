package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.PIXEL_FORMAT;

import java.util.ArrayList;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import static com.vuforia.Vuforia.setFrameFormat;

@Autonomous
public class TensorFlow extends BaseOp {
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";

    private static final String VUFORIA_KEY = GameConstants.VUFORIA_KEY;


    private VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;


    @Override
    public void init() {
        initVuforia();
        initTfod();
        tfod.activate();
    }

    @Override
    public void loop() {
        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
        if (updatedRecognitions != null) {
            telemetry.addData("# Object Detected", updatedRecognitions.size());

            // step through the list of recognitions and display boundary info.
            int i = 0;
            for (Recognition recognition : updatedRecognitions) {
                telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                        recognition.getLeft(), recognition.getTop());
                telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                        recognition.getRight(), recognition.getBottom());
                ArrayList<Integer> xVals = new ArrayList<>();
                Bitmap map = null;
                try {
                    map = vuforia.convertFrameToBitmap(vuforia.getFrameQueue().take());

                } catch (Exception e) { }
                telemetry.addData("Bitmap", true);
                Detection detection = new Detection(map, recognition);
                telemetry.addData("Detection", true);
                xVals = detection.getSkystoneXVals();
                telemetry.addData("Finished", true);
                telemetry.addData("Xvals", xVals);

                int height = map.getHeight();
                int width = map.getWidth();
                float recHeight = recognition.getWidth();
                float recWidth = recognition.getHeight();

                int minY = (int) (height-recognition.getBottom());
                int maxY = (int) (height-recognition.getTop());
                int minX = (int) (0+recognition.getLeft());
                int maxX = (int) (width-recognition.getRight());

                double recRatio = 0.71;
                int numBlocks = (int) Math.round(recWidth/(recHeight/recRatio));
                int blockWidth = Math.round(recWidth/numBlocks);

                telemetry.addData("MaxX", maxX);
                telemetry.addData("MinX", minX);
                telemetry.addData("MaxY", maxY);
                telemetry.addData("MinY", minY);
                telemetry.addData("bottom", recognition.getBottom());
                telemetry.addData("width", map.getWidth());
                telemetry.addData("numBlocks", numBlocks);
                telemetry.addData("blockWidth", blockWidth);
                telemetry.addData("recRatio", recRatio);
                telemetry.addData("recWidth", recWidth);
                telemetry.addData("recHeiht", recHeight);

            }
            telemetry.update();
        }
    }


    private class Detection {
        private Bitmap map;
        private Recognition recognition;
        private int maxY;
        private int minY;
        private int maxX;
        private int minX;

        private double recRatio;
        private double numBlocks;
        private int blockWidth;

        public Detection(Bitmap map, Recognition recognition) {
            this.map = map;
            this.recognition = recognition;

            int height = map.getHeight();
            int width = map.getWidth();
            float recHeight = recognition.getWidth();
            float recWidth = recognition.getHeight();

            maxY = (int) (height-recognition.getBottom());
            minY = (int) (height-recognition.getTop());
            minX = (int) (0+recognition.getLeft());
            maxX = (int) (width-recognition.getRight());

            recRatio = 0.71;
            numBlocks = (int) Math.round(recWidth/(recHeight/recRatio));
            blockWidth = (int) Math.round(recWidth/numBlocks);
        }

        public ArrayList<Integer> getAverageColor(Bitmap map) {
            int r = 0;
            int g = 0;
            int b = 0;

            int total = 0;

            for (int x = 0; x < map.getWidth(); x+=10)
            {
                for (int y = 0; y < map.getHeight(); y+=10)
                {
                    Color color = map.getPixel(x, y);

                    r += color.R;
                    g += color.G;
                    b += color.B;

                    total++;
                }
            }

            //Calculate average
            r /= total;
            g /= total;
            b /= total;

            return Color.FromArgb(r, g, b);
        }

        public ArrayList<Integer> getSkystoneXVals() {
            ArrayList<Integer> xVals = new ArrayList<Integer>();
            ArrayList<Integer> samples = new ArrayList<Integer>();
            int sumPixel = 0; //total color?

            for (int x = 0; x < map.getWidth(); x += 10) {
                for (int y = 0; y < map.getHeight(); y += 10) {
                        sumPixel += map.getPixel(x, y); //color of pixel
                    if (x%blockWidth == 0) {
                        samples.add(sumPixel/blockWidth); //color/num pixels -> average color?
                    }
                }
            }
            int totalSum = 0; //sum of average colors?
            for (int i : samples) {
                totalSum += i;
            }
            int totalAvg = totalSum/samples.size();
            for (int i = 0; i < samples.size(); i++) {
                if (samples.get(i) < totalAvg) {
                    xVals.add(i);
                }
            }
            return xVals;
        }
    }

    public void stop() {
        tfod.shutdown();
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.7;
        tfodParameters.useObjectTracker = true;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);

    }

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;
        parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        setFrameFormat(PIXEL_FORMAT.RGB565, true);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }
}
