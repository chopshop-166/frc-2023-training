package frc.robot.util;

import java.util.ArrayList;
import java.util.Map;

import com.chopshop166.chopshoplib.drive.SwerveDriveMap;

import org.photonvision.PhotonCamera;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.util.RobotPoseEstimator.PoseStrategy;

public class VisionOdemetry {

    private final Map<Integer, Pose3d> aprilTags;

    private boolean positionKnown = false;
    private Pose2d tagEstimatedPose;
    private Pose2d odometryPose;
    private final SwerveDriveMap driveMap;

    private final SwerveDriveOdometry odometry;

    private final RobotPoseEstimator poseEstimator;

    public VisionOdemetry(String photonName, SwerveDriveMap driveMap, Transform3d cameraToRobot,
            Map<Integer, Pose3d> aprilTags) {
        this.aprilTags = aprilTags;

        tagEstimatedPose = new Pose2d();

        this.driveMap = driveMap;
        SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
                driveMap.getFrontLeft().getLocation(),
                driveMap.getFrontRight().getLocation(),
                driveMap.getRearLeft().getLocation(),
                driveMap.getRearRight().getLocation());

        ArrayList<Pair<PhotonCamera, Transform3d>> allCameras = new ArrayList<>();
        allCameras.add(new Pair<>(new PhotonCamera(NetworkTableInstance.getDefault(), photonName), cameraToRobot));

        odometry = new SwerveDriveOdometry(kinematics, driveMap.getGyro().getRotation2d());
        poseEstimator = new RobotPoseEstimator(this.aprilTags, PoseStrategy.AVERAGE_BEST_TARGETS,
                allCameras);

    }

    public void update() {
        Pair<Pose3d, Double> estimatedPose = poseEstimator.update();

        tagEstimatedPose = new Pose2d(
                estimatedPose.getFirst().getX(),
                estimatedPose.getFirst().getY(),
                new Rotation2d(estimatedPose.getFirst().getRotation().getZ()));

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

}
