package frc.robot.util;

import java.util.Map;

import com.chopshop166.chopshoplib.drive.SwerveDriveMap;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class SwerveVisionPoseEstimator {

    private final Map<Integer, Pose3d> aprilTags;

    private boolean positionKnown = false;
    private Pose2d odometryPose = new Pose2d();
    private final SwerveDriveMap driveMap;

    private final SwerveDriveOdometry odometry;

    public Transform3d debugTag = new Transform3d();

    private final PhotonCamera camera;
    private final Transform3d cameraToRobot;

    public SwerveVisionPoseEstimator(String photonName, SwerveDriveMap driveMap, Transform3d cameraToRobot,
            Map<Integer, Pose3d> aprilTags) {
        this.aprilTags = aprilTags;

        this.driveMap = driveMap;
        SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
                driveMap.getFrontLeft().getLocation(),
                driveMap.getFrontRight().getLocation(),
                driveMap.getRearLeft().getLocation(),
                driveMap.getRearRight().getLocation());

        this.cameraToRobot = cameraToRobot;

        this.camera = new PhotonCamera(NetworkTableInstance.getDefault(), photonName);

        odometry = new SwerveDriveOdometry(kinematics, driveMap.getGyro().getRotation2d());

    }

    public void update() {

        PhotonPipelineResult result = camera.getLatestResult();
        if (result.hasTargets()) {
            Transform3d cameraToTarget = result.getBestTarget().getBestCameraToTarget();
            debugTag = cameraToTarget;
            int tagId = result.getBestTarget().getFiducialId();
            Pose3d fieldToRobot = aprilTags.get(tagId).plus(cameraToTarget.inverse())
                    .plus(cameraToRobot.inverse());

            Pose2d tagEstimatedPose = fieldToRobot.toPose2d();

            tagEstimatedPose = new Pose2d(-tagEstimatedPose.getX(), -tagEstimatedPose.getY(),
                    tagEstimatedPose.getRotation());
            // driveMap.getGyro().setAngle(-tagEstimatedPose.getRotation().getDegrees() -
            // 180);
            driveMap.getGyro().reset();
            odometry.resetPosition(tagEstimatedPose, driveMap.getGyro().getRotation2d());
        }

        odometryPose = odometry.update(
                Rotation2d.fromDegrees(driveMap.getGyro().getAngle() - 180),
                driveMap.getFrontLeft().getState(),
                driveMap.getFrontRight().getState(),
                driveMap.getRearLeft().getState(),
                driveMap.getRearRight().getState());
    }

    public boolean isPositionKnow() {
        return positionKnown;
    }

    public Pose2d getPose() {
        return odometryPose;
    }

}
