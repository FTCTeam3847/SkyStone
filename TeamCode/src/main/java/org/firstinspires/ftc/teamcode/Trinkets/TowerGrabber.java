// leftPos  Start 0.60, End 0.15
// rightPos Start 0.25, End 0.70

package org.firstinspires.ftc.teamcode.Trinkets;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.String.format;

public class TowerGrabber {
    public static TowerGrabber NIL = new TowerGrabber((c) -> {}, (c) -> {}, () -> 0.0d, () -> 0.0d);

    //Consumer takes a variable and returns a void
    Consumer<Double> setLeftPos;
    Consumer<Double> setRightPos;
    Supplier<Double> getLeftPos;
    Supplier<Double> getRightPos;

    double position;

    double leftStartPos = 0.60;
    double leftEndPos = 0.10;
    double rightStartPos = 0.25;
    double rightEndPos = 0.75;

    public TowerGrabber(Consumer<Double> setLeftPos,
                        Consumer<Double> setRightPos,
                        Supplier<Double> getLeftPos,
                        Supplier<Double> getRightPos) {
        this.setLeftPos = setLeftPos;
        this.setRightPos = setRightPos;
        this.getLeftPos = getLeftPos;
        this.getRightPos = getRightPos;
    }

    public void setPosition(double position) { // from 0.0 to 1.0
        position = max(min(1.0, position),0.0);
        setLeftPos.accept(leftStartPos - (position * (leftStartPos - leftEndPos)));
        setRightPos.accept(rightStartPos + (position * (rightEndPos - rightStartPos)));
        this.position = position;
    }

    public double getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return format(
                Locale.ENGLISH,
                "%.2f L%.2f R%.2f",
                position,
                getLeftPos.get(),
                getRightPos.get()
        );
    }

}
