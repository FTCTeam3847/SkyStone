package org.firstinspires.ftc.teamcode.Trinkets;

import java.util.Locale;

import static java.lang.String.format;

public class TowerBuilder {
    public static final TowerBuilder NIL = new TowerBuilder(
            TowerGrabber.NIL,
            TowerLifter.NIL,
            BlockLifter.NIL,
            BlockExtender.NIL,
            BlockGrabber.NIL
    );

    public final TowerGrabber towerGrabber;
    public final TowerLifter towerLifter;
    public final BlockLifter blockLifter;
    public final BlockExtender blockExtender;
    public final BlockGrabber blockGrabber;


    public TowerBuilder(
            TowerGrabber towerGrabber,
            TowerLifter towerLifter,
            BlockLifter blockLifter,
            BlockExtender blockExtender,
            BlockGrabber blockGrabber
    ) {
        this.towerGrabber = towerGrabber;
        this.towerLifter = towerLifter;
        this.blockLifter = blockLifter;
        this.blockExtender = blockExtender;
        this.blockGrabber = blockGrabber;
    }

    @Override
    public String toString() {
        return format(Locale.US,
                "G{%s}, L{%s}, BL{%s}, BX{%s}, BG{%s}",
                towerGrabber,
                towerLifter,
                blockLifter,
                blockExtender,
                blockGrabber
        );
    }
}
