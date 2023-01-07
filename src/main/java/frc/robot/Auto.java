package frc.robot;

import static edu.wpi.first.wpilibj2.command.Commands.runOnce;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Auto {
    // Declare copies of subsystems

    // Pass in all subsystems
    Auto() {
        // Assign all subsystems
    }

    public CommandBase exampleAuto() {
        return runOnce(() -> {
        });
    }

}