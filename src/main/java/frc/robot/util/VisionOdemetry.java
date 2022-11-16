package frc.robot.util;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTableInstance;

public class VisionOdemetry {

    private TagField fieldTags;
    private PhotonCamera camera;

    private boolean positionKnown;
    private Pose2d tagEstimatedPose;

    public VisionOdemetry(String photonName, TagField tags) {
        fieldTags = tags;
        camera = new PhotonCamera(NetworkTableInstance.getDefault(), photonName);
        positionKnown = false;

        tagEstimatedPose = new Pose2d();
    }

    public void update() {
        PhotonPipelineResult result = camera.getLatestResult();

        if (result.hasTargets()) {
            positionKnown = ;
            Listresult.getTargets()
            PhotonTrackedTarget target = result.getBestTarget();
        }
    }

    public boolean isPositionKnow() {
        return positionKnown;
    }

    public Pose2d getPose() {

        return tagEstimatedPose;
    }
}
