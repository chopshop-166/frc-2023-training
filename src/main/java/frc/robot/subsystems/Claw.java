package frc.robot.subsystems;

import com.chopshop166.chopshoplib.commands.SmartSubsystemBase;
import com.chopshop166.chopshoplib.digital.DigitalInputSource;
import com.chopshop166.chopshoplib.digital.MockDigitalInput;
import com.chopshop166.chopshoplib.pneumatics.IDSolenoid;
import com.chopshop166.chopshoplib.pneumatics.MockDSolenoid;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Claw extends SmartSubsystemBase {

    private IDSolenoid solenoid = new MockDSolenoid();

    // true = object in claw
    private DigitalInputSource sensor = new MockDigitalInput();

    public CommandBase open() {
        return instant("Open", () -> {
            solenoid.set(Value.kForward);
        });
    }

    public CommandBase close() {
        return instant("Close", () -> {
            solenoid.set(Value.kReverse);
        });
    }

    public CommandBase grab() {
        return cmd("Grab").onInitialize(() -> {
            solenoid.set(Value.kForward);
        }).runsUntil(sensor).onEnd(() -> {
            solenoid.set(Value.kReverse);
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