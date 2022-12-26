package frc.robot;

import com.chopshop166.chopshoplib.Autonomous;
import com.chopshop166.chopshoplib.commands.CommandRobot;
import com.chopshop166.chopshoplib.controls.ButtonXboxController;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.maps.RobotMap;
import frc.robot.maps.ValkyrieMap;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drive;

public class Robot extends CommandRobot {
    private RobotMap map = new ValkyrieMap();

    private Auto auto = new Auto();
    private Claw claw = new Claw(map.getClawMap());
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
        controller.a().whenPressed(claw.open()).whenReleased(claw.close());
        controller.start().toggleWhenPressed(drive.tankDrive(controller::getLeftY, controller::getRightY))

    }

    @Override
    public void populateDashboard() {

    }

    @Override
    public void setDefaultCommands() {
        drive.setDefaultCommand(drive.drive(controller::getTriggers, controller::getLeftX ));

    }
}