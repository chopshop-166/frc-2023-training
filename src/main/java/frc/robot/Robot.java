package frc.robot;

import com.chopshop166.chopshoplib.Autonomous;
import com.chopshop166.chopshoplib.commands.CommandRobot;
import com.chopshop166.chopshoplib.commands.SmartSubsystem;
import com.chopshop166.chopshoplib.controls.ButtonXboxController;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Counter;
import frc.robot.subsystems.Drive;

public class Robot extends CommandRobot {

    private Counter counter = new Counter();

    private Auto auto = new Auto();

    private Claw claw = new Claw();

    private Drive drive = new Drive();

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
        controller.rbumper().whenPressed(claw.open()).whenReleased(claw.close());
        controller.start().toggleWhenPressed(drive.tankDrive(controller::getLeftY, controller::getRightY));
    }

    @Override
    public void populateDashboard() {

    }

    @Override
    public void setDefaultCommands() {
        drive.setDefaultCommand(drive.drive(controller::getTriggers, controller::getRightX));
    }
}