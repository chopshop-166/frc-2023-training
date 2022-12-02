package frc.robot;

import com.chopshop166.chopshoplib.Autonomous;
import com.chopshop166.chopshoplib.commands.CommandRobot;
import com.chopshop166.chopshoplib.commands.SmartSubsystem;
import com.chopshop166.chopshoplib.controls.ButtonXboxController;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PrintCommand;

public class Robot extends CommandRobot {

    private Auto auto = new Auto();

    @Autonomous(defaultAuto = true)
    public CommandBase exampleAuto = auto.exampleAuto();

    private ButtonXboxController controller = new ButtonXboxController(0);

    @Override
    public void teleopInit() {

    }

    @Override
    public void robotInit() {
        super.robotInit();
    }

    @Override
    public void configureButtonBindings() {
        controller.a().whenPressed(new PrintCommand("Hello, world!"));
        controller.b().whenPressed(new PrintCommand("Fortnite"));
        controller.x().whileHeld(startEnd("Foo", () -> {
            System.out.println("Start");
        }, () -> {
            System.out.println("End");
        }));
    }

    @Override
    public void populateDashboard() {

    }

    @Override
    public void setDefaultCommands() {

    }
}