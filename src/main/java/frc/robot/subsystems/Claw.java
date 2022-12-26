package frc.robot.subsystems;

import com.chopshop166.chopshoplib.commands.SmartSubsystemBase;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.maps.subsystems.ClawMap;

public class Claw extends SmartSubsystemBase {

    private ClawMap map;

    public Claw( final ClawMap map) {
        this.map = map; 
    }

  public CommandBase open() {
    return instant("Open", () -> {
        map.getSolenoid().set(Value.kForward);
    });
  }

  public CommandBase close() {
      return instant("Close", () -> {
         map.getSolenoid() .set(Value.kReverse);
      });
  }

  public CommandBase openClose() {
      return startEnd("OpenClose", () -> {
          map.getSolenoid().set(Value.kReverse);
      }, ()-> {
          map.getSolenoid().set(Value.kForward);
      }
      );
  }

  public CommandBase grab() {
      return cmd("Grab").onInitialize(() -> {
          map.getSolenoid().set(Value.kForward);
    }).runsUntil(map.getSensor()).onEnd(() -> {
          map.getSolenoid().set(Value.kReverse);  
    });
    }
    public CommandBase grab2() {
      return sequence("Grab Sequence", open(), waitUntil("Wait For Object", map.getSensor()), close());}

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