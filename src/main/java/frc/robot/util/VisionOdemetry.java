package frc.robot.util;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.NetworkTableInstance;

public class VisionOdemetry {

    private final TagField fieldTags;
    private final PhotonCamera camera;

    private boolean positionKnown = false;
    private Pose2d tagEstimatedPose;

    private final Translation3d cameraToRobot;
    private final Rotation2d cameraPitch;

    public VisionOdemetry(String photonName, Translation3d cameraToRobot, Rotation2d cameraPitch, TagField tags) {
        fieldTags = tags;
        camera = new PhotonCamera(NetworkTableInstance.getDefault(), photonName);
        tagEstimatedPose = new Pose2d();
        this.cameraToRobot = cameraToRobot;
        this.cameraPitch = cameraPitch;

    }

    public void update() {
        PhotonPipelineResult result = camera.getLatestResult();

        if (result.hasTargets()) {
            positionKnown = true;
            List<PhotonTrackedTarget> allTargets = result.getTargets();

            Pose2d averagePose = new Pose2d();

            for (PhotonTrackedTarget target : allTargets) {

                // TODO: Add correct transforms here

                // ! These calculations are temporary until new features get added
                AprilTag tag = fieldTags.getTag(target.getFiducialId());

                Transform3d cameraToTarget = target.getCameraToTarget();
                Translation2d cameraRelative = new Translation2d(
                        cameraToTarget.getX(),
                        cameraToTarget.getY());
                double yawRelative = cameraToTarget.getRotation().getZ();
                double cameraDistance = cameraRelative.getNorm();

                Pose2d robotPose = new Pose2d(
                        tag.getPose().getX()
                                + (cameraDistance * Math.cos(yawRelative + tag.getPose().getRotation().getRadians())),
                        tag.getPose().getY()
                                + (cameraDistance * Math.sin(yawRelative + tag.getPose().getRotation().getRadians())),
                        new Rotation2d(yawRelative + tag.getPose().getRotation().getRadians()));
                // ! Until Here

                averagePose = new Pose2d(
                        averagePose.getX() + robotPose.getX(),
                        averagePose.getY() + robotPose.getY(),
                        averagePose.getRotation().plus(robotPose.getRotation()));

            }
            tagEstimatedPose = new Pose2d(
                    averagePose.getX() / allTargets.size(),
                    averagePose.getY() / allTargets.size(),
                    averagePose.getRotation().times(1.0 / allTargets.size()));
        }
    }

    public boolean isPositionKnow() {
        return positionKnown;
    }

    public Pose2d getPose() {

        return tagEstimatedPose;
    }
}
