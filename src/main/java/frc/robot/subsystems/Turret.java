package frc.robot.subsystems;

import com.chopshop166.chopshoplib.commands.SmartSubsystemBase;
import com.chopshop166.chopshoplib.motors.Modifier;
import com.chopshop166.chopshoplib.motors.ModifierGroup;
import com.chopshop166.chopshoplib.motors.SmartMotorController;

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

    private PIDController pid = new PIDController(0.01, 0.0, 0.0);

    private final double ENCODER_MAX = 15.4;
    private final double ENCODER_MIN = -27.0;

    private double ballX = 0.0;

    private ModifierGroup modifiers;

    public Turret(TurretMap map) {
        this.motor = map.getMotor();

        this.modifiers = new ModifierGroup(
                Modifier.upperLimit(() -> motor.getEncoder().getDistance() >= ENCODER_MAX),
                Modifier.lowerLimit(() -> motor.getEncoder().getDistance() <= ENCODER_MIN));

    }

    public CommandBase follow() {
        return running("Follow", () -> {
            motor.set(modifiers.applyAsDouble(pid.calculate(ballX)));
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

        SmartDashboard.putNumber("Encoder", motor.getEncoder().getDistance());

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    @Override
    public void safeState() {
        motor.set(0);
        motor.getEncoder().reset();
    }
}