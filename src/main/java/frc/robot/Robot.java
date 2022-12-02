package frc.robot;

import java.util.stream.Stream;

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

    public ButtonXboxController controller = new ButtonXboxController(0);

    @Override
    public void teleopInit() {

    }

    @Override
    public void robotInit() {
        super.robotInit();

    }

    @Override
    public void configureButtonBindings() {
        controller.a().toggleWhenPressed(new PrintCommand("hello world!"));
        controller.b().whenHeld(startEnd("foo", () -> {
            System.out.println("start");
        }, () -> {
            System.out.println("stop");
        }));

    }

    @Override
    public void populateDashboard() {

    }

    @Override
    public void setDefaultCommands() {

    }

    public CommandBase safeStateSubsystems(final SmartSubsystem... subsystems) {
        return parallel("Reset Subsystems",
                Stream.of(subsystems).map(SmartSubsystem::safeStateCmd).toArray(CommandBase[]::new));
    }
}