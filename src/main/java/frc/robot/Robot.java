package frc.robot;

import com.chopshop166.chopshoplib.Autonomous;
import com.chopshop166.chopshoplib.commands.CommandRobot;
import com.chopshop166.chopshoplib.controls.ButtonXboxController;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Counter;

public class Robot extends CommandRobot {

    private Counter counter = new Counter();
    private Claw claw = new Claw();
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
        // a: increment
        // b: decrement
        // x: increment 5
        // y: decrement 2
        controller.rbumper().whenPressed(claw.open()).whenReleased(claw.close());
    }

    @Override
    public void populateDashboard() {

    }

    @Override
    public void setDefaultCommands() {

    }
}