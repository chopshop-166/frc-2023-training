package frc.robot.util;

import java.util.List;

import com.chopshop166.chopshoplib.drive.SwerveDriveMap;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class VisionOdemetry {

    private final TagField fieldTags;
    private final PhotonCamera camera;

    private boolean positionKnown = false;
    private Pose2d tagEstimatedPose;
    private Pose2d odometryPose;
    private final SwerveDriveMap driveMap;

    private final SwerveDriveOdometry odometry;

    private final Translation3d cameraToRobot;
    private final Rotation2d cameraPitch;

    public VisionOdemetry(String photonName, SwerveDriveMap driveMap, Translation3d cameraToRobot,
            Rotation2d cameraPitch, TagField tags) {
        fieldTags = tags;
        camera = new PhotonCamera(NetworkTableInstance.getDefault(), photonName);
        tagEstimatedPose = new Pose2d();
        this.cameraToRobot = cameraToRobot;
        this.cameraPitch = cameraPitch;

        this.driveMap = driveMap;
        SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
                driveMap.getFrontLeft().getLocation(),
                driveMap.getFrontRight().getLocation(),
                driveMap.getRearLeft().getLocation(),
                driveMap.getRearRight().getLocation());

        odometry = new SwerveDriveOdometry(kinematics, driveMap.getGyro().getRotation2d());

    }

    public void update() {
        PhotonPipelineResult result = camera.getLatestResult();
        if (result.hasTargets()) {
            positionKnown = true;
            tagEstimatedPose = estimatePose(result.getTargets());
            driveMap.getGyro().setAngle(tagEstimatedPose.getRotation().getDegrees());
            odometry.resetPosition(new Pose2d(), driveMap.getGyro().getRotation2d());
        }
        odometry.update(driveMap.getGyro().getRotation2d(),
                driveMap.getFrontLeft().getState(),
                driveMap.getFrontRight().getState(),
                driveMap.getRearLeft().getState(),
                driveMap.getRearRight().getState());
    }

    public boolean isPositionKnow() {
        return positionKnown;
    }

    public Pose2d getPose() {
        return new Pose2d(
                tagEstimatedPose.getX() + odometryPose.getX(),
                tagEstimatedPose.getY() + odometryPose.getY(),
                tagEstimatedPose.getRotation().plus(odometryPose.getRotation()));
    }

    private Pose2d estimatePose(List<PhotonTrackedTarget> allTargets) {
        Pose2d averagePose = new Pose2d();

        for (PhotonTrackedTarget target : allTargets) {

            // TODO: Add correct transforms here
            // https://github.com/PhotonVision/photonvision/blob/master/photonlib-java-examples/src/main/java/org/photonlib/examples/simposeest/robot/DrivetrainPoseEstimator.java
            // Might be cool to check out ^^^
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
                    averagePose.getTranslation().plus(robotPose.getTranslation()),
                    averagePose.getRotation().plus(robotPose.getRotation()));

        }
        return new Pose2d(
                averagePose.getTranslation().div(allTargets.size()),
                averagePose.getRotation().times(1.0 / allTargets.size()));

    }
}
