package frc.robot.subsystems;

import com.chopshop166.chopshoplib.commands.SmartSubsystemBase;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ExampleSubsystem extends SmartSubsystemBase {

  public CommandBase exampleCommand() {
    return instant("Example", () -> {
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