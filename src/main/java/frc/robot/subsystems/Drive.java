package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.chopshop166.chopshoplib.commands.SmartSubsystemBase;
import com.chopshop166.chopshoplib.motors.CSSparkMax;
import com.chopshop166.chopshoplib.motors.SmartMotorController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Drive extends SmartSubsystemBase {

    private SmartMotorController left = new CSSparkMax(2, MotorType.kBrushless);
    private SmartMotorController right = new CSSparkMax(3, MotorType.kBrushless);

    private DifferentialDrive differentialDrive = new DifferentialDrive(left, right);

    public CommandBase drive(DoubleSupplier xSpeed, DoubleSupplier zRotation) {
        return running("Drive", () -> {
            differentialDrive.arcadeDrive(xSpeed.getAsDouble(), zRotation.getAsDouble());
        });
    }

    public CommandBase tankDrive(DoubleSupplier leftSpeed, DoubleSupplier rightSpeed) {
        return running("Tank Drive", () -> {
            differentialDrive.tankDrive(leftSpeed.getAsDouble(), rightSpeed.getAsDouble());
        });
    }

    @Override
    public void reset() {
        // Nothing to reset here
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        // Use this for any background processing
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    @Override
    public void safeState() {

    }
}