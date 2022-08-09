package frc.robot.subsystems;

import com.chopshop166.chopshoplib.commands.SmartSubsystemBase;
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

    private NetworkTable table = tableInstance.getTable("photonvision");

    private NetworkTableEntry pitchEntry = table.getEntry("targetPitch");
    private NetworkTableEntry yawEntry = table.getEntry("targetYaw");
    private NetworkTableEntry targetEntry = table.getEntry("hasTarget");

    private PIDController pid = new PIDController(0.0, 0.0, 0.0);

    public Turret(TurretMap map) {
        this.motor = map.getMotor();
    }

    public CommandBase follow() {
        return running("Follow", () -> {
            motor.set(pid.calculate(pitchEntry.getDouble(0.0)));
        });
    }

    @Override
    public void reset() {
        motor.set(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Sees Target", targetEntry.getBoolean(false));
        SmartDashboard.putNumber("Target Pitch", pitchEntry.getDouble(0.0));
        SmartDashboard.putNumber("Target Yaw", yawEntry.getDouble(0.0));
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