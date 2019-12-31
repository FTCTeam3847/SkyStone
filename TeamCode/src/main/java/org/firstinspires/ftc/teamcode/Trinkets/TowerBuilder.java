package org.firstinspires.ftc.teamcode.Trinkets;

import java.util.Locale;

import static java.lang.String.format;

public class TowerBuilder {
    public static final TowerBuilder NIL = new TowerBuilder(
            TowerGrabber.NIL,
            TowerLifter.NIL
    );

    public final TowerGrabber grabber;
    public final TowerLifter lifter;

    public TowerBuilder(
            TowerGrabber towerGrabber,
            TowerLifter lifter) {
        this.grabber = towerGrabber;
        this.lifter = lifter;
    }

    @Override
    public String toString() {
        return format(Locale.US,
                "G{%s}, L{%s}",
                grabber,
                lifter
        );
    }
}
