package frc.robot.subsystems;

import java.util.HashMap;
import java.util.function.DoubleSupplier;

import com.chopshop166.chopshoplib.commands.SmartSubsystemBase;
import com.chopshop166.chopshoplib.drive.SwerveDriveMap;
import com.chopshop166.chopshoplib.drive.SwerveModule;
import com.chopshop166.chopshoplib.motors.Modifier;
import com.chopshop166.chopshoplib.sensors.gyro.SmartGyro;

import org.photonvision.PhotonCamera;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.util.DrivePID;
import frc.robot.util.VisionOdemetry;

public class Drive extends SmartSubsystemBase {

    private final SwerveDriveKinematics kinematics;
    private final SwerveModule frontLeft;
    private final SwerveModule frontRight;
    private final SwerveModule rearLeft;
    private final SwerveModule rearRight;

    private final double maxDriveSpeedMetersPerSecond;
    private final double maxRotationRadiansPerSecond;
    private final SmartGyro gyro;
    // private boolean inverted = false;

    private PhotonCamera camera = new PhotonCamera(NetworkTableInstance.getDefault(), "gloworm");

    private double speedCoef = 0.075;

    private double rotationOffset = 0.0;
    private double startingRotation = 0.0;

    private final VisionOdemetry odometry;
    private Pose2d robotPose = new Pose2d();

    DrivePID drivePid = new DrivePID(0, 0, 0, 0, 0, 0);

    public Drive(
            final SwerveDriveMap map) {
        super();
        frontLeft = map.getFrontLeft();
        frontRight = map.getFrontRight();
        rearLeft = map.getRearLeft();
        rearRight = map.getRearRight();
        kinematics = new SwerveDriveKinematics(frontLeft.getLocation(), frontRight.getLocation(),
                rearLeft.getLocation(), rearRight.getLocation());
        gyro = map.getGyro();
        maxDriveSpeedMetersPerSecond = map.getMaxDriveSpeedMetersPerSecond();
        maxRotationRadiansPerSecond = map.getMaxRotationRadianPerSecond();

        Transform3d cameraToRobot = new Transform3d(
                new Translation3d(0, 0, 0),
                new Rotation3d(0, Units.degreesToRadians(26), 0));

        HashMap<Integer, Pose3d> aprilTags = new HashMap<>();
        aprilTags.put(0, new Pose3d(
                new Translation3d(Units.inchesToMeters(64), 0, Units.inchesToMeters(
                        48)),
                new Rotation3d(0, 0, Math.PI)));

        odometry = new VisionOdemetry("gloworm", map, cameraToRobot, aprilTags);

    }

    public CommandBase setRotationOffset() {
        return instant("Set Rotation Offset", () -> {
            rotationOffset = gyro.getRotation2d().getDegrees();
        });
    }

    public CommandBase resetRotationOffset() {
        return instant("Reset Rotation Offset", () -> {
            rotationOffset = 0.0;
        });
    }

    public void setDriverMode(boolean mode) {
        camera.setDriverMode(mode);
    }

    public CommandBase fieldCentricDrive(final DoubleSupplier translateX, final DoubleSupplier translateY,
            final DoubleSupplier rotation) {
        return running("Field Centric Drive",
                () -> updateSwerveSpeedAngle(translateX, translateY, rotation));
    }

    public CommandBase driveTo(Pose2d targetPose) {
        return cmd("Drive To").onExecute(() -> {
            Transform2d velocities = drivePid.calculate(robotPose, targetPose);
            updateSwerveSpeedAngle(velocities::getX, velocities::getY, () -> velocities.getRotation().getRadians());

        }).runsUntil(() -> drivePid.getError(robotPose, targetPose) < 0.1).onEnd(
                () -> {
                    updateSwerveSpeedAngle(() -> 0, () -> 0, () -> 0);
                });
    }

    private void updateSwerveSpeedAngle(final DoubleSupplier translateX, final DoubleSupplier translateY,
            final DoubleSupplier rotation) {
        final Modifier deadband = Modifier.deadband(0.15);
        final double translateXSpeed = deadband.applyAsDouble(translateX.getAsDouble()) * maxDriveSpeedMetersPerSecond
                * speedCoef;
        final double translateYSpeed = deadband.applyAsDouble(translateY.getAsDouble()) * maxDriveSpeedMetersPerSecond
                * speedCoef;
        final double rotationSpeed = deadband.applyAsDouble(rotation.getAsDouble()) * maxRotationRadiansPerSecond;

        // rotationOffset is temporary and startingRotation is set at the start
        final ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(translateYSpeed, translateXSpeed,
                rotationSpeed, Rotation2d.fromDegrees(gyro.getAngle() - rotationOffset - startingRotation));

        // Now use this in our kinematics
        final SwerveModuleState[] moduleStates = kinematics.toSwerveModuleStates(speeds);

        // Front left module state
        frontLeft.setDesiredState(moduleStates[0]);

        // Front right module state
        frontRight.setDesiredState(moduleStates[1]);

        // Back left module state
        rearLeft.setDesiredState(moduleStates[2]);

        // Back right module state
        rearRight.setDesiredState(moduleStates[3]);
    }

    public CommandBase resetGyro() {
        return instant("Reset Gyro", () -> {
            gyro.reset();
            startingRotation = 0.0;
        });
    }

    public void setModuleStates(SwerveModuleState[] states) {
        // Front left module state
        frontLeft.setDesiredState(states[0]);

        // Front right module state
        frontRight.setDesiredState(states[1]);

        // Back left module state
        rearLeft.setDesiredState(states[2]);

        // Back right module state
        rearRight.setDesiredState(states[3]);
    }

    @Override
    public void periodic() {
        odometry.update();
        robotPose = odometry.getPose();
        SmartDashboard.putNumber("robotX", robotPose.getX());
        SmartDashboard.putNumber("robotY", robotPose.getY());
        SmartDashboard.putNumber("robotAngle", robotPose.getRotation().getRadians());
    }

    @Override
    public void reset() {
        gyro.reset();

    }

    @Override
    public void safeState() {
        final SwerveModuleState stop = new SwerveModuleState(0.0, new Rotation2d(0, 0));

        frontLeft.setDesiredState(stop);
        frontRight.setDesiredState(stop);
        rearLeft.setDesiredState(stop);
        rearRight.setDesiredState(stop);

    }
}