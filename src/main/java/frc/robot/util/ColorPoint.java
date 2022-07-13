package frc.robot.util;

import edu.wpi.first.wpilibj.util.Color;

public class ColorPoint {
    public Color color;
    public double index;

    public ColorPoint(double red, double green, double blue, double index) {
        this(new Color(red, green, blue), index);
    }

    public ColorPoint(Color color, double index) {
        this.color = color;
        this.index = index;
    }
}
