package org.firstinspires.ftc.teamcode.Trinkets;

import java.util.Locale;

import static java.lang.String.format;

public class TowerBuilder {
    public static final TowerBuilder NIL = new TowerBuilder(
            TowerGrabber.NIL,
            TowerLifter.NIL,
            BlockLifter.NIL
    );

    public final TowerGrabber grabber;
    public final TowerLifter lifter;
    public final BlockLifter blockLifter;

    public TowerBuilder(
            TowerGrabber towerGrabber,
            TowerLifter lifter, BlockLifter blockLifter) {
        this.grabber = towerGrabber;
        this.lifter = lifter;
        this.blockLifter = blockLifter;
    }

    @Override
    public String toString() {
        return format(Locale.US,
                "G{%s}, L{%s}, BL{%s}",
                grabber,
                lifter,
                blockLifter
        );
    }
}
