package org.firstinspires.ftc.teamcode.Trinkets;

public class TowerBuilder {
    public static final TowerBuilder NIL = new TowerBuilder(TowerGrabber.NIL);

    public final TowerGrabber grabber;

    public TowerBuilder(TowerGrabber towerGrabber) {
        this.grabber = towerGrabber;
    }
}
