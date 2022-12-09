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

    private DigitalInputSource sensor = new MockDigitalInput()

    public CommandBase open() -> {
        return instant ("open", () -> {
            solenoid.set(Value.kForward)
        }
    });
    }

    public CommandBase close() {
        return instant("close", () -> {
            solenoid.set(Value.kReverse)
        }
    }

    public CommandBase openClose() {
        return startEnd("OpenClose", () -> {
            solenoid.set(Value.kForward);
        }, () -> solenoid.set(Value.kReverse));
    }

    public CommandBase grab() {
        return cmd("grab").onInitialized(() -> {
            if (!sensor.getAsBooleam()) {
                solenoid.set(value.kFoward);
            }
        }).runsUntill(sensor).onEnd(() -> {
            solenoid.set(value.kReverse);
        });
    }

    public CommandBase grab2() {
        return sequence("Grab Sequence", open(), waitUntil("wait for Object", sensor), close());
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