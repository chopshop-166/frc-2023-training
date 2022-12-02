package frc.robot;

import com.chopshop166.chopshoplib.Autonomous;
import com.chopshop166.chopshoplib.commands.CommandRobot;
import com.chopshop166.chopshoplib.controls.ButtonXboxController;

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
        controller.b().whenHeld(startEnd("Foo", () -> {
            System.out.println("Start");
        }, () -> {
            System.out.println("Stop");
        }));
    }

    @Override
    public void populateDashboard() {

    }

    @Override
    public void setDefaultCommands() {

    }
}