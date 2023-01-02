package frc.robot.util;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;

public class DrivePID {
    private PIDController xPid;
    private PIDController yPid;
    private PIDController anglePid;

    public DrivePID(double posP, double posI, double posD, double angleP, double angleI, double angleD) {
        this.xPid = new PIDController(posP, posI, posD);
        this.yPid = new PIDController(posP, posI, posD);
        this.anglePid = new PIDController(angleP, angleI, angleD);
    }

    public Transform2d calculate(Pose2d currentPose, Pose2d targetPose) {
        return new Transform2d(
                new Translation2d(
                        yPid.calculate(currentPose.getY() - targetPose
                                .getY()),
                        xPid.calculate(currentPose.getX() - targetPose.getX())),
                new Rotation2d(anglePid
                        .calculate(currentPose.getRotation().getRadians() - targetPose.getRotation().getRadians())));
    }

    public double getError(Pose2d currentPose, Pose2d targetPose) {
        return Math.pow(currentPose.getX() - targetPose.getX(), 2) +
                Math.pow(currentPose.getY() - targetPose.getY(), 2) +
                Math.pow(currentPose.getRotation().getRadians() - targetPose.getRotation().getRadians(), 2);
    }

}
