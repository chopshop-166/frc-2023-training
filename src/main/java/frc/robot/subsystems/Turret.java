package frc.robot.subsystems;

import com.chopshop166.chopshoplib.commands.SmartSubsystemBase;
import com.chopshop166.chopshoplib.motors.SmartMotorController;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.maps.subsystems.TurretMap;

public class Turret extends SmartSubsystemBase {

    private SmartMotorController motor;

    private NetworkTableInstance tableInstance = NetworkTableInstance.getDefault();
    private NetworkTable camTable = tableInstance.getTable("photonvision").getSubTable("swerveCam");

    private NetworkTableEntry targetEntry = camTable.getEntry("hasTarget");
    private NetworkTableEntry yawEntry = camTable.getEntry("targetYaw");

    private PhotonCamera camera = new PhotonCamera("swerveCam");

    private PIDController pid = new PIDController(0.01, 0.0, 0.0);

    private double ballX = 0.0;

    public Turret(TurretMap map) {
        this.motor = map.getMotor();
    }

    public CommandBase follow() {
        return running("Follow", () -> {
            motor.set(pid.calculate(ballX));
        });
    }

    @Override
    public void reset() {
        motor.set(0);
    }

    @Override
    public void periodic() {

        // PhotonPipelineResult result = camera.getLatestResult();
        boolean hasTargets = targetEntry.getBoolean(false);

        SmartDashboard.putBoolean("Sees Target", hasTargets);
        if (hasTargets) {
            ballX = yawEntry.getDouble(0.0);
        }
        SmartDashboard.putNumber("Target Yaw", ballX);

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    @Override
    public void safeState() {
        motor.set(0);
    }
}