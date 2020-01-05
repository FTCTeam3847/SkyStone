package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.controller.FieldPosition;
import org.firstinspires.ftc.teamcode.controller.Localizer;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static java.lang.String.format;
import static org.firstinspires.ftc.teamcode.polar.PolarUtil.subtract;

public class BufferingLocalizer implements Localizer<FieldPosition> {
    private final int RECENTS_MAX_SIZE = 15;
    private final Localizer<FieldPosition> delegate;
    private final List<FieldPosition> recents;
    private FieldPosition lastFieldPosition = FieldPosition.UNKNOWN;
    private double lastDeltaRadius = 0;
    private double lastAverageRadius = 0;
    private int unknownsInBuffer;

    public BufferingLocalizer(Localizer<FieldPosition> delegate) {
        this.delegate = delegate;
        this.recents = new LinkedList<>();
        recents.add(FieldPosition.UNKNOWN);
    }

    @Override
    public FieldPosition getCurrent() {
        FieldPosition current = delegate.getCurrent();
        FieldPosition previous = recents.get(0);
        FieldPosition ret = FieldPosition.UNKNOWN;

        lastAverageRadius = 0;
        if (!current.equals(FieldPosition.UNKNOWN)
                && !previous.equals(FieldPosition.UNKNOWN)
                && !recents.contains(FieldPosition.UNKNOWN)
        ) {
            lastDeltaRadius = subtract(current.polarCoord, previous.polarCoord).radius;

            for (int i = 0; recents.size() >= 2 && i < recents.size() - 1; i++) {
                FieldPosition older = recents.get(0);
                FieldPosition newer = recents.get(1);

                double delta = subtract(newer.polarCoord, older.polarCoord).radius;
                lastAverageRadius += delta / (recents.size() - 1);
            }

            if (lastDeltaRadius > (1.0d + lastAverageRadius)) {
                ret = FieldPosition.UNKNOWN;
            } else {
                ret = current;
            }
        }

        if (recents.size() > RECENTS_MAX_SIZE) recents.remove(0);
        recents.add(current);

        lastFieldPosition = ret;
        return ret;
    }

    @Override
    public FieldPosition getLast() {
        return lastFieldPosition;
    }

    @Override
    public void calibrate(FieldPosition reference) {
        delegate.calibrate(reference);
    }

    public String toString() {
        return format(Locale.US,
                "%s ∆%.2f ∆∆%.2f\n%s",
                lastFieldPosition,
                lastDeltaRadius,
                lastAverageRadius,
                delegate
        );
    }

}
