package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;

public class AprilTag {
    private int id;
    private Pose2d fieldPose;
    private double heightMeters;

    public AprilTag(int id, Pose2d fieldPose, double heightMeters) {
        this.id = id;
        this.fieldPose = fieldPose;
        this.heightMeters = heightMeters;
    }

    public int getId() {
        return id;
    }

    public Pose2d getPose() {
        return fieldPose;
    }

    public double getHeightMeters() {
        return heightMeters;
    }
}
