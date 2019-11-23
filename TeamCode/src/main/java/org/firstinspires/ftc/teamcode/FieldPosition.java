package org.firstinspires.ftc.teamcode;

import java.util.Locale;

public class FieldPosition
{
    public final double x;
    public final  double y;
    public final  double h;
    public final String description;

    public FieldPosition(double x, double y, double h, String description)
    {
        this.x = x;
        this.y = y;
        this.h = h;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                Locale.US,
                "{(x=%.2f\",y=%.2f\"), h=%.2f·PI, %s}",
                x,
                y,
                h/Math.PI,
                description
        );
    }
}
