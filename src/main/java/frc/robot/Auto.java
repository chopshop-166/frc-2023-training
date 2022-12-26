package frc.robot;

import com.chopshop166.chopshoplib.commands.Commandable;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Claw;

public class Auto implements Commandable {
    // Declare copies of subsystems
    final Claw claw;
    // Pass in all subsystems
    public Auto(final Claw claw) {
        // Assign all subsystems
        this.claw = claw;
    }

    public CommandBase openClose(){
        return sequence("Open Close", claw.open(), new WaitCommand(5.0), claw.close());
    }

    public CommandBase exampleAuto() {
        return instant("", () -> {
        });
    }

}