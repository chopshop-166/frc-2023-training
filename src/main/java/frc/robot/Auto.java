package frc.robot;

import com.chopshop166.chopshoplib.commands.Commandable;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Auto implements Commandable {
    // Declare copies of subsystems

    // Pass in all subsystems
    Auto() {
        // Assign all subsystems
    }

    public CommandBase exampleAuto() {
        return instant("", () -> {
        });
    }

}