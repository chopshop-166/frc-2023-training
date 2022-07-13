package frc.robot.util;

import com.chopshop166.chopshoplib.ColorMath;

import edu.wpi.first.wpilibj.util.Color;

public class LightAnimation {

    ColorPoint[] colorPoints;
    double frame = 0;

    public LightAnimation(ColorPoint... colorPoints) {
        this.colorPoints = colorPoints;
    }

    private double rangeMap(double oldStart, double oldEnd, double newStart, double newEnd, double value) {
        return newStart + (newEnd - newStart) * (value - oldStart) / (oldEnd - oldStart);
    }

    private Color getColorFromIndex(double index) {
        int higherIndex = 1;
        while (colorPoints[higherIndex].index < index) {
            higherIndex++;
        }
        double lowestIndex = colorPoints[higherIndex - 1].index;
        double highestIndex = colorPoints[higherIndex].index;
        Color startColor = colorPoints[higherIndex - 1].color;
        Color endColor = colorPoints[higherIndex].color;
        return ColorMath.lerp(startColor, endColor, rangeMap(lowestIndex,
                highestIndex, 0, 1, index));
    }

    public Color getFrame(double index) {
        return getColorFromIndex(index);
    }

    void advanceFrame(double step) {
        frame += step;
    }

    void advanceFrame() {
        frame += 0.01;
    }

}
